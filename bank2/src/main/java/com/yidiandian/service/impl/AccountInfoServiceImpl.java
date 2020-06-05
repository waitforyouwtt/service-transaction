package com.yidiandian.service.impl;

import com.yidiandian.entity.AccountInfo;
import com.yidiandian.dao.AccountInfoDao;
import com.yidiandian.service.AccountInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AccountInfo)表服务实现类
 *
 * @author makejava
 * @since 2020-06-04 13:48:19
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    private AccountInfoDao accountInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AccountInfo queryById(Long id) {
        return this.accountInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<AccountInfo> queryAllByLimit(int offset, int limit) {
        return this.accountInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param accountInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AccountInfo insert(AccountInfo accountInfo) {
        this.accountInfoDao.insert(accountInfo);
        return accountInfo;
    }

    /**
     * 修改数据
     *
     * @param accountInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AccountInfo update(AccountInfo accountInfo) {
        this.accountInfoDao.update(accountInfo);
        return this.queryById(accountInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.accountInfoDao.deleteById(id) > 0;
    }
}