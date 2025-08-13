package com.z.wx.controller;

import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.core.page.TableDataInfo;
import com.z.system.domain.SysEventCycle;
import com.z.system.domain.SysEvents;
import com.z.system.domain.SysEventsTemplate;
import com.z.system.service.ISysEventsService;
import com.z.system.service.ISysSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动Controller
 *
 * @author z
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/test/")
public class WxTestController extends BaseController {
    @Autowired
    private ISysEventsService sysEventsService;
    @Autowired
    ISysSemesterService sysSemesterService;

    /**
     * 查询活动列表
     */
    @GetMapping("/checkCycleStatus")
    public void checkCycleStatus() {
        sysSemesterService.checkCycleStatus();

    }

}
