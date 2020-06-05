package com.yidiandian.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yidiandian.dao.*;
import com.yidiandian.entity.AccountInfo;
import com.yidiandian.entity.AccountPay;
import com.yidiandian.feignClient.TransferClient;
import com.yidiandian.model.AccountChangeEvent;
import com.yidiandian.service.AccountPayService;
//import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * (AccountPay)表服务实现类
 *
 * @author makejava
 * @since 2020-06-04 09:40:32
 */
@Service
@Slf4j
public class AccountPayServiceImpl implements AccountPayService {

    @Resource
    private AccountPayDao accountPayDao;

    @Resource
    AccountInfoDao accountInfoDao;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    TransferClient transferClient;

    @Resource
    LocalTryLogDao localTryLogDao;

    @Resource
    LocalConfirmLogDao localConfirmLogDao;

    @Resource
    LocalCancelLogDao localCancelLogDao;

    @Resource
    Bank2DuplicationDao bank2DuplicationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AccountPay queryByTxNo(String id) {
        return this.accountPayDao.queryById(id);
    }


    /**
     * 转账方式一：消息通知
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    @Override
    public AccountPay transferNotifyMsg(AccountPay accountPay) {

        log.info("转账方式一：消息通知:{}", JSON.toJSONString(accountPay));
        int updateAccountBalance = accountInfoDao.updateAccountBalance(accountPay.getAccountNoSource(), accountPay.getPayAmount()* -1);

        accountPay.setResult("success");
        int insert = accountPayDao.insert(accountPay);

        if (updateAccountBalance >0 && insert > 0){
            //发送通知,使用普通消息发送通知
            accountPay.setResult("success");
            rocketMQTemplate.convertAndSend("topic_notifymsg",accountPay);
            return accountPay;
        }
        return accountPay;
    }

    /**
     * 转账方式2：seata 分布式中间件
     *
     * @param accountPay
     * @return
     */
    @Transactional
    //开启全局事务
    //@GlobalTransactional
    @Override
    public AccountPay transferSeata(AccountPay accountPay) {
        log.info("扣减转账账户的金额:{}",JSON.toJSONString(accountPay));

        accountInfoDao.updateAccountBalance(accountPay.getAccountNoSource(),accountPay.getPayAmount()*-1);

        //调用外部服务进行转账
        String transferResult = transferClient.transferUseSeata(accountPay);
        if("fallback".equals(transferResult)){
            //调用李四微服务异常
            throw new RuntimeException("调用工商银行微服务异常");
        }
        if(accountPay.getPayAmount() == 0){
            //人为制造异常:转账金额不能为0
            throw new RuntimeException("bank2 make exception..");
        }
        return null;
    }

    /**
     * 转账方式三：TCC 两阶段提交
     *
     * @param accountPay
     * @return
     */
    @Override
    @Transactional
    //只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法的名字
    @Hmily(confirmMethod="commit",cancelMethod="rollback")
    public void transferTcc(AccountPay accountPay) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("资金转出方transfer try begin 开始执行...xid:{}",transId);
        //幂等判断 判断local_try_log表中是否有try日志记录，如果有则不再执行
        if (localTryLogDao.queryById(transId) != null){
            return ;
        }
        //try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if (localConfirmLogDao.queryById(transId) != null || localCancelLogDao.queryById(transId) != null){
            return;
        }
        //扣减金额
        if (accountInfoDao.updateAccountBalance(accountPay.getAccountNoSource(),accountPay.getPayAmount()*-1) <= 0){
            throw new RuntimeException("账户{}扣减失败，全局事务id:{}"+accountPay.getAccountNoSource()+":"+transId);
        }
        //插入try执行记录,用于幂等判断
        localTryLogDao.insertTry(transId);

        //远程调用工商银行进行转账
        if("fail".equals(transferClient.transferTcc(accountPay))){
            throw new RuntimeException("远程调用转账微服务失败,xid:{}"+transId);
        }

        if(accountPay.getPayAmount() > 100000){
            throw new RuntimeException("人为制造异常,xid:{}"+transId);
        }

        log.info("bank1 try end 结束执行...xid:{}",transId);
    }

    //confirm方法
    @Transactional
    public void commit(AccountPay accountPay){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("资金转出方transfer confirm begin 开始执行...transId:{},accountPay请求参数:{}",transId,JSON.toJSONString(accountPay));
    }

    /** cancel方法
     * 	cancel幂等校验
     * 	cancel空回滚处理
     * 	增加可用余额
     * @param accountPay
     */
    @Transactional
    public void rollback(AccountPay accountPay){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("资金转出方transfer cancel begin 开始执行...transId:{}",transId);

        //cancel幂等校验
        if(localCancelLogDao.queryById(transId) != null){
            log.info("资金转出方transfer cancel 已经执行，无需重复执行,transId:{}",transId);
            return ;
        }

        //cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(localTryLogDao.queryById(transId) == null){
            log.info("资金转出方transfer 空回滚处理，try没有执行，不允许cancel执行,xid:{}",transId);
            return ;
        }
        //增加可用余额
        accountInfoDao.updateAccountBalance(accountPay.getAccountNoSource(),accountPay.getPayAmount());
        //插入一条cancel的执行记录
        localCancelLogDao.insertCancelLog(transId);
        log.info("资金转出方 cancel end 结束执行...xid:{}",transId);
    }

    /**
     * 转账方式四：事务+rocketMQ 消息通知
     *
     * @param event
     */
    @Override
    public void sendUpdateAccountBalance( AccountChangeEvent event) {
        //将accountChangeEvent转成json
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("accountChange",event);
        String jsonString = jsonObject.toJSONString();
        //生成message类型
        Message<String> message = MessageBuilder.withPayload(jsonString).build();
        //发送一条事务消息
        /**
         * String txProducerGroup 生产组
         * String destination topic，
         * Message<?> message, 消息内容
         * Object arg 参数
         */
        rocketMQTemplate.sendMessageInTransaction("producer_group_txmsg","topic_txmsg",message,null);
    }

    /**
     * 更新账户金额
     * @param event
     */
    @Override
    public void doUpdateAccountBalance(AccountChangeEvent event) {
        //幂等判断
        if (bank2DuplicationDao.queryById(event.getTxNo()) != null) {
            return;
        }
        //扣减金额
        accountInfoDao.updateAccountBalance(event.getAccountNoSource(),event.getAmount() * -1);
        //添加事务日志
        bank2DuplicationDao.insertTx(event.getTxNo());
    }


}