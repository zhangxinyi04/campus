package com.z.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.system.domain.SysEventsTemplate;
import com.z.system.mapper.SysSemesterManageMapper;
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
import com.z.system.domain.SysEvents;
import com.z.system.service.ISysEventsService;
import com.z.common.utils.poi.ExcelUtil;
import com.z.common.core.page.TableDataInfo;

/**
 * 活动Controller
 *
 * @author z
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/system/events")
public class SysEventsController extends BaseController {
    @Autowired
    private ISysEventsService sysEventsService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:events:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEvents sysEvents) {
        startPage();
        List<SysEvents> list = sysEventsService.selectSysEventsList(sysEvents);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:events:export')")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEvents sysEvents) {
        List<SysEvents> list = sysEventsService.selectSysEventsList(sysEvents);
        ExcelUtil<SysEvents> util = new ExcelUtil<SysEvents>(SysEvents.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 获取活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:events:query')")
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") Long eventId) {
        return success(sysEventsService.selectSysEventsByEventId(eventId));
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('system:events:add')")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEvents sysEvents) {
        sysEventsService.insertSysEvents(sysEvents);
        return success();
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('system:events:edit')")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEvents sysEvents) {
        return toAjax(sysEventsService.updateSysEvents(sysEvents));
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('system:events:remove')")
    @Log(title = "活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eventIds}")
    public AjaxResult remove(@PathVariable Long[] eventIds) {
        return toAjax(sysEventsService.deleteSysEventsByEventIds(eventIds));
    }

    @PostMapping("/addTemplate")
    public AjaxResult addTemplate() {
        return toAjax(sysEventsService.addTemplate());
    }

    @GetMapping("/listTemplate")
    public TableDataInfo listTemplate() {
        startPage();
        List<SysEventsTemplate> list = sysEventsService.listTemplate();
        return getDataTable(list);
    }

    @GetMapping("/semesterList")
    public AjaxResult semesterList() {
        List<SysSemesterManage> sysSemesterManage = semesterManageMapper.selectAllSemester();
        return success(sysSemesterManage);
    }

}
