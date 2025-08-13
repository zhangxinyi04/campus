package com.z.wx.controller;

import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysUser;
import com.z.system.domain.*;
import com.z.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wx/student")
@Validated
public class WxStudentController {

    @Autowired
    private IWxStudentService studentService;
    @Autowired
    private ISysStudentScoreService studentScoreService;
    @Autowired
    private ISysStudentPeScoreService studentPeScoreService;

    @PostMapping("/getInfo")
    public AjaxResult getInfo() {
        return studentService.getInfo();
    }

    /**
     * 学生写实记录
     *
     * @param sysUserDevelop
     * @return
     */
    @PostMapping("realisticRecords")
    public AjaxResult realisticRecords(@RequestBody SysUserDevelop sysUserDevelop) {
        return studentService.realisticRecords(sysUserDevelop);
    }

    /**
     * 查询学生写实记录
     *
     * @param sysUserDevelop
     * @return
     */
    @PostMapping("selectRealisticRecords")
    public AjaxResult selectRealisticRecords(@RequestBody SysUserDevelop sysUserDevelop) {
        return studentService.selectRealisticRecords(sysUserDevelop);
    }


    /**
     * 学生打卡
     *
     * @param eventCycleStudent
     * @return
     */
    @PostMapping("clockIn")
    public AjaxResult clockIn(@RequestBody SysEventCycleStudent eventCycleStudent) {
        return studentService.clockIn(eventCycleStudent);
    }

    /**
     * 班级圈
     *
     * @param studentMarkRecords
     * @return
     */
    @PostMapping("classCircle")
    public AjaxResult classCircle(@RequestBody SysStudentMarkRecords studentMarkRecords) {
        return studentService.classCircle(studentMarkRecords);
    }

    /**
     * 查询成绩
     */

    @PostMapping("selectStudentScore")
    public AjaxResult selectStudentScore(@RequestBody SysStudentScore studentScore) {
        return AjaxResult.success(studentScoreService.selectSysStudentScoreList(studentScore));
    }

    /**
     * 查询体测成绩
     */

    @PostMapping("selectStudentPeScore")
    public AjaxResult selectStudentPeScore(@RequestBody SysStudentPeScore studentPeScore) {
        return AjaxResult.success(studentPeScoreService.selectSysStudentPeScoreList(studentPeScore));
    }
}
