package com.yidiandian.service;

import com.yidiandian.entity.AccountPay;
import com.yidiandian.model.AccountChangeEvent;

/**
 * (AccountInfo)表服务接口
 *
 * @author makejava
 * @since 2020-06-03 17:24:28
 */
public interface AccountInfoService {

    /**
     * 更新账户金额
     */
    void updateAccountBalance(AccountChangeEvent accountChange);

    /**
     * 根据事务号主动查询支付结果
     * @param txNo
     * @return
     */
    AccountPay activeQueryPayResult(String txNo);

    /**
     * 更新账户金额
     * @param accountPay
     * @return
     */
    String updateReceiverAccountBalance(AccountPay accountPay);

    /**
     * 更新账户余额
     * @param accountPay
     * @return
     */
    void transferTcc(AccountPay accountPay);
}