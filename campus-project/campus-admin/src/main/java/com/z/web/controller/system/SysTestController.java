package com.z.web.controller.system;

import com.alibaba.fastjson2.JSONObject;
import com.z.common.annotation.Excel;
import com.z.common.core.controller.BaseController;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.utils.poi.ExcelUtil;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentScore;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.service.ISysEventsService;
import com.z.system.service.ISysSemesterService;
import com.z.system.service.ISysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动Controller
 *
 * @author z
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/test")
public class SysTestController extends BaseController {
    @Autowired
    private ISysStudentService studentService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        List<SysStudentScore> sysStudentScores = new ArrayList<>();
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        List<SysStudent> students = studentService.selectSysStudentListAndDept();
        for (SysStudent student : students) {
            SysStudentScore sysStudentScore = new SysStudentScore();
            sysStudentScore.setSemesterId(sysSemesterManage.getSemesterId());
            sysStudentScore.setStudentName(student.getStudentName());
            sysStudentScore.setDeptName(student.getDept().getDeptName());
            sysStudentScores.add(sysStudentScore);
        }
        ExcelUtil<SysStudentScore> util = new ExcelUtil<>(SysStudentScore.class);
        util.importTemplateExcel(sysStudentScores, response, "学生成绩数据","学生成绩数据");
    }

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysStudentScore> util = new ExcelUtil<>(SysStudentScore.class);
        List<SysStudentScore> userList = util.importExcel(file.getInputStream());

//        String operName = getUsername();
//        String message = userService.importUser(userList, updateSupport, operName);
        return success(JSONObject.toJSONString(userList));
    }

}
