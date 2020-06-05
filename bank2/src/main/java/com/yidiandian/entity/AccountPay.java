package com.yidiandian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (AccountPay)实体类
 *
 * @author makejava
 * @since 2020-06-04 09:40:28
 */
@Data
public class AccountPay implements Serializable {
    private static final long serialVersionUID = 749869223491954832L;
    
    private String id;
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
    private Double payAmount;
    /**
    * 充值结果:success，fail
    */
    private String result;

}