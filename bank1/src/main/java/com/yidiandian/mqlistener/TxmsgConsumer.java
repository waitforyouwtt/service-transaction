package com.yidiandian.mqlistener;

import com.alibaba.fastjson.JSONObject;
import com.yidiandian.model.AccountChangeEvent;
import com.yidiandian.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: luoxian
 * @Date: 2020/6/5 9:40
 * @Email: 15290810931@163.com
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "consumer_group_txmsg_bank2",topic = "topic_txmsg")
public class TxmsgConsumer implements RocketMQListener<String> {

    @Autowired
    AccountInfoService accountInfoService;

    @Override
    public void onMessage(String message) {
        log.info("开始消费消息:{}",message);
        //解析消息
        JSONObject jsonObject = JSONObject.parseObject(message);
        String accountChangeString = jsonObject.getString("accountChange");
        //转成AccountChangeEvent
        AccountChangeEvent accountChangeEvent = JSONObject.parseObject(accountChangeString, AccountChangeEvent.class);
        //更新本地账户，增加金额
        accountInfoService.updateAccountBalance(accountChangeEvent);
    }
}
