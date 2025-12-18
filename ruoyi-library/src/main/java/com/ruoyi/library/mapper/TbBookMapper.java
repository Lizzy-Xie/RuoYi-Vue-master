package com.ruoyi.library.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.library.domain.TbBook;

/**
 * 图书信息Mapper接口
 *
 * @author xlz
 * @date 2025-12-15
 */
public interface TbBookMapper
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
     * 删除图书信息
     *
     * @param bkID 图书信息主键
     * @return 结果
     */
    public int deleteTbBookByBkID(Long bkID);

    /**
     * 批量删除图书信息
     *
     * @param bkIDs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbBookByBkIDs(Long[] bkIDs);

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
    public int updateTbBookStatusBatch(@Param("bkIDs") Long[] bkIDs, @Param("bkStatus") String bkStatus);

    /**
     * 查询图书分类目录（用于下拉选择）
     *
     * @return 分类目录集合（key:分类编码/名称，value:分类名称/编码）
     */
    public List<Map<String, String>> selectBookCatalog();

    /**
     * 查询最大书号（批量入库时生成连续书号）
     *
     * @return 最大书号
     */
    public String selectMaxBkCode();
}