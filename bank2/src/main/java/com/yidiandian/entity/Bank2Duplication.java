package com.yidiandian.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (DeDuplication)实体类
 *
 * @author makejava
 * @since 2020-06-03 17:59:24
 */
@Data
public class Bank2Duplication implements Serializable {
    private static final long serialVersionUID = 794981509956283137L;
    
    private String txNo;
    
    private Date createTime;

}