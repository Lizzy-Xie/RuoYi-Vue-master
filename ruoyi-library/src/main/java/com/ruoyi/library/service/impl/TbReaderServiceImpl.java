package com.ruoyi.library.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.TbReaderMapper;
import com.ruoyi.library.domain.TbReader;
import com.ruoyi.library.service.ITbReaderService;

/**
 * 读者信息Service业务层处理
 * 
 * @author xlz
 * @date 2025-12-15
 */
@Service
public class TbReaderServiceImpl implements ITbReaderService 
{
    @Autowired
    private TbReaderMapper tbReaderMapper;

    /**
     * 查询读者信息
     * 
     * @param rdID 读者信息主键
     * @return 读者信息
     */
    @Override
    public TbReader selectTbReaderByRdID(Long rdID)
    {
        return tbReaderMapper.selectTbReaderByRdID(rdID);
    }

    /**
     * 查询读者信息列表
     * 
     * @param tbReader 读者信息
     * @return 读者信息
     */
    @Override
    public List<TbReader> selectTbReaderList(TbReader tbReader)
    {
        return tbReaderMapper.selectTbReaderList(tbReader);
    }

    /**
     * 新增读者信息
     * 
     * @param tbReader 读者信息
     * @return 结果
     */
    @Override
    public int insertTbReader(TbReader tbReader)
    {
        return tbReaderMapper.insertTbReader(tbReader);
    }

    /**
     * 修改读者信息
     * 
     * @param tbReader 读者信息
     * @return 结果
     */
    @Override
    public int updateTbReader(TbReader tbReader)
    {
        return tbReaderMapper.updateTbReader(tbReader);
    }

    /**
     * 批量删除读者信息
     * 
     * @param rdIDs 需要删除的读者信息主键
     * @return 结果
     */
    @Override
    public int deleteTbReaderByRdIDs(Long[] rdIDs)
    {
        return tbReaderMapper.deleteTbReaderByRdIDs(rdIDs);
    }

    /**
     * 删除读者信息信息
     * 
     * @param rdID 读者信息主键
     * @return 结果
     */
    @Override
    public int deleteTbReaderByRdID(Long rdID)
    {
        return tbReaderMapper.deleteTbReaderByRdID(rdID);
    }
}
