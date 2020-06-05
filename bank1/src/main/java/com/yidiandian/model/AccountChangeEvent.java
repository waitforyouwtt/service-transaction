package com.yidiandian.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeEvent implements Serializable {
    /**
     * 转账账号
     */
    private String accountNoSource;
    /**
     * 接收账号
     */
    private String accountNoReceiver;
    /**
     * 转账金额
     */
    private double amount;
    /**
     * 事务号
     */
    private String txNo;

}
