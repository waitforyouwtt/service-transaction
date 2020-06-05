package com.yidiandian.controller;

import com.yidiandian.entity.AccountPay;
import com.yidiandian.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: luoxian
 * @Date: 2020/6/3 18:03
 * @Email: 15290810931@163.com
 */
@RestController
public class AccountInfoController {

    @Autowired
    AccountInfoService accountInfoService;

    /**
     * 根据事务号主动查询支付结果
     */
    @GetMapping(value = "/query-pay-result/{txNo}")
    public AccountPay activeQueryPayResult(@PathVariable("txNo") String txNo){
        AccountPay accountPay = accountInfoService.activeQueryPayResult(txNo);
        return accountPay;
    }

    /**
     * 资金接收方更新账户金额
     * @param accountPay
     * @return
     */
    @PostMapping("/transfer-use-seata")
    public String transferUseSeata(@RequestBody AccountPay accountPay){
        return accountInfoService.updateReceiverAccountBalance(accountPay);
    }

    @PostMapping("/transferTcc")
    String transferTcc(@RequestBody AccountPay accountPay){
         accountInfoService.transferTcc(accountPay);
        return "success";
    }
}
