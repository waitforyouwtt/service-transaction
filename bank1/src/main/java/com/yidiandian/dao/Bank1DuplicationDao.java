package com.yidiandian.dao;

import com.yidiandian.entity.Bank1Duplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (DeDuplication)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-03 17:59:24
 */
@Mapper
public interface Bank1DuplicationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param txNo 主键
     * @return 实例对象
     */
    Bank1Duplication queryById(String txNo);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Bank1Duplication> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param deDuplication 实例对象
     * @return 对象列表
     */
    List<Bank1Duplication> queryAll(Bank1Duplication deDuplication);

    /**
     * 新增数据
     *
     * @param deDuplication 实例对象
     * @return 影响行数
     */
    int insert(Bank1Duplication deDuplication);
    int insertTx(@Param("txNo") String txNo);


    /**
     * 修改数据
     *
     * @param deDuplication 实例对象
     * @return 影响行数
     */
    int update(Bank1Duplication deDuplication);

    /**
     * 通过主键删除数据
     *
     * @param txNo 主键
     * @return 影响行数
     */
    int deleteById(String txNo);

}