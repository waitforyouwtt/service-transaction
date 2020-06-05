package com.yidiandian.dao;

import com.yidiandian.entity.AccountPay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (AccountPay)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-04 09:40:30
 */
@Mapper
public interface AccountPayDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountPay queryById(String id);

    /**
     * 新增数据
     *
     * @param accountPay 实例对象
     * @return 影响行数
     */
    int insert(AccountPay accountPay);

    /**
     * 修改数据
     *
     * @param accountPay 实例对象
     * @return 影响行数
     */
    int update(AccountPay accountPay);

}