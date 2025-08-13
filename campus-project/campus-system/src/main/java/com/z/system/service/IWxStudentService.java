package com.z.system.service;

import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysUser;
import com.z.system.domain.SysEventCycleStudent;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.domain.SysUserDevelop;

import java.util.Map;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface IWxStudentService {
    AjaxResult getInfo();

    AjaxResult realisticRecords(SysUserDevelop sysUserDevelop);

    AjaxResult selectRealisticRecords(SysUserDevelop sysUserDevelop);

    AjaxResult clockIn(SysEventCycleStudent eventCycleStudent);

    AjaxResult classCircle(SysStudentMarkRecords studentMarkRecords);

}
