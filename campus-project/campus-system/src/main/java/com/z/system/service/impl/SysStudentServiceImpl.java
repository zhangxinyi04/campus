package com.z.system.service.impl;

import java.util.*;

import com.z.common.core.domain.AjaxResult;
import com.z.common.core.domain.entity.SysDept;
import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.common.utils.DateUtils;
import com.z.common.utils.SecurityUtils;
import com.z.system.domain.*;
import com.z.system.domain.vo.StatisticVo;
import com.z.system.mapper.*;
import com.z.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生信息Service业务层处理
 *
 * @author z
 * @date 2025-03-08
 */
@Service
public class SysStudentServiceImpl implements ISysStudentService {
    @Autowired
    private SysStudentMapper sysStudentMapper;

    @Autowired
    private SysSemesterManageMapper semesterManageMapper;
    @Autowired
    private ISysStudentCommentService sysStudentCommentService;
    @Autowired
    private SysStudentPeScoreMapper sysStudentPeScoreMapper;
    @Autowired
    private SysStudentScoreMapper sysStudentScoreMapper;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysEventsService eventsService;
    @Autowired
    private ISysUserDevelopService userDevelopService;
    @Autowired
    private SysStudentCommentMapper commentMapper;

    /**
     * 查询学生信息
     *
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    @Override
    public SysStudent selectSysStudentByStudentId(Long studentId) {
        return sysStudentMapper.selectSysStudentByStudentId(studentId);
    }

    /**
     * 查询学生信息列表
     *
     * @param sysStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<SysStudent> selectSysStudentList(SysStudent sysStudent) {
        return sysStudentMapper.selectSysStudentList(sysStudent);
    }

    /**
     * 新增学生信息
     *
     * @param sysStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertSysStudent(SysStudent sysStudent) {
        sysStudent.setCreateTime(DateUtils.getNowDate());
        int i = sysStudentMapper.insertSysStudent(sysStudent);
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);

        List<SysStudentMarkRecords> studentMarkRecordsList = new ArrayList<>();
        SysStudentMarkRecords sysStudentMarkRecords = new SysStudentMarkRecords();
        sysStudentMarkRecords.setStudentId(sysStudent.getStudentId());
        sysStudentMarkRecords.setMark(0L);
        sysStudentMarkRecords.setSemesterId(sysSemesterManage.getSemesterId());
        studentMarkRecordsList.add(sysStudentMarkRecords);
        sysStudentMapper.insertStudentMark(studentMarkRecordsList);
        return i;
    }

    /**
     * 修改学生信息
     *
     * @param sysStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateSysStudent(SysStudent sysStudent) {
        sysStudent.setUpdateTime(DateUtils.getNowDate());
        return sysStudentMapper.updateSysStudent(sysStudent);
    }

    /**
     * 批量删除学生信息
     *
     * @param studentIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentByStudentIds(Long[] studentIds) {
        List<Long> patriarchs = sysStudentMapper.selectPatriarchByStudentIds(studentIds);
        sysStudentMapper.deletePatriarchByPatriarch(patriarchs);
        return sysStudentMapper.deleteSysStudentByStudentIds(studentIds);
    }

    /**
     * 删除学生信息信息
     *
     * @param studentId 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentByStudentId(Long studentId) {
        return sysStudentMapper.deleteSysStudentByStudentId(studentId);
    }

    @Override
    @Transactional
    public AjaxResult addClassEvaluation(List<SysStudentMarkRecords> studentMarkRecordsList) {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        for (SysStudentMarkRecords sysStudentMarkRecords : studentMarkRecordsList) {
            sysStudentMarkRecords.setSemesterId(sysSemesterManage.getSemesterId());
            sysStudentMarkRecords.setTeacherId(userId);
            sysStudentMarkRecords.setCreateTime(new Date());
        }


        sysStudentMapper.addClassEvaluation(studentMarkRecordsList);
        sysStudentMapper.addStudentMark(studentMarkRecordsList);
        return AjaxResult.success();
    }

    @Override
    public List<SysStudent> selectSysStudentListAndDept() {
        return sysStudentMapper.selectSysStudentListAndDept();
    }

    @Override
    public SysStudent selectSysStudentIdByNameAndDept(String studentName, String deptName) {
        return sysStudentMapper.selectSysStudentIdByNameAndDept(studentName, deptName);
    }

    @Override
    public List<SysStudentMarkRecords> selectStudentBonusCount(SysStudentMarkRecords markRecords) {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        return sysStudentMapper.selectStudentBonusCount(markRecords);
    }

    @Override
    public List<SysStudentMarkRecords> selectStudentBonusCountByStudentId(SysStudentMarkRecords markRecords) {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        return sysStudentMapper.selectStudentBonusCountByStudentId(markRecords);
    }

    @Override
    public List<SysStudentMarkRecords> selectStudentDetail(SysStudentMarkRecords markRecords) {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        markRecords.setTeacherId(userId);
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        return sysStudentMapper.selectStudentDetail(markRecords);
    }

    @Override
    public List<StatisticVo> selectEvaluationDetail(SysStudentMarkRecords markRecords) {
        Long userId = SecurityUtils.getLoginUser().getUserId();
        markRecords.setTeacherId(userId);
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        return sysStudentMapper.selectEvaluationDetail(markRecords);
    }

    @Override
    @Transactional
    public int removeMarkRecords(SysStudentMarkRecords markRecords) {
        List<SysStudentMarkRecords> sysStudentMarkRecords = new ArrayList<>();

        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        markRecords.setMark(-markRecords.getMark());
        markRecords.setSemesterId(sysSemesterManage.getSemesterId());
        sysStudentMarkRecords.add(markRecords);

        sysStudentMapper.addStudentMark(sysStudentMarkRecords);
        return sysStudentMapper.removeMarkRecords(markRecords.getRecordId());
    }

    @Override
    public AjaxResult termEvaluation(SysStudentComment studentComment) {
        boolean flag = false;
        Long[] postIds = SecurityUtils.getLoginUser().getUser().getPostIds();
        for (int i = 0; i < postIds.length; i++) {
            if (postIds[i] == 2L) {
                flag = true;
            }
        }
        if (!flag) {
            return AjaxResult.error("您不是班主任，无权操作");
        }
        sysStudentCommentService.updateSysStudentComment(studentComment);
        return AjaxResult.success();
    }

    /**
     * 批量修改学生信息
     *
     * @param sysStudent 学生信息
     * @return 结果
     */
    @Override
    public int updateSysStudentBatch(List<SysStudent> sysStudent) {
        return sysStudentMapper.updateSysStudentBatch(sysStudent);
    }

