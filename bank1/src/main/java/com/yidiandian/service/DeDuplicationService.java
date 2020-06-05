package com.yidiandian.service;

import com.yidiandian.entity.Bank1Duplication;
import java.util.List;

/**
 * (DeDuplication)表服务接口
 *
 * @author makejava
 * @since 2020-06-03 17:59:24
 */
public interface DeDuplicationService {

    /**
     * 通过ID查询单条数据
     *
     * @param txNo 主键
     * @return 实例对象
     */
    Bank1Duplication queryById(String txNo);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Bank1Duplication> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param deDuplication 实例对象
     * @return 实例对象
     */
    Bank1Duplication insert(Bank1Duplication deDuplication);

    /**
     * 修改数据
     *
     * @param deDuplication 实例对象
     * @return 实例对象
     */
    Bank1Duplication update(Bank1Duplication deDuplication);

    /**
     * 通过主键删除数据
     *
     * @param txNo 主键
     * @return 是否成功
     */
    boolean deleteById(String txNo);

}