package com.yidiandian.dao;

import com.yidiandian.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (AccountInfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-03 17:24:27
 */
@Mapper
public interface AccountInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AccountInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param accountInfo 实例对象
     * @return 对象列表
     */
    List<AccountInfo> queryAll(AccountInfo accountInfo);

    /**
     * 新增数据
     *
     * @param accountInfo 实例对象
     * @return 影响行数
     */
    int insert(AccountInfo accountInfo);

    /**
     * 修改数据
     *
     * @param accountInfo 实例对象
     * @return 影响行数
     */
    int update(AccountInfo accountInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 更新账户金额
     * @param accountNo
     * @param amount
     * @return
     */
    int updateAccountBalance(@Param("accountNo") String accountNo,@Param("amount") double amount);
}