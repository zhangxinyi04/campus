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
import com.z.system.domain.SysEventMedal;
import com.z.system.service.ISysEventMedalService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;

/**
 * 活动奖章Controller
 *
 * @author z
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/system/medal")
public class SysEventMedalController extends BaseController {
    @Autowired
    private ISysEventMedalService sysEventMedalService;

    /**
     * 查询活动奖章列表
     */
    @PreAuthorize("@ss.hasPermi('system:medal:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEventMedal sysEventMedal) {
        startPage();
        List<SysEventMedal> list = sysEventMedalService.selectSysEventMedalList(sysEventMedal);
        return getDataTable(list);
    }

    /**
     * 导出活动奖章列表
     */
    @PreAuthorize("@ss.hasPermi('system:medal:export')")
    @Log(title = "活动奖章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEventMedal sysEventMedal) {
        List<SysEventMedal> list = sysEventMedalService.selectSysEventMedalList(sysEventMedal);
        ExcelUtil<SysEventMedal> util = new ExcelUtil<SysEventMedal>(SysEventMedal.class);
        util.exportExcel(response, list, "活动奖章数据");
    }

    /**
     * 获取活动奖章详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:medal:query')")
    @GetMapping(value = "/{eventMedalId}")
    public AjaxResult getInfo(@PathVariable("eventMedalId") Long eventMedalId) {
        return success(sysEventMedalService.selectSysEventMedalByEventMedalId(eventMedalId));
    }

    /**
     * 新增活动奖章
     */
    @PreAuthorize("@ss.hasPermi('system:medal:add')")
    @Log(title = "活动奖章", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEventMedal sysEventMedal) {
        return toAjax(sysEventMedalService.insertSysEventMedal(sysEventMedal));
    }

    /**
     * 修改活动奖章
     */
    @PreAuthorize("@ss.hasPermi('system:medal:edit')")
    @Log(title = "活动奖章", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEventMedal sysEventMedal) {
        return toAjax(sysEventMedalService.updateSysEventMedal(sysEventMedal));
    }

    /**
     * 删除活动奖章
     */
    @PreAuthorize("@ss.hasPermi('system:medal:remove')")
    @Log(title = "活动奖章", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eventMedalIds}")
    public AjaxResult remove(@PathVariable Long[] eventMedalIds) {
        return toAjax(sysEventMedalService.deleteSysEventMedalByEventMedalIds(eventMedalIds));
    }
}
