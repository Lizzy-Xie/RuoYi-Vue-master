package com.ruoyi.library.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.TbBorrowMapper;
import com.ruoyi.library.domain.TbBorrow;
import com.ruoyi.library.service.ITbBorrowService;

/**
 * 借阅信息Service业务层处理
 * 
 * @author xlz
 * @date 2025-12-15
 */
@Service
public class TbBorrowServiceImpl implements ITbBorrowService 
{
    @Autowired
    private TbBorrowMapper tbBorrowMapper;

    /**
     * 查询借阅信息
     * 
     * @param BorrowID 借阅信息主键
     * @return 借阅信息
     */
    @Override
    public TbBorrow selectTbBorrowByBorrowID(Long BorrowID)
    {
        return tbBorrowMapper.selectTbBorrowByBorrowID(BorrowID);
    }

    /**
     * 查询借阅信息列表
     * 
     * @param tbBorrow 借阅信息
     * @return 借阅信息
     */
    @Override
    public List<TbBorrow> selectTbBorrowList(TbBorrow tbBorrow)
    {
        return tbBorrowMapper.selectTbBorrowList(tbBorrow);
    }

    /**
     * 新增借阅信息
     * 
     * @param tbBorrow 借阅信息
     * @return 结果
     */
    @Override
    public int insertTbBorrow(TbBorrow tbBorrow)
    {
        return tbBorrowMapper.insertTbBorrow(tbBorrow);
    }

    /**
     * 修改借阅信息
     * 
     * @param tbBorrow 借阅信息
     * @return 结果
     */
    @Override
    public int updateTbBorrow(TbBorrow tbBorrow)
    {
        return tbBorrowMapper.updateTbBorrow(tbBorrow);
    }

    /**
     * 批量删除借阅信息
     * 
     * @param BorrowIDs 需要删除的借阅信息主键
     * @return 结果
     */
    @Override
    public int deleteTbBorrowByBorrowIDs(Long[] BorrowIDs)
    {
        return tbBorrowMapper.deleteTbBorrowByBorrowIDs(BorrowIDs);
    }

    /**
     * 删除借阅信息信息
     * 
     * @param BorrowID 借阅信息主键
     * @return 结果
     */
    @Override
    public int deleteTbBorrowByBorrowID(Long BorrowID)
    {
        return tbBorrowMapper.deleteTbBorrowByBorrowID(BorrowID);
    }
}
