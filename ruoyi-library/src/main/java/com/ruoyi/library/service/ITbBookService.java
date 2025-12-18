package com.ruoyi.library.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.library.domain.TbBook;

/**
 * 图书信息Service接口
 *
 * @author xlz
 * @date 2025-12-15
 */
public interface ITbBookService
{
    /**
     * 查询图书信息
     *
     * @param bkID 图书信息主键
     * @return 图书信息
     */
    public TbBook selectTbBookByBkID(Long bkID);

    /**
     * 查询图书信息列表
     *
     * @param tbBook 图书信息
     * @return 图书信息集合
     */
    public List<TbBook> selectTbBookList(TbBook tbBook);

    /**
     * 新增图书信息
     *
     * @param tbBook 图书信息
     * @return 结果
     */
    public int insertTbBook(TbBook tbBook);

    /**
     * 修改图书信息
     *
     * @param tbBook 图书信息
     * @return 结果
     */
    public int updateTbBook(TbBook tbBook);

    /**
     * 批量删除图书信息
     *
     * @param bkIDs 需要删除的图书信息主键集合
     * @return 结果
     */
    public int deleteTbBookByBkIDs(Long[] bkIDs);

    /**
     * 删除图书信息信息
     *
     * @param bkID 图书信息主键
     * @return 结果
     */
    public int deleteTbBookByBkID(Long bkID);

    // ===================== 以下为新增方法（无任何原有内容修改） =====================
    /**
     * 根据状态查询图书（用于列表筛选）
     *
     * @param bkStatus 图书状态
     * @return 图书信息集合
     */
    public List<TbBook> selectTbBookByStatus(String bkStatus);

    /**
     * 批量更新图书状态（借书/还书联动）
     *
     * @param bkIDs 图书主键集合
     * @param bkStatus 图书状态
     * @return 结果
     */
    public int updateTbBookStatusBatch(Long[] bkIDs, String bkStatus);

    /**
     * 查询图书分类目录（用于下拉选择）
     *
     * @return 分类目录集合（key:分类编码，value:分类名称）
     */
    public List<Map<String, String>> getBookCatalog();

    /**
     * 批量入库（生成连续书号）
     *
     * @param book 基础图书信息（含分类、名称等通用字段）
     * @param batchNum 批量入库数量
     * @return 成功入库数量
     */
    public int batchInsertBook(TbBook book, int batchNum);
}