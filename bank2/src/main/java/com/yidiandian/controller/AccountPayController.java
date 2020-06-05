package com.yidiandian.controller;

import com.yidiandian.entity.AccountPay;
import com.yidiandian.model.AccountChangeEvent;
import com.yidiandian.service.AccountPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author: luoxian
 * @Date: 2020/6/4 9:44
 * @Email: 15290810931@163.com
 */
@RestController
public class AccountPayController {

    @Autowired
    AccountPayService accountPayService;

    /**
     * 转账方式1:消息通知
     * @param accountPay
     * @return
     */
    @PostMapping(value = "/transfer_notify_msg")
    public AccountPay transferNotifyMsg(@RequestBody AccountPay accountPay){
        //生成事务编号
        String txNo = UUID.randomUUID().toString();
        accountPay.setId(txNo);
        return accountPayService.transferNotifyMsg(accountPay);
    }

    /**
     * 提供外部主动查询转账结果接口
     */
    @GetMapping(value = "/pay/payresult/{txNo}")
    public AccountPay payresult(@PathVariable("txNo") String txNo){
        return accountPayService.queryByTxNo(txNo);
    }
   // planB-------------------------------------------------------------------

    /**
     * 转账方式2：seata 分布式中间件
     * @param accountPay
     * @return
     */
    @PostMapping(value = "/transfer-seata")
    public AccountPay transferSeata(@RequestBody AccountPay accountPay){
        return accountPayService.transferSeata(accountPay);
    }

    /**
     * 转账方式三：两阶段提交
     * @param accountPay
     * @return
     */
    @PostMapping(value = "/transfer_tcc")
    public AccountPay transferTcc(@RequestBody AccountPay accountPay){
        accountPayService.transferTcc(accountPay);
        return null;
    }

    /**
     *转账方式四：事务+rocketMQ 消息通知
     */
    @PostMapping(value = "/transfer_tx_message")
    public AccountPay transferTXMessage(@RequestBody AccountPay accountPay){
        //创建一个事务id，作为消息内容发到mq
        String txNo = UUID.randomUUID().toString();
        AccountChangeEvent event = new AccountChangeEvent();

        event.setTxNo(txNo);
        event.setAccountNoSource(accountPay.getAccountNoSource());
        event.setAccountNoReceiver(accountPay.getAccountNoReceiver());
        event.setAmount(accountPay.getPayAmount());
        accountPayService.sendUpdateAccountBalance(event);
        return null;
    }
}
