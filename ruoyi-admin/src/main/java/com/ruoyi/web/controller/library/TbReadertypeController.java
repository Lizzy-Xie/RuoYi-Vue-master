package com.ruoyi.library.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.library.domain.TbReadertype;
import com.ruoyi.library.service.ITbReadertypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 读者类别Controller
 * 
 * @author xlz
 * @date 2025-12-17
 */
@RestController
@RequestMapping("/library/readertype")
public class TbReadertypeController extends BaseController
{
    @Autowired
    private ITbReadertypeService tbReadertypeService;

    /**
     * 查询读者类别列表
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbReadertype tbReadertype)
    {
        startPage();
        List<TbReadertype> list = tbReadertypeService.selectTbReadertypeList(tbReadertype);
        return getDataTable(list);
    }

    /**
     * 导出读者类别列表
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:export')")
    @Log(title = "读者类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbReadertype tbReadertype)
    {
        List<TbReadertype> list = tbReadertypeService.selectTbReadertypeList(tbReadertype);
        ExcelUtil<TbReadertype> util = new ExcelUtil<TbReadertype>(TbReadertype.class);
        util.exportExcel(response, list, "读者类别数据");
    }

    /**
     * 获取读者类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:query')")
    @GetMapping(value = "/{rdType}")
    public AjaxResult getInfo(@PathVariable("rdType") Long rdType)
    {
        return success(tbReadertypeService.selectTbReadertypeByRdType(rdType));
    }

    /**
     * 新增读者类别
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:add')")
    @Log(title = "读者类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbReadertype tbReadertype)
    {
        return toAjax(tbReadertypeService.insertTbReadertype(tbReadertype));
    }

    /**
     * 修改读者类别
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:edit')")
    @Log(title = "读者类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbReadertype tbReadertype)
    {
        return toAjax(tbReadertypeService.updateTbReadertype(tbReadertype));
    }

    /**
     * 删除读者类别
     */
    @PreAuthorize("@ss.hasPermi('library:readertype:remove')")
    @Log(title = "读者类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{rdTypes}")
    public AjaxResult remove(@PathVariable Long[] rdTypes)
    {
        return toAjax(tbReadertypeService.deleteTbReadertypeByRdTypes(rdTypes));
    }
}
