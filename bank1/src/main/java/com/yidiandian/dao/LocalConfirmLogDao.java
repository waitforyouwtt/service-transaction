package com.yidiandian.dao;

import com.yidiandian.entity.LocalConfirmLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (LocalConfirmLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-04 16:28:52
 */
@Mapper
public interface LocalConfirmLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param txNo 主键
     * @return 实例对象
     */
    LocalConfirmLog queryById(String txNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<LocalConfirmLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param localConfirmLog 实例对象
     * @return 对象列表
     */
    List<LocalConfirmLog> queryAll(LocalConfirmLog localConfirmLog);

    /**
     * 新增数据
     *
     * @param localConfirmLog 实例对象
     * @return 影响行数
     */
    int insert(LocalConfirmLog localConfirmLog);

    int insertConfirm(@Param("txNo") String txNo);

    /**
     * 修改数据
     *
     * @param localConfirmLog 实例对象
     * @return 影响行数
     */
    int update(LocalConfirmLog localConfirmLog);

    /**
     * 通过主键删除数据
     *
     * @param txNo 主键
     * @return 影响行数
     */
    int deleteById(String txNo);

}