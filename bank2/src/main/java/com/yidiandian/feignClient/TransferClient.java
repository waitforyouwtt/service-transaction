package com.yidiandian.feignClient;

import com.yidiandian.entity.AccountPay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: luoxian
 * @Date: 2020/6/4 13:59
 * @Email: 15290810931@163.com
 */
@FeignClient(value="bank1",fallback=BankClientFallback.class)
public interface TransferClient {

    //远程调用李四的微服务
    @PostMapping("/transfer-use-seata")
    String transferUseSeata(@RequestBody AccountPay accountPay);

    @PostMapping("/transferTcc")
    String transferTcc(@RequestBody AccountPay accountPay);
}
