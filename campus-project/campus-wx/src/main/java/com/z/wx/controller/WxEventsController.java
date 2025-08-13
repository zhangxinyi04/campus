package com.z.wx.controller;

import com.z.common.annotation.Log;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.page.TableDataInfo;
import com.z.common.enums.BusinessType;
import com.z.common.utils.poi.ExcelUtil;
import com.z.system.domain.*;
import com.z.system.service.ISysEventMedalService;
import com.z.system.service.ISysEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动Controller
 *
 * @author z
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/wx/events")
public class WxEventsController extends BaseController {
    @Autowired
    private ISysEventsService sysEventsService;
    @Autowired
    private ISysEventMedalService sysEventMedalService;
    /**
     * 查询活动列表
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SysEvents sysEvents) {
        startPage();
        List<SysEvents> list = sysEventsService.selectSysEventsList(sysEvents);
        return getDataTable(list);
    }


    /**
     * 获取活动详细信息
     */
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") Long eventId) {
        return success(sysEventsService.selectSysEventsByEventId(eventId));
    }

    /**
     * 新增活动
     */
    @PostMapping
    public AjaxResult add(@RequestBody SysEvents sysEvents) {
         sysEventsService.insertSysEvents(sysEvents);
        return success();
    }


    @GetMapping("/listTemplate")
    public TableDataInfo listTemplate() {
        startPage();
        List<SysEventsTemplate> list = sysEventsService.listTemplate();
        return getDataTable(list);
    }

    /**
     * 打卡日期
     * @return
     */
    @GetMapping("/cycleRecords")
    public AjaxResult cycleRecords(SysEventCycle eventCycle) {
        List<SysEventCycle> list = sysEventsService.cycleRecords(eventCycle);
        return success(list);
    }

    /**
     * 通过周期 查看学生打卡记录
     * @return
     */
    @GetMapping("/cycleRecordsByStudent")
    public AjaxResult cycleRecordsByStudent(SysEventCycleStudent eventCycleStudent) {
        List<SysEventCycleStudent> list = sysEventsService.cycleRecordsByStudent(eventCycleStudent);
        return success(list);
    }

    @GetMapping("/medalList")
    public AjaxResult medalList(SysEventMedal sysEventMedal) {
        List<SysEventMedal> list = sysEventMedalService.selectSysEventMedalList(sysEventMedal);
        return success(list);
    }
}
