package com.yidiandian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (AccountInfo)实体类
 *
 * @author makejava
 * @since 2020-06-03 17:24:26
 */
@Data
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 296133267656336940L;
    
    private Long id;
    /**
    * 户主姓名
    */
    private String accountName;
    /**
    * 银行卡号
    */
    private String accountNo;
    /**
    * 帐户密码
    */
    private String accountPassword;
    /**
    * 帐户余额
    */
    private Object accountBalance;

}