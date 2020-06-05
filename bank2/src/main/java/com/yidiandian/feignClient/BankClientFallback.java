package com.yidiandian.feignClient;

import com.yidiandian.entity.AccountPay;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class BankClientFallback implements TransferClient {

    @Override
    public String transferUseSeata(AccountPay accountPay) {
        return "fallback";
    }

    @Override
    public String transferTcc(AccountPay accountPay) {
        return "fallback";
    }

}
