package com.z.system.mapper;

import java.util.List;
import java.util.Map;

import com.z.common.core.domain.entity.SysDept;
import com.z.system.domain.*;
import com.z.system.domain.vo.StatisticVo;
import org.apache.ibatis.annotations.Param;

/**
 * 学生信息Mapper接口
 *
 * @author z
 * @date 2025-03-08
 */
public interface SysStudentMapper {
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
     * 删除学生信息
     *
     * @param studentId 学生信息主键
     * @return 结果
     */
    public int deleteSysStudentByStudentId(Long studentId);

    /**
     * 批量删除学生信息
     *
     * @param studentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysStudentByStudentIds(Long[] studentIds);


    int addClassEvaluation(List<SysStudentMarkRecords> studentMarkRecordsList);

    void addStudentMark(List<SysStudentMarkRecords> studentMarkRecordsList);

    List<Long> selectPatriarchByStudentIds(Long[] studentIds);

    void deletePatriarchByPatriarch(List<Long> patriarchs);

    void insertStudentMark(List<SysStudentMarkRecords> sysStudentMarkRecords);

    List<Long> selectSutdentByGradeIds(List<Long> gradeIds);

    List<Long> selectSutdentByClassIds(List<Long> classIds);

    void insertStudentMedal(List<SysEventStudentMedal> studentMedalList);

    List<SysStudent> selectSysStudentListAndDept();


    SysStudent selectSysStudentIdByNameAndDept(@Param("studentName") String studentName, @Param("deptName") String deptName);

    List<SysStudentMarkRecords> selectStudentBonusCount(SysStudentMarkRecords markRecords);

    List<SysStudentMarkRecords> selectStudentBonusCountByStudentId(SysStudentMarkRecords markRecords);

    List<SysStudentMarkRecords> selectStudentDetail(SysStudentMarkRecords markRecords);

    List<StatisticVo> selectEvaluationDetail(SysStudentMarkRecords markRecords);

    int removeMarkRecords(Long id);

    List<Long> selectStudentIdByDeptName(String name);

    List<Long> selectUserByStudentIds(List<Long> studentIds);

    void deleteStudentPatriarchByPatriarchIds(List<Long> patriarchIds);

    List<SysDept> selectclassByGradeName(String gradeName);

    Long selectIdByDeptName(String newClass);


    void updateStudentDeptByMap(@Param("studentId") List<Long> studentId, @Param("newClassId") Long newClassId);

    int updateSysStudentBatch(List<SysStudent> sysStudent);

    List<SysEventMedal> selecteventStudentMedalByStudentId(@Param("studentId") Long studentId, @Param("semesterId") String semesterId);

    void insertStudentDeptHistory(List<SysStudentDeptHistory> studentDeptHistories);

    Long selectDeptHistory(@Param("semesterId") String semesterId, @Param("studentId") Long studentId);

    List<SysStudentMarkRecords> selectCourseCount(@Param("semesterId") String semesterId, @Param("studentId") Long studentId);

    Integer selectStudentCountIndex();

    Long selectMarkByStudentId(@Param("semesterId") String semesterId, @Param("studentId") Long studentId);


}
