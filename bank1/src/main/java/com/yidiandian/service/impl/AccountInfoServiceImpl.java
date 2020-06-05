package com.yidiandian.service.impl;

import com.alibaba.fastjson.JSON;
import com.yidiandian.dao.AccountInfoDao;
import com.yidiandian.dao.Bank1DuplicationDao;
import com.yidiandian.dao.LocalConfirmLogDao;
import com.yidiandian.entity.AccountPay;
import com.yidiandian.feignClient.PayClient;
import com.yidiandian.model.AccountChangeEvent;
import com.yidiandian.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * (AccountInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-06-03 17:24:28
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Resource
    private AccountInfoDao accountInfoDao;

    @Autowired
    PayClient payClient;

    @Resource
    private Bank1DuplicationDao bank1DuplicationDao;

    @Resource
    private LocalConfirmLogDao localConfirmLogDao;
    /**
     * 更新账户金额
     *
     * @param accountChange
     */
    @Override
    public void updateAccountBalance(AccountChangeEvent accountChange) {
        //幂等校验
        if(bank1DuplicationDao.queryById(accountChange.getTxNo()) != null){
            return ;
        }
        int i = accountInfoDao.updateAccountBalance(accountChange.getAccountNoReceiver(), accountChange.getAmount());
        //插入事务记录，用于幂等控制
        bank1DuplicationDao.insertTx(accountChange.getTxNo());
    }

    /**
     * 根据事务号主动查询支付结果
     *
     * @param txNo
     * @return
     */
    @Override
    public AccountPay activeQueryPayResult(String txNo) {
        AccountPay payresult = payClient.payresult(txNo);
        if("success".equals(payresult.getResult())){
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNoReceiver(payresult.getAccountNoReceiver());//账号
            accountChangeEvent.setAmount(payresult.getPayAmount());//金额
            accountChangeEvent.setTxNo(payresult.getId());//充值事务号
            updateAccountBalance(accountChangeEvent);
        }
        return payresult;
    }

    /**
     * 更新账户金额
     *
     * @param accountPay
     * @return
     */
    @Override
    public String updateReceiverAccountBalance(AccountPay accountPay) {
        log.info("资金接收方更新账户金额：{}", JSON.toJSONString(accountPay));

        int i = accountInfoDao.updateAccountBalance(accountPay.getAccountNoReceiver(), accountPay.getPayAmount());
        if(accountPay.getPayAmount() > 100000){
            //人为制造异常
            throw new RuntimeException("对不起，你已经超过了每日限额");
        }
        return i>0 ? "success":"fail";
    }

    /**
     * 更新账户余额
     *
     * @param accountPay
     * @return
     */
    @Override
    @Hmily(confirmMethod="confirm", cancelMethod="cancel")
    public void transferTcc(AccountPay accountPay) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("资金接收方 try begin 开始执行...xid:{}",transId);
    }

    /**
     * confirm方法
     * 	confirm幂等校验
     * 	正式增加金额
     * @param accountPay
     */
    @Transactional
    public void confirm(AccountPay accountPay){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("资金接收方 confirm begin 开始执行...xid:{}",transId);
        if(localConfirmLogDao.queryById(transId) != null){
            log.info("bank2 confirm 已经执行，无需重复执行...xid:{}",transId);
            return ;
        }
        //增加金额
        accountInfoDao.updateAccountBalance(accountPay.getAccountNoReceiver(),accountPay.getPayAmount());
        //增加一条confirm日志，用于幂等
        localConfirmLogDao.insertConfirm(transId);
        log.info("bank2 confirm end 结束执行...xid:{}",transId);
    }

    /**
     * @param accountNo
     * @param amount
     */
    public void cancel(String accountNo, Double amount){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 cancel begin 开始执行...xid:{}",transId);
    }
}