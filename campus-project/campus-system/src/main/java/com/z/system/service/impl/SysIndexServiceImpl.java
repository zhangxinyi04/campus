package com.z.system.service.impl;

import com.z.common.annotation.DataScope;
import com.z.common.constant.UserConstants;
import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.*;
import com.z.common.exception.ServiceException;
import com.z.common.utils.SecurityUtils;
import com.z.common.utils.StringUtils;
import com.z.common.utils.bean.BeanValidators;
import com.z.common.utils.spring.SpringUtils;
import com.z.system.domain.*;
import com.z.system.domain.vo.StudentScoreVo;
import com.z.system.mapper.*;
import com.z.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysIndexServiceImpl implements ISysIndexService {
    @Autowired
    private ISysEventsService eventsService;
    @Autowired
    private ISysStudentScoreService scoreService;
    @Autowired
    private ISysStudentService studentService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysUserDevelopService developService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysCourseService courseService;
    @Autowired
    private ISysStudentPeScoreService peScoreService;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    @Override
    public AjaxResult index() {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        String semesterId = sysSemesterManage.getSemesterId();
        Map<String, Object> map = new HashMap<>();

        Integer studentCount = studentService.selectStudentCountIndex();
        Integer teacherCount = userService.selectTeacherCountIndex();
        Integer courseCount = courseService.selectCourseCountIndex();
        Integer deptCount = deptService.selectDeptCountIndex();

        List<SysEvents> sysEvents = eventsService.selectEventIndex(semesterId);
        List<SysUserDevelop> develops = developService.selectDevelopsIndex(semesterId);
        List<StudentScoreVo> studentScore = scoreService.selectIndexScore(semesterId);
        List<SysStudentPeScore> studentScorePe = peScoreService.selectIndexScorePe(semesterId);
        map.put("studentCount", studentCount);
        map.put("teacherCount", teacherCount);
        map.put("courseCount", courseCount);
        map.put("deptCount", deptCount);
        map.put("sysEvents", sysEvents);
        map.put("develops", develops);
        map.put("studentScore", studentScore);
        map.put("studentScorePe", studentScorePe);
        return AjaxResult.success(map);
    }

    @Override
    public AjaxResult visualization(String semesterId) {
        Integer studentCount = studentService.selectStudentCountIndex();
        Integer teacherCount = userService.selectTeacherCountIndex();
        Integer courseCount = courseService.selectCourseCountIndex();
        Integer deptCount = deptService.selectDeptCountIndex();
        return null;
    }
}
