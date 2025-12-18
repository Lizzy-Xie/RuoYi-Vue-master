package com.ruoyi.library.mapper;

import java.util.List;
import com.ruoyi.library.domain.TbBorrow;

/**
 * 借阅信息Mapper接口
 * 
 * @author xlz
 * @date 2025-12-15
 */
public interface TbBorrowMapper 
{
    /**
     * 查询借阅信息
     * 
     * @param BorrowID 借阅信息主键
     * @return 借阅信息
     */
    public TbBorrow selectTbBorrowByBorrowID(Long BorrowID);

    /**
     * 查询借阅信息列表
     * 
     * @param tbBorrow 借阅信息
     * @return 借阅信息集合
     */
    public List<TbBorrow> selectTbBorrowList(TbBorrow tbBorrow);

    /**
     * 新增借阅信息
     * 
     * @param tbBorrow 借阅信息
     * @return 结果
     */
    public int insertTbBorrow(TbBorrow tbBorrow);

    /**
     * 修改借阅信息
     * 
     * @param tbBorrow 借阅信息
     * @return 结果
     */
    public int updateTbBorrow(TbBorrow tbBorrow);

    /**
     * 删除借阅信息
     * 
     * @param BorrowID 借阅信息主键
     * @return 结果
     */
    public int deleteTbBorrowByBorrowID(Long BorrowID);

    /**
     * 批量删除借阅信息
     * 
     * @param BorrowIDs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbBorrowByBorrowIDs(Long[] BorrowIDs);
}
