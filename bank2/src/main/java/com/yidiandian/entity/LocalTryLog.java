package com.yidiandian.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (LocalTryLog)实体类
 *
 * @author makejava
 * @since 2020-06-04 15:23:49
 */
@Data
public class LocalTryLog implements Serializable {
    private static final long serialVersionUID = -83189626959530378L;
    /**
    * 事务id
    */
    private String txNo;
    
    private Date createTime;

}