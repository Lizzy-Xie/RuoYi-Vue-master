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
import com.ruoyi.library.domain.TbBorrow;
import com.ruoyi.library.service.ITbBorrowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 借阅信息Controller
 * 
 * @author xlz
 * @date 2025-12-15
 */
@RestController
@RequestMapping("/library/borrow")
public class TbBorrowController extends BaseController
{
    @Autowired
    private ITbBorrowService tbBorrowService;

    /**
     * 查询借阅信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbBorrow tbBorrow)
    {
        startPage();
        List<TbBorrow> list = tbBorrowService.selectTbBorrowList(tbBorrow);
        return getDataTable(list);
    }

    /**
     * 导出借阅信息列表
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:export')")
    @Log(title = "借阅信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbBorrow tbBorrow)
    {
        List<TbBorrow> list = tbBorrowService.selectTbBorrowList(tbBorrow);
        ExcelUtil<TbBorrow> util = new ExcelUtil<TbBorrow>(TbBorrow.class);
        util.exportExcel(response, list, "借阅信息数据");
    }

    /**
     * 获取借阅信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:query')")
    @GetMapping(value = "/{BorrowID}")
    public AjaxResult getInfo(@PathVariable("BorrowID") Long BorrowID)
    {
        return success(tbBorrowService.selectTbBorrowByBorrowID(BorrowID));
    }

    /**
     * 新增借阅信息
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:add')")
    @Log(title = "借阅信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbBorrow tbBorrow)
    {
        return toAjax(tbBorrowService.insertTbBorrow(tbBorrow));
    }

    /**
     * 修改借阅信息
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:edit')")
    @Log(title = "借阅信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbBorrow tbBorrow)
    {
        return toAjax(tbBorrowService.updateTbBorrow(tbBorrow));
    }

    /**
     * 删除借阅信息
     */
    @PreAuthorize("@ss.hasPermi('library:borrow:remove')")
    @Log(title = "借阅信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{BorrowIDs}")
    public AjaxResult remove(@PathVariable Long[] BorrowIDs)
    {
        return toAjax(tbBorrowService.deleteTbBorrowByBorrowIDs(BorrowIDs));
    }
}
