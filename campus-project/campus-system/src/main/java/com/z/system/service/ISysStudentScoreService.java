package com.z.system.service;

import java.util.List;
import com.z.system.domain.SysStudentScore;
import com.z.system.domain.vo.StudentScoreVo;

/**
 * 学生成绩管理Service接口
 * 
 * @author z
 * @date 2025-04-10
 */
public interface ISysStudentScoreService 
{
    /**
     * 查询学生成绩管理
     * 
     * @param studentScoreId 学生成绩管理主键
     * @return 学生成绩管理
     */
    public SysStudentScore selectSysStudentScoreByStudentScoreId(Long studentScoreId);

    /**
     * 查询学生成绩管理列表
     * 
     * @param sysStudentScore 学生成绩管理
     * @return 学生成绩管理集合
     */
    public List<SysStudentScore> selectSysStudentScoreList(SysStudentScore sysStudentScore);

    /**
     * 新增学生成绩管理
     * 
     * @param sysStudentScore 学生成绩管理
     * @return 结果
     */
    public int insertSysStudentScore(SysStudentScore sysStudentScore);

    /**
     * 修改学生成绩管理
     * 
     * @param sysStudentScore 学生成绩管理
     * @return 结果
     */
    public int updateSysStudentScore(SysStudentScore sysStudentScore);

    /**
     * 批量删除学生成绩管理
     * 
     * @param studentScoreIds 需要删除的学生成绩管理主键集合
     * @return 结果
     */
    public int deleteSysStudentScoreByStudentScoreIds(Long[] studentScoreIds);

    /**
     * 删除学生成绩管理信息
     * 
     * @param studentScoreId 学生成绩管理主键
     * @return 结果
     */
    public int deleteSysStudentScoreByStudentScoreId(Long studentScoreId);

    void insertSysStudentScores(List<SysStudentScore> studentScores);

    void deleteBySemesterId(String semesterId);

    List<StudentScoreVo> selectIndexScore(String semesterId);
}
