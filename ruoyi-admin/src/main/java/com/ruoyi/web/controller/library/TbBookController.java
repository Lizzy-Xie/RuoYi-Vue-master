package com.ruoyi.web.controller.library;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.library.domain.TbBook;
import com.ruoyi.library.service.ITbBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书信息Controller
 *
 * @author xlz
 * @date 2025-12-15
 */
@RestController
@RequestMapping("/library/book")
public class TbBookController extends BaseController
{
    @Autowired
    private ITbBookService tbBookService;

    /**
     * 查询图书信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbBook tbBook)
    {
        startPage();
        List<TbBook> list = tbBookService.selectTbBookList(tbBook);
        return getDataTable(list);
    }

    /**
     * 导出图书信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:book:export')")
    @Log(title = "图书信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbBook tbBook)
    {
        List<TbBook> list = tbBookService.selectTbBookList(tbBook);
        ExcelUtil<TbBook> util = new ExcelUtil<TbBook>(TbBook.class);
        util.exportExcel(response, list, "图书信息数据");
    }

    /**
     * 获取图书信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:book:query')")
    @GetMapping(value = "/{bkID}")
    public AjaxResult getInfo(@PathVariable("bkID") Long bkID)
    {
        return success(tbBookService.selectTbBookByBkID(bkID));
    }

    /**
     * 新增图书信息
     */
    @PreAuthorize("@ss.hasPermi('library:book:add')")
    @Log(title = "图书信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbBook tbBook)
    {
        return toAjax(tbBookService.insertTbBook(tbBook));
    }

    /**
     * 修改图书信息
     */
    @PreAuthorize("@ss.hasPermi('library:book:edit')")
    @Log(title = "图书信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbBook tbBook)
    {
        return toAjax(tbBookService.updateTbBook(tbBook));
    }

    /**
     * 删除图书信息
     */
    @PreAuthorize("@ss.hasPermi('library:book:remove')")
    @Log(title = "图书信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bkIDs}")
    public AjaxResult remove(@PathVariable Long[] bkIDs)
    {
        return toAjax(tbBookService.deleteTbBookByBkIDs(bkIDs));
    }

    // ===================== 以下为新增接口（无任何原有内容修改） =====================
    /**
     * 获取图书分类目录（前端下拉选择用）
     */
    @PreAuthorize("@ss.hasPermi('library:book:query')")
    @GetMapping("/catalog")
    public AjaxResult getBookCatalog()
    {
        List<Map<String, String>> catalogList = tbBookService.getBookCatalog();
        return success(catalogList);
    }

    /**
     * 批量更新图书状态（借书/还书联动）
     */
    @PreAuthorize("@ss.hasPermi('library:book:edit')")
    @Log(title = "图书信息", businessType = BusinessType.UPDATE)
    @PutMapping("/batch/status")
    public AjaxResult updateBatchStatus(@RequestParam Long[] bkIDs, @RequestParam String bkStatus)
    {
        return toAjax(tbBookService.updateTbBookStatusBatch(bkIDs, bkStatus));
    }

    /**
     * 批量入库（生成连续书号）
     */
    @PreAuthorize("@ss.hasPermi('library:book:add')")
    @Log(title = "图书信息", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batchInsert(@RequestBody TbBook book, @RequestParam int batchNum)
    {
        int successCount = tbBookService.batchInsertBook(book, batchNum);
        return success("批量入库成功，共新增" + successCount + "本图书");
    }
}