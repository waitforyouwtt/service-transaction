package com.yidiandian.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (LocalConfirmLog)实体类
 *
 * @author makejava
 * @since 2020-06-04 16:28:52
 */
@Data
public class LocalConfirmLog implements Serializable {
    private static final long serialVersionUID = 953402445763349949L;
    /**
    * 事务id
    */
    private String txNo;
    
    private Date createTime;

}