    @Override
    public Map<String, Object> selectStudentReportById(Long studentId, String semesterId) {
        Map<String, Object> map = new HashMap<>();
        //查询学生信息
        SysStudent sysStudent = sysStudentMapper.selectSysStudentByStudentId(studentId);
        Long deptId = sysStudentMapper.selectDeptHistory(semesterId, studentId);
        if (deptId == null) {
            deptId = sysStudent.getDeptId();
        }
        SysDept dept = deptService.selectDeptById(deptId);
        sysStudent.setDept(dept);
        map.put("studentInfo", sysStudent);
        //查询学科成绩
        SysStudentScore sysStudentScore = new SysStudentScore();
        sysStudentScore.setStudentId(studentId);
        sysStudentScore.setSemesterId(semesterId);
        List<SysStudentScore> sysStudentScores = sysStudentScoreMapper.selectSysStudentScoreList(sysStudentScore);
        map.put("studentScores", sysStudentScores);
        //查询体育成绩
        SysStudentPeScore studentPeScore = new SysStudentPeScore();
        studentPeScore.setStudentId(studentId);
        studentPeScore.setSemesterId(semesterId);
        List<SysStudentPeScore> sysStudentPeScores = sysStudentPeScoreMapper.selectSysStudentPeScoreList(studentPeScore);
        map.put("studentPeScore", sysStudentPeScores);
        //查询活动奖章
        List<SysEventMedal> studentMedalList = sysStudentMapper.selecteventStudentMedalByStudentId(studentId, semesterId);
        map.put("studentMedalList", studentMedalList);

        SysEvents events = new SysEvents();
        events.setSemesterId(semesterId);

        List<SysEvents> sysEvents = new ArrayList<>();
        List<SysUserDevelop> userDevelops = new ArrayList<>();

        //封装写实记录
        SysUserDevelop userDevelop = new SysUserDevelop();
        userDevelop.setStatus("1");
        userDevelop.setSemesterId(semesterId);
        userDevelop.setStudentId(studentId);
        userDevelop.setDevelopIndex("0");

        //品德发展活动次数
        events.setEventTagType(SysEvents.MORAL_DEVELOPMENT);
        sysEvents = eventsService.selectCountByEventTagType(events);
        map.put("moralDevelopment", sysEvents);


        userDevelops = userDevelopService.selectSysUserDevelopStatistics(userDevelop);
        map.put("moralRealisticRecord", userDevelops);


        //学业发展活动次数
        events.setEventTagType(SysEvents.ACADEMIC_DEVELOPMENT);
        sysEvents = eventsService.selectCountByEventTagType(events);
        map.put("academicDevelopment", sysEvents);
        userDevelop.setDevelopIndex("1");
        userDevelops = userDevelopService.selectSysUserDevelopStatistics(userDevelop);
        map.put("academicRealisticRecord", userDevelops);

        //身心发展活动次数
        events.setEventTagType(SysEvents.PHYSICAL_AND_MENTAL_DEVELOPMENT);
        sysEvents = eventsService.selectCountByEventTagType(events);
        map.put("physicalAndMentalDevelopment", sysEvents);
        List<SysStudentMarkRecords> sysStudentMarkRecords = sysStudentMapper.selectCourseCount(semesterId, studentId);
        map.put("sysStudentMarkRecords", sysStudentMarkRecords);
        userDevelop.setDevelopIndex("2");
        userDevelops = userDevelopService.selectSysUserDevelopStatistics(userDevelop);
        map.put("physicalAndMentalRealisticRecord", userDevelops);
        //艺术素养活动次数
        events.setEventTagType(SysEvents.ARTISTIC_LITERACY);
        sysEvents = eventsService.selectCountByEventTagType(events);
        map.put("artisticLiteracy", sysEvents);
        userDevelop.setDevelopIndex("3");
        userDevelops = userDevelopService.selectSysUserDevelopStatistics(userDevelop);
        map.put("artisticLiteracyRealisticRecord", userDevelops);

        //劳动与社会实践
        events.setEventTagType(SysEvents.LABOR_AND_SOCIAL_PRACTICE);
        sysEvents = eventsService.selectCountByEventTagType(events);
        map.put("laborAndSocialPractice", sysEvents);

        userDevelop.setDevelopIndex("4");
        userDevelops = userDevelopService.selectSysUserDevelopStatistics(userDevelop);
        map.put("laborAndSocialPracticeRealisticRecord", userDevelops);

        //评语
        SysStudentComment sysStudentComment = new SysStudentComment();
        sysStudentComment.setStudentId(studentId);
        sysStudentComment.setSemesterId(semesterId);
        List<SysStudentComment> sysStudentComments = commentMapper.selectSysStudentCommentList(sysStudentComment);
        map.put("sysStudentComments",sysStudentComments);
        return map;
    }

    @Override
    public Integer selectStudentCountIndex() {
        return sysStudentMapper.selectStudentCountIndex();
    }
}
