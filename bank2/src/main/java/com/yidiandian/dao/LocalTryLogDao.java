package com.yidiandian.dao;

import com.yidiandian.entity.LocalTryLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (LocalTryLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-04 15:23:49
 */
@Mapper
public interface LocalTryLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param txNo 主键
     * @return 实例对象
     */
    LocalTryLog queryById(String txNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LocalTryLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param localTryLog 实例对象
     * @return 对象列表
     */
    List<LocalTryLog> queryAll(LocalTryLog localTryLog);

    /**
     * 新增数据
     *
     * @param localTryLog 实例对象
     * @return 影响行数
     */
    int insert(LocalTryLog localTryLog);

    int insertTry(@Param("txNo") String txNo);

    /**
     * 修改数据
     *
     * @param localTryLog 实例对象
     * @return 影响行数
     */
    int update(LocalTryLog localTryLog);

    /**
     * 通过主键删除数据
     *
     * @param txNo 主键
     * @return 影响行数
     */
    int deleteById(String txNo);

}