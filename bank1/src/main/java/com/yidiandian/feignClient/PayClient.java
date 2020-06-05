package com.yidiandian.feignClient;

import com.yidiandian.entity.AccountPay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: luoxian
 * @Date: 2020/6/3 18:15
 * @Email: 15290810931@163.com
 */
//value 指的是调用服务者的spring.application.name
@FeignClient(value = "bank2",fallback = PayFallback.class)
public interface PayClient {

    //远程调用充值系统的接口查询充值结果
    @GetMapping(value = "/pay/payresult/{txNo}")
    AccountPay payresult(@PathVariable("txNo") String txNo);

}
