package com.z.system.service;

import java.util.List;
import com.z.system.domain.SysStudentPeScore;

/**
 * 学生体育成绩管理Service接口
 * 
 * @author z
 * @date 2025-04-10
 */
public interface ISysStudentPeScoreService 
{
    /**
     * 查询学生体育成绩管理
     * 
     * @param studentScoreId 学生体育成绩管理主键
     * @return 学生体育成绩管理
     */
    public SysStudentPeScore selectSysStudentPeScoreByStudentScoreId(Long studentScoreId);

    /**
     * 查询学生体育成绩管理列表
     * 
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 学生体育成绩管理集合
     */
    public List<SysStudentPeScore> selectSysStudentPeScoreList(SysStudentPeScore sysStudentPeScore);

    /**
     * 新增学生体育成绩管理
     * 
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 结果
     */
    public int insertSysStudentPeScore(SysStudentPeScore sysStudentPeScore);

    /**
     * 修改学生体育成绩管理
     * 
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 结果
     */
    public int updateSysStudentPeScore(SysStudentPeScore sysStudentPeScore);

    /**
     * 批量删除学生体育成绩管理
     * 
     * @param studentScoreIds 需要删除的学生体育成绩管理主键集合
     * @return 结果
     */
    public int deleteSysStudentPeScoreByStudentScoreIds(Long[] studentScoreIds);

    /**
     * 删除学生体育成绩管理信息
     * 
     * @param studentScoreId 学生体育成绩管理主键
     * @return 结果
     */
    public int deleteSysStudentPeScoreByStudentScoreId(Long studentScoreId);

    void insertSysStudentPeScores(List<SysStudentPeScore> studentPeScores);

    void deleteBySemesterId(String semesterId);

    List<SysStudentPeScore> selectIndexScorePe(String semesterId);
}
