package com.yidiandian.service.impl;

import com.yidiandian.entity.Bank1Duplication;
import com.yidiandian.dao.Bank1DuplicationDao;
import com.yidiandian.service.DeDuplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DeDuplication)表服务实现类
 *
 * @author makejava
 * @since 2020-06-03 17:59:24
 */
@Service
public class DeDuplicationServiceImpl implements DeDuplicationService {
    @Resource
    private Bank1DuplicationDao deDuplicationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param txNo 主键
     * @return 实例对象
     */
    @Override
    public Bank1Duplication queryById(String txNo) {
        return this.deDuplicationDao.queryById(txNo);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Bank1Duplication> queryAllByLimit(int offset, int limit) {
        return this.deDuplicationDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param deDuplication 实例对象
     * @return 实例对象
     */
    @Override
    public Bank1Duplication insert(Bank1Duplication deDuplication) {
        this.deDuplicationDao.insert(deDuplication);
        return deDuplication;
    }

    /**
     * 修改数据
     *
     * @param deDuplication 实例对象
     * @return 实例对象
     */
    @Override
    public Bank1Duplication update(Bank1Duplication deDuplication) {
        this.deDuplicationDao.update(deDuplication);
        return this.queryById(deDuplication.getTxNo());
    }

    /**
     * 通过主键删除数据
     *
     * @param txNo 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String txNo) {
        return this.deDuplicationDao.deleteById(txNo) > 0;
    }
}