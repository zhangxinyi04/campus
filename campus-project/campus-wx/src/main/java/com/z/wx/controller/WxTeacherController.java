package com.z.wx.controller;

import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.utils.UserToken;
import com.z.common.utils.UserTokenManager;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentComment;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.service.ISysStudentService;
import com.z.system.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/wx/teacher")
@Validated
public class WxTeacherController {

    @Autowired
    private IWxUserService userService;
    @Autowired
    private ISysStudentService studentService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    @PostMapping("/getInfo")
    public AjaxResult getInfo() {
        return userService.getInfo();
    }

    @PostMapping("/updatePw")
    public AjaxResult updatePw(@RequestBody SysUser user) {

        return userService.updatePw(user);
    }


    @PostMapping("/getStudentInfo")
    public AjaxResult getStudentInfo(@RequestBody SysStudent student) {
        return userService.getStudentInfo(student);
    }

    /**
     * 课堂评价
     *
     */
    @PostMapping("/classEvaluation")
    public AjaxResult classEvaluation(@RequestBody List<SysStudentMarkRecords> studentMarkRecordsList) {
        return studentService.addClassEvaluation(studentMarkRecordsList);
    }

    /**
     * 学期评价
     * @param studentComment
     * @return
     */
    @PostMapping("/termEvaluation")
    public AjaxResult termEvaluation(@RequestBody SysStudentComment studentComment) {
        return studentService.termEvaluation(studentComment);
    }

    @GetMapping("/semesterList")
    public AjaxResult semesterList() {
        List<SysSemesterManage> sysSemesterManage = semesterManageMapper.selectAllSemester();
        return AjaxResult.success(sysSemesterManage);
    }
}
