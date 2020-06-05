package com.yidiandian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (AccountInfo)实体类
 *
 * @author makejava
 * @since 2020-06-04 13:48:19
 */
@Data
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = -14384957791269812L;
    
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