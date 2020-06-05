package com.yidiandian.service;

import com.yidiandian.entity.AccountPay;
import java.util.List;

/**
 * (AccountPay)表服务接口
 *
 * @author makejava
 * @since 2020-06-03 17:25:47
 */
public interface AccountPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AccountPay queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AccountPay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    AccountPay insert(AccountPay accountPay);

    /**
     * 修改数据
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    AccountPay update(AccountPay accountPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}