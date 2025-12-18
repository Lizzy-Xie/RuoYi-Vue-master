package com.ruoyi.library.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.TbReadertypeMapper;
import com.ruoyi.library.domain.TbReadertype;
import com.ruoyi.library.service.ITbReadertypeService;

/**
 * 读者类别Service业务层处理
 * 
 * @author xlz
 * @date 2025-12-17
 */
@Service
public class TbReadertypeServiceImpl implements ITbReadertypeService 
{
    @Autowired
    private TbReadertypeMapper tbReadertypeMapper;

    /**
     * 查询读者类别
     * 
     * @param rdType 读者类别主键
     * @return 读者类别
     */
    @Override
    public TbReadertype selectTbReadertypeByRdType(Long rdType)
    {
        return tbReadertypeMapper.selectTbReadertypeByRdType(rdType);
    }

    /**
     * 查询读者类别列表
     * 
     * @param tbReadertype 读者类别
     * @return 读者类别
     */
    @Override
    public List<TbReadertype> selectTbReadertypeList(TbReadertype tbReadertype)
    {
        return tbReadertypeMapper.selectTbReadertypeList(tbReadertype);
    }

    /**
     * 新增读者类别
     * 
     * @param tbReadertype 读者类别
     * @return 结果
     */
    @Override
    public int insertTbReadertype(TbReadertype tbReadertype)
    {
        return tbReadertypeMapper.insertTbReadertype(tbReadertype);
    }

    /**
     * 修改读者类别
     * 
     * @param tbReadertype 读者类别
     * @return 结果
     */
    @Override
    public int updateTbReadertype(TbReadertype tbReadertype)
    {
        return tbReadertypeMapper.updateTbReadertype(tbReadertype);
    }

    /**
     * 批量删除读者类别
     * 
     * @param rdTypes 需要删除的读者类别主键
     * @return 结果
     */
    @Override
    public int deleteTbReadertypeByRdTypes(Long[] rdTypes)
    {
        return tbReadertypeMapper.deleteTbReadertypeByRdTypes(rdTypes);
    }

    /**
     * 删除读者类别信息
     * 
     * @param rdType 读者类别主键
     * @return 结果
     */
    @Override
    public int deleteTbReadertypeByRdType(Long rdType)
    {
        return tbReadertypeMapper.deleteTbReadertypeByRdType(rdType);
    }
}
