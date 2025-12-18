package com.ruoyi.web.controller.library;

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
import com.ruoyi.library.domain.TbReader;
import com.ruoyi.library.service.ITbReaderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 读者信息Controller
 * 
 * @author xlz
 * @date 2025-12-15
 */
@RestController
@RequestMapping("/library/reader")
public class TbReaderController extends BaseController
{
    @Autowired
    private ITbReaderService tbReaderService;

    /**
     * 查询读者信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:reader:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbReader tbReader)
    {
        startPage();
        List<TbReader> list = tbReaderService.selectTbReaderList(tbReader);
        return getDataTable(list);
    }

    /**
     * 导出读者信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:reader:export')")
    @Log(title = "读者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbReader tbReader)
    {
        List<TbReader> list = tbReaderService.selectTbReaderList(tbReader);
        ExcelUtil<TbReader> util = new ExcelUtil<TbReader>(TbReader.class);
        util.exportExcel(response, list, "读者信息数据");
    }

    /**
     * 获取读者信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:reader:query')")
    @GetMapping(value = "/{rdID}")
    public AjaxResult getInfo(@PathVariable("rdID") Long rdID)
    {
        return success(tbReaderService.selectTbReaderByRdID(rdID));
    }

    /**
     * 新增读者信息
     */
    @PreAuthorize("@ss.hasPermi('library:reader:add')")
    @Log(title = "读者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbReader tbReader)
    {
        return toAjax(tbReaderService.insertTbReader(tbReader));
    }

    /**
     * 修改读者信息
     */
    @PreAuthorize("@ss.hasPermi('library:reader:edit')")
    @Log(title = "读者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbReader tbReader)
    {
        return toAjax(tbReaderService.updateTbReader(tbReader));
    }

    /**
     * 删除读者信息
     */
    @PreAuthorize("@ss.hasPermi('library:reader:remove')")
    @Log(title = "读者信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{rdIDs}")
    public AjaxResult remove(@PathVariable Long[] rdIDs)
    {
        return toAjax(tbReaderService.deleteTbReaderByRdIDs(rdIDs));
    }
}
