package com.z.web.controller.system;

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
import com.z.common.annotation.Log;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.enums.BusinessType;
import com.z.system.domain.SysUserDevelop;
import com.z.system.service.ISysUserDevelopService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;

/**
 * 学生发展管理Controller
 *
 * @author z
 * @date 2025-03-18
 */
@RestController
@RequestMapping("/system/develop")
public class SysUserDevelopController extends BaseController {
    @Autowired
    private ISysUserDevelopService sysUserDevelopService;

    /**
     * 查询学生发展管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:develop:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserDevelop sysUserDevelop) {
        startPage();
        List<SysUserDevelop> list = sysUserDevelopService.selectSysUserDevelopList(sysUserDevelop);
        return getDataTable(list);
    }

    /**
     * 导出学生发展管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:develop:export')")
    @Log(title = "学生发展管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserDevelop sysUserDevelop) {
        List<SysUserDevelop> list = sysUserDevelopService.selectSysUserDevelopList(sysUserDevelop);
        ExcelUtil<SysUserDevelop> util = new ExcelUtil<SysUserDevelop>(SysUserDevelop.class);
        util.exportExcel(response, list, "学生发展管理数据");
    }

    /**
     * 获取学生发展管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:develop:query')")
    @GetMapping(value = "/{developId}")
    public AjaxResult getInfo(@PathVariable("developId") Long developId) {
        return success(sysUserDevelopService.selectSysUserDevelopByDevelopId(developId));
    }

    /**
     * 新增学生发展管理
     */
    @PreAuthorize("@ss.hasPermi('system:develop:add')")
    @Log(title = "学生发展管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserDevelop sysUserDevelop) {
        return toAjax(sysUserDevelopService.insertSysUserDevelop(sysUserDevelop));
    }

    /**
     * 修改学生发展管理
     */
    @PreAuthorize("@ss.hasPermi('system:develop:edit')")
    @Log(title = "学生发展管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserDevelop sysUserDevelop) {
        return toAjax(sysUserDevelopService.updateSysUserDevelop(sysUserDevelop));
    }

    /**
     * 删除学生发展管理
     */
    @PreAuthorize("@ss.hasPermi('system:develop:remove')")
    @Log(title = "学生发展管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{developIds}")
    public AjaxResult remove(@PathVariable Long[] developIds) {
        return toAjax(sysUserDevelopService.deleteSysUserDevelopByDevelopIds(developIds));
    }
}
