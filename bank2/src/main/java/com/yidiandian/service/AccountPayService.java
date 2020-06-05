package com.yidiandian.service;

import com.yidiandian.entity.AccountPay;
import com.yidiandian.model.AccountChangeEvent;

import java.util.List;

/**
 * (AccountPay)表服务接口
 *
 * @author makejava
 * @since 2020-06-04 09:40:31
 */
public interface AccountPayService {

    /**
     * 通过事务号查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountPay queryByTxNo(String id);


    /**
     * 转账方式一：消息通知
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    AccountPay transferNotifyMsg(AccountPay accountPay);

    /**
     *转账方式2：seata 分布式事务框架
     * @param accountPay
     * @return
     */
    AccountPay transferSeata(AccountPay accountPay);

    /**
     * 转账方式三：TCC 两阶段提交
     * @param accountPay
     * @return
     */
    void transferTcc(AccountPay accountPay);

    /**
     * 转账方式四：事务+rocketMQ 消息通知
     * @param event
     */
    void sendUpdateAccountBalance( AccountChangeEvent event);

    /**
     * 更新账户金额
     * @param event
     */
    void doUpdateAccountBalance(AccountChangeEvent event);
}