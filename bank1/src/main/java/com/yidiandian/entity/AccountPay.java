package com.yidiandian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (AccountPay)实体类
 *
 * @author makejava
 * @since 2020-06-03 17:25:47
 */
@Data
public class AccountPay implements Serializable {
    private static final long serialVersionUID = -85062876980066931L;

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