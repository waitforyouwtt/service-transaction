package com.yidiandian.service.impl;

import com.yidiandian.entity.AccountPay;
import com.yidiandian.dao.AccountPayDao;
import com.yidiandian.service.AccountPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AccountPay)表服务实现类
 *
 * @author makejava
 * @since 2020-06-03 17:25:47
 */
@Service
public class AccountPayServiceImpl implements AccountPayService {
    @Resource
    private AccountPayDao accountPayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AccountPay queryById(String id) {
        return this.accountPayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<AccountPay> queryAllByLimit(int offset, int limit) {
        return this.accountPayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    @Override
    public AccountPay insert(AccountPay accountPay) {
        this.accountPayDao.insert(accountPay);
        return accountPay;
    }

    /**
     * 修改数据
     *
     * @param accountPay 实例对象
     * @return 实例对象
     */
    @Override
    public AccountPay update(AccountPay accountPay) {
        this.accountPayDao.update(accountPay);
        return this.queryById(accountPay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.accountPayDao.deleteById(id) > 0;
    }
}