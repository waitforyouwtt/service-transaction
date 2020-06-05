package com.yidiandian.service;

import com.yidiandian.entity.AccountInfo;
import java.util.List;

/**
 * (AccountInfo)表服务接口
 *
 * @author makejava
 * @since 2020-06-04 13:48:19
 */
public interface AccountInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountInfo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AccountInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param accountInfo 实例对象
     * @return 实例对象
     */
    AccountInfo insert(AccountInfo accountInfo);

    /**
     * 修改数据
     *
     * @param accountInfo 实例对象
     * @return 实例对象
     */
    AccountInfo update(AccountInfo accountInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}