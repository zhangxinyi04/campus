package com.z.system.service;

import java.util.List;
import java.util.Map;

import com.z.common.core.domain.AjaxResult;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentComment;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.domain.vo.StatisticVo;

/**
 * 学生信息Service接口
 * 
 * @author z
 * @date 2025-03-08
 */
public interface ISysStudentService 
{
    /**
     * 查询学生信息
     * 
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public SysStudent selectSysStudentByStudentId(Long studentId);

    /**
     * 查询学生信息列表
     * 
     * @param sysStudent 学生信息
     * @return 学生信息集合
     */
    public List<SysStudent> selectSysStudentList(SysStudent sysStudent);

    /**
     * 新增学生信息
     * 
     * @param sysStudent 学生信息
     * @return 结果
     */
    public int insertSysStudent(SysStudent sysStudent);

    /**
     * 修改学生信息
     * 
     * @param sysStudent 学生信息
     * @return 结果
     */
    public int updateSysStudent(SysStudent sysStudent);

    /**
     * 批量删除学生信息
     * 
     * @param studentIds 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteSysStudentByStudentIds(Long[] studentIds);

    /**
     * 删除学生信息信息
     * 
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteSysStudentByStudentId(Long studentId);

    AjaxResult addClassEvaluation(List<SysStudentMarkRecords> studentMarkRecordsList);

    List<SysStudent> selectSysStudentListAndDept();


    SysStudent selectSysStudentIdByNameAndDept(String studentName, String deptName);

    List<SysStudentMarkRecords> selectStudentBonusCount(SysStudentMarkRecords markRecords);

    List<SysStudentMarkRecords> selectStudentBonusCountByStudentId(SysStudentMarkRecords markRecords);

    List<SysStudentMarkRecords> selectStudentDetail(SysStudentMarkRecords markRecords);

    List<StatisticVo> selectEvaluationDetail(SysStudentMarkRecords markRecords);

    int removeMarkRecords(SysStudentMarkRecords markRecords);

    AjaxResult termEvaluation(SysStudentComment studentComment);

    int updateSysStudentBatch(List<SysStudent> sysStudent);

    Map<String,Object> selectStudentReportById(Long studentId, String semesterId);

    Integer selectStudentCountIndex();


}
