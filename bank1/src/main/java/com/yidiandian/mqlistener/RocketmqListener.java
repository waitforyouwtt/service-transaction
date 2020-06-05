package com.yidiandian.mqlistener;

import com.alibaba.fastjson.JSON;
import com.yidiandian.entity.AccountPay;
import com.yidiandian.model.AccountChangeEvent;
import com.yidiandian.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: luoxian
 * @Date: 2020/6/3 18:05
 * @Email: 15290810931@163.com
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic_notifymsg",consumerGroup = "consumer_group_notifymsg_bank1")
public class RocketmqListener implements RocketMQListener<AccountPay> {

    @Autowired
    AccountInfoService accountInfoService;

    @Override
    public void onMessage(AccountPay accountPay) {
        log.info("接收到消息：{}", JSON.toJSONString(accountPay));
        if("success".equals(accountPay.getResult())){
            log.info("更新账户金额");
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNoReceiver(accountPay.getAccountNoReceiver());
            accountChangeEvent.setAmount(accountPay.getPayAmount());
            accountChangeEvent.setTxNo(accountPay.getId());
            accountInfoService.updateAccountBalance(accountChangeEvent);
        }
        log.info("处理消息完成：{}", JSON.toJSONString(accountPay));
    }
}
