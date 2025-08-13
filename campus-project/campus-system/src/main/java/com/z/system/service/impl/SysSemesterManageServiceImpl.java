package com.z.system.service.impl;

import com.z.common.core.domain.entity.SysDept;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.utils.LocalDateTimeComparisonUtil;
import com.z.common.utils.SemesterUtils;
import com.z.system.domain.*;
import com.z.system.mapper.SysEventsMapper;
import com.z.system.mapper.SysSemesterManageMapper;
import com.z.system.mapper.SysStudentMapper;
import com.z.system.mapper.SysUserMapper;
import com.z.system.service.ISysSemesterService;
import com.z.system.service.ISysStudentService;
import com.z.system.util.NumberToChineseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


/**
 * 定时任务调度信息 服务层
 *
 * @author ruoyi
 */
@Service
public class SysSemesterManageServiceImpl implements ISysSemesterService {

    @Autowired
    private SysSemesterManageMapper semesterManageMapper;
    @Autowired
    private SysEventsMapper sysEventsMapper;
    @Autowired
    private SysStudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public void semesterManage() {
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemester();
        String[] split = sysSemesterManage.getSemesterId().split("-");
        int year1 = Integer.parseInt(split[0]);
        int year2 = Integer.parseInt(split[1]);
        List<SysSemesterManage> sysSemesterManages = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            SysSemesterManage manage = new SysSemesterManage();
            int newYear1 = year1 + 1;
            int newYear2 = year2 + 1;
            manage.setSemesterId(newYear1 + "-" + newYear2 + "-" + i);
            manage.setName(newYear1 + "-" + newYear2 + "学年第" + i + "学期");
            sysSemesterManages.add(manage);
        }
        semesterManageMapper.insertSemester(sysSemesterManages);
    }

    @Override
    public void checkSemesterStatus() {
        Calendar cal = Calendar.getInstance();  // 获取当前时间的Calendar实例
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String id = SemesterUtils.getSchoolYearAndSemester(year, month);
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setSemesterId(id);
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        if (!SysSemesterManage.CURRENT.equals(sysSemesterManage.getStatus())) {
            SysSemesterManage manage = new SysSemesterManage();
            manage.setStatus("0");
            semesterManageMapper.updateSemester(manage);
            manage.setStatus("1");
            manage.setSemesterId(id);
            semesterManageMapper.updateSemester(manage);
            List<SysStudentMarkRecords> sysStudentMarkRecords = new ArrayList<>();
            List<SysStudent> students = studentMapper.selectSysStudentList(null);
            for (SysStudent student : students) {
                SysStudentMarkRecords records = new SysStudentMarkRecords();
                records.setSemesterId(id);
                records.setStudentId(student.getStudentId());
                records.setMark(0L);
                sysStudentMarkRecords.add(records);
            }
            studentMapper.insertStudentMark(sysStudentMarkRecords);


        }
    }

    @Override
    public void checkCycleStatus() {
        SysEventCycle param = new SysEventCycle();
        SysEvents events = new SysEvents();
        Long[] statusArray = {0L, 1L};
        param.setStatusArray(statusArray);
        events.setStatusArray(statusArray);
        List<SysEventCycle> result = new ArrayList<>();
        List<SysEvents> eventsResult = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        //检查活动是否过期或者是否已经开始
        List<SysEvents> sysEvents = sysEventsMapper.selectSysEventsList(events);
        if (!sysEvents.isEmpty()) {
            for (SysEvents sysEvent : sysEvents) {
                LocalDateTime startTime = sysEvent.getStartTime();
                LocalDateTime endTime = sysEvent.getEndTime();
                if (LocalDateTimeComparisonUtil.isBefore(now, endTime) && LocalDateTimeComparisonUtil.isAfter(now, startTime)) {
                    sysEvent.setStatus("1");
                    eventsResult.add(sysEvent);
                }
                if (LocalDateTimeComparisonUtil.isAfter(now, endTime)) {
                    sysEvent.setStatus("2");
                    eventsResult.add(sysEvent);
                    //如果活动已经结束 根据打卡情况,结算奖章
                    //查询活动周期
                    List<SysEventCycle> eventCycleIds = sysEventsMapper.selectEventsCycle(sysEvent);
                    SysSemesterManage sysSemester = new SysSemesterManage();
                    sysSemester.setStatus("1");
                    SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
                    String eventType = sysEvent.getEventType() + "";
                    SysEvents event = sysEventsMapper.selectSysEventsByEventId(sysEvent.getEventId());
                    List<SysEventGradeClass> eventGradeClass = event.getEventGradeClass();
                    List<Long> gradeIds = new ArrayList<>();
                    List<Long> classIds = new ArrayList<>();
                    List<Long> studentIds = new ArrayList<>();
                    List<SysEventStudentMedal> studentMedalList = new ArrayList<>();
                    if (SysEvents.UNIVERSITY.equals(eventType)) {
                        //如果活动类型为校级 查询活动所选年级下的所有学生
                        for (SysEventGradeClass gradeClass : eventGradeClass) {
                            gradeIds.add(gradeClass.getGradeId());
                        }
                        studentIds = studentMapper.selectSutdentByGradeIds(gradeIds);
                    } else if (SysEvents.CLASS.equals(eventType)) {
                        //如果活动类型为班级 查询活动所选年级下的所有学生
                        for (SysEventGradeClass gradeClass : eventGradeClass) {
                            classIds.add(gradeClass.getClassId());
                        }
                        studentIds = studentMapper.selectSutdentByClassIds(classIds);

                    }
                    //如果不需要打卡，结束时直接发放奖章
                    if ("0".equals(sysEvent.getRecords())) {
                        for (Long studentId : studentIds) {
                            SysEventStudentMedal studentMedal = new SysEventStudentMedal();
                            studentMedal.setMedalId(sysEvent.getMedalId());
                            studentMedal.setStudentId(studentId);
                            studentMedal.setSemesterId(sysSemesterManage.getSemesterId());
                            studentMedalList.add(studentMedal);
                        }
                    } else {
                        for (Long studentId : studentIds) {
                            //查询学生活动打卡记录
                            List<Long> students = sysEventsMapper.selectStudentEventsCycle(studentId, sysEvent.getEventId());
                            if (eventCycleIds.size() == students.size()) {
                                SysEventStudentMedal studentMedal = new SysEventStudentMedal();
                                studentMedal.setMedalId(sysEvent.getMedalId());
                                studentMedal.setStudentId(studentId);
                                studentMedal.setSemesterId(sysSemesterManage.getSemesterId());
                                studentMedalList.add(studentMedal);
                            }
                        }
                    }

                    if (!studentMedalList.isEmpty()) {
                        studentMapper.insertStudentMedal(studentMedalList);
                    }
                }
            }
            sysEventsMapper.updateSysEventsStatus(eventsResult);
        }
        List<SysEventCycle> sysEventCycles = sysEventsMapper.cycleRecords(param);
        if (!sysEventCycles.isEmpty()) {
            for (SysEventCycle sysEventCycle : sysEventCycles) {
                LocalDateTime startTime = sysEventCycle.getStartTime();
                LocalDateTime endTime = sysEventCycle.getEndTime();
                if (LocalDateTimeComparisonUtil.isBefore(now, endTime) && LocalDateTimeComparisonUtil.isAfter(now, startTime)) {
                    sysEventCycle.setStatus("1");
                    result.add(sysEventCycle);
                }
                if (LocalDateTimeComparisonUtil.isAfter(now, endTime)) {
                    sysEventCycle.setStatus("2");
                    result.add(sysEventCycle);
                }
            }
            sysEventsMapper.updateSysEventsCycle(result);
        }
    }

    @Override
    public void autoChangeClasses() {
        //查询六年级的学生然后删除
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);


        List<Long> studentIds = studentMapper.selectStudentIdByDeptName("六年级");
        if (!studentIds.isEmpty()) {
            studentMapper.deleteSysStudentByStudentIds(studentIds.toArray(new Long[studentIds.size()]));
            List<Long> patriarchIds = studentMapper.selectUserByStudentIds(studentIds);
            if (!patriarchIds.isEmpty()) {
                studentMapper.deleteStudentPatriarchByPatriarchIds(patriarchIds);
                sysUserMapper.deleteUserByIds(patriarchIds.toArray(new Long[patriarchIds.size()]));
            }
        }
        List<SysStudentDeptHistory> studentDeptHistories = new ArrayList<>();

        for (int i = 5; i > 0; i--) {
            String convert = NumberToChineseUtil.convert(i);
            //查询对应年级下的班级信息
            List<SysDept> depts = studentMapper.selectclassByGradeName(convert + "年级");
            for (SysDept dept : depts) {
                //五年级1班
                String deptName = dept.getDeptName();
                //六年级1班
                String newClass = NumberToChineseUtil.convert(i + 1) + deptName.substring(1);
                Long newClassId = studentMapper.selectIdByDeptName(newClass);

                //通过班级id查询'五年级1班'的学生
                List<Long> ids = new ArrayList<>();
                ids.add(dept.getDeptId());
                List<Long> studentId = studentMapper.selectSutdentByClassIds(ids);
                if (!studentId.isEmpty()) {
                    studentMapper.updateStudentDeptByMap(studentId, newClassId);

                }

                for (Long aLong : studentId) {
                    SysStudentDeptHistory studentDeptHistory = new SysStudentDeptHistory();
                    studentDeptHistory.setDeptId(dept.getDeptId());
                    studentDeptHistory.setSemesterId(sysSemesterManage.getSemesterId());
                    studentDeptHistory.setStudentId(aLong);
                    studentDeptHistories.add(studentDeptHistory);
                }
            }
        }
        studentMapper.insertStudentDeptHistory(studentDeptHistories);


    }


}
