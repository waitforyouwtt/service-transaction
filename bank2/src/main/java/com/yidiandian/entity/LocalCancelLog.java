package com.yidiandian.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (LocalCancelLog)实体类
 *
 * @author makejava
 * @since 2020-06-04 15:24:27
 */
@Data
public class LocalCancelLog implements Serializable {
    private static final long serialVersionUID = -89923536917801784L;
    /**
    * 事务id
    */
    private String txNo;
    
    private Date createTime;

}