package com.ruoyi.library.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.library.mapper.TbBookMapper;
import com.ruoyi.library.domain.TbBook;
import com.ruoyi.library.service.ITbBookService;

/**
 * 图书信息Service业务层处理
 *
 * @author xlz
 * @date 2025-12-15
 */
@Service
public class TbBookServiceImpl implements ITbBookService
{
    @Autowired
    private TbBookMapper tbBookMapper;

    /**
     * 查询图书信息
     *
     * @param bkID 图书信息主键
     * @return 图书信息
     */
    @Override
    public TbBook selectTbBookByBkID(Long bkID)
    {
        return tbBookMapper.selectTbBookByBkID(bkID);
    }

    /**
     * 查询图书信息列表
     *
     * @param tbBook 图书信息
     * @return 图书信息
     */
    @Override
    public List<TbBook> selectTbBookList(TbBook tbBook)
    {
        return tbBookMapper.selectTbBookList(tbBook);
    }

    /**
     * 新增图书信息
     *
     * @param tbBook 图书信息
     * @return 结果
     */
    @Override
    public int insertTbBook(TbBook tbBook)
    {
        return tbBookMapper.insertTbBook(tbBook);
    }

    /**
     * 修改图书信息
     *
     * @param tbBook 图书信息
     * @return 结果
     */
    @Override
    public int updateTbBook(TbBook tbBook)
    {
        return tbBookMapper.updateTbBook(tbBook);
    }

    /**
     * 批量删除图书信息
     *
     * @param bkIDs 需要删除的图书信息主键
     * @return 结果
     */
    @Override
    public int deleteTbBookByBkIDs(Long[] bkIDs)
    {
        return tbBookMapper.deleteTbBookByBkIDs(bkIDs);
    }

    /**
     * 删除图书信息信息
     *
     * @param bkID 图书信息主键
     * @return 结果
     */
    @Override
    public int deleteTbBookByBkID(Long bkID)
    {
        return tbBookMapper.deleteTbBookByBkID(bkID);
    }

    // ===================== 以下为新增方法实现（无任何原有内容修改） =====================
    @Override
    public List<TbBook> selectTbBookByStatus(String bkStatus) {
        return tbBookMapper.selectTbBookByStatus(bkStatus);
    }

    @Override
    public int updateTbBookStatusBatch(Long[] bkIDs, String bkStatus) {
        return tbBookMapper.updateTbBookStatusBatch(bkIDs, bkStatus);
    }

    @Override
    public List<Map<String, String>> getBookCatalog() {
        return tbBookMapper.selectBookCatalog();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertBook(TbBook book, int batchNum) {
        // 1. 查询最大书号
        String maxBkCode = tbBookMapper.selectMaxBkCode();
        // 2. 初始化起始书号
        int startCode = 1;
        if (maxBkCode != null && !maxBkCode.isEmpty()) {
            try {
                startCode = Integer.parseInt(maxBkCode) + 1;
            } catch (NumberFormatException e) {
                // 书号非数字格式时，默认从1开始
                startCode = 1;
            }
        }
        // 3. 批量插入
        int successCount = 0;
        for (int i = 0; i < batchNum; i++) {
            TbBook newBook = new TbBook();
            // 复制基础字段（按需调整，确保与你的TbBook实体字段匹配）
            newBook.setBkCode(String.format("%04d", startCode + i)); // 4位补零书号
            newBook.setBkName(book.getBkName());
            newBook.setBkAuthor(book.getBkAuthor());
            newBook.setBkPress(book.getBkPress());
            newBook.setBkCatalog(book.getBkCatalog());
            newBook.setBkStatus(book.getBkStatus());
            newBook.setBkISBN(book.getBkISBN());
            newBook.setBkPrice(book.getBkPrice());
            // 执行插入
            successCount += tbBookMapper.insertTbBook(newBook);
        }
        return successCount;
    }
}