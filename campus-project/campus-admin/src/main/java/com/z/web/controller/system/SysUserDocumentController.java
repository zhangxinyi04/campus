package com.z.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.z.common.core.domain.entity.SysResources;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.z.common.annotation.Log;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.enums.BusinessType;
import com.z.system.domain.SysUserDocument;
import com.z.system.service.ISysUserDocumentService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;

/**
 * 文件信息Controller
 *
 * @author z
 * @date 2025-03-04
 */
@RestController
@RequestMapping("/system/document")
public class SysUserDocumentController extends BaseController {
    @Autowired
    private ISysUserDocumentService sysUserDocumentService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:document:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserDocument sysUserDocument) {
        startPage();
        List<SysUserDocument> list = sysUserDocumentService.selectSysUserDocumentList(sysUserDocument);
        return getDataTable(list);
    }

//    /**
//     * 导出文件信息列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:document:export')")
//    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysUserDocument sysUserDocument) {
//        List<SysUserDocument> list = sysUserDocumentService.selectSysUserDocumentList(sysUserDocument);
//        ExcelUtil<SysUserDocument> util = new ExcelUtil<SysUserDocument>(SysUserDocument.class);
//        util.exportExcel(response, list, "文件信息数据");
//    }

//    /**
//     * 获取文件信息详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:document:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return success(sysUserDocumentService.selectSysUserDocumentById(id));
//    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:document:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserDocument sysUserDocument) {
        return toAjax(sysUserDocumentService.insertSysUserDocument(sysUserDocument));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:document:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserDocument sysUserDocument) {
        return toAjax(sysUserDocumentService.updateSysUserDocument(sysUserDocument));
    }

    /**
     * 审核文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:resources:examine')")
    @Log(title = "文件信息", businessType = BusinessType.EXAMINE)
    @PutMapping("/examine")
    public AjaxResult examine(@Validated @RequestBody SysUserDocument sysUserDocument) {
        return toAjax(sysUserDocumentService.updateSysUserDocument(sysUserDocument));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:document:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysUserDocumentService.deleteSysUserDocumentByIds(ids));
    }

    /**
     * 记录下载信息
     */
    @PostMapping("/recordsDownload")
    public void recordsDownload(@RequestBody SysUserDocument sysUserDocument) {
        sysUserDocumentService.recordsDownload(sysUserDocument);
    }

    /**
     * 我得记录
     */
    @PostMapping("/selectMyRecords")
    public TableDataInfo selectMyRecords(@RequestBody SysUserDocument sysUserDocument) {
        startPage();
        List<SysUserDocument> list = sysUserDocumentService.selectMyDocumentList(sysUserDocument);
        return getDataTable(list);
    }

    /**
     * 下载通知
     */
    @PostMapping("/downloadNotification")
    public TableDataInfo downloadNotification(@RequestBody SysUserDocument sysUserDocument) {
        startPage();
        List<SysUserDocument> list = sysUserDocumentService.downloadNotification(sysUserDocument);
        return getDataTable(list);
    }

    @GetMapping("/read")
    public AjaxResult read(Long id) {
        sysUserDocumentService.read(id);
        return success();
    }
}
