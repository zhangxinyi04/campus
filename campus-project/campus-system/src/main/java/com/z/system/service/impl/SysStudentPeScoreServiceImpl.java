package com.z.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysStudentPeScoreMapper;
import com.z.system.domain.SysStudentPeScore;
import com.z.system.service.ISysStudentPeScoreService;

/**
 * 学生体育成绩管理Service业务层处理
 *
 * @author z
 * @date 2025-04-10
 */
@Service
public class SysStudentPeScoreServiceImpl implements ISysStudentPeScoreService {
    @Autowired
    private SysStudentPeScoreMapper sysStudentPeScoreMapper;

    /**
     * 查询学生体育成绩管理
     *
     * @param studentScoreId 学生体育成绩管理主键
     * @return 学生体育成绩管理
     */
    @Override
    public SysStudentPeScore selectSysStudentPeScoreByStudentScoreId(Long studentScoreId) {
        return sysStudentPeScoreMapper.selectSysStudentPeScoreByStudentScoreId(studentScoreId);
    }

    /**
     * 查询学生体育成绩管理列表
     *
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 学生体育成绩管理
     */
    @Override
    public List<SysStudentPeScore> selectSysStudentPeScoreList(SysStudentPeScore sysStudentPeScore) {
        return sysStudentPeScoreMapper.selectSysStudentPeScoreList(sysStudentPeScore);
    }

    /**
     * 新增学生体育成绩管理
     *
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 结果
     */
    @Override
    public int insertSysStudentPeScore(SysStudentPeScore sysStudentPeScore) {
        return sysStudentPeScoreMapper.insertSysStudentPeScore(sysStudentPeScore);
    }

    /**
     * 修改学生体育成绩管理
     *
     * @param sysStudentPeScore 学生体育成绩管理
     * @return 结果
     */
    @Override
    public int updateSysStudentPeScore(SysStudentPeScore sysStudentPeScore) {
        return sysStudentPeScoreMapper.updateSysStudentPeScore(sysStudentPeScore);
    }

    /**
     * 批量删除学生体育成绩管理
     *
     * @param studentScoreIds 需要删除的学生体育成绩管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentPeScoreByStudentScoreIds(Long[] studentScoreIds) {
        return sysStudentPeScoreMapper.deleteSysStudentPeScoreByStudentScoreIds(studentScoreIds);
    }

    /**
     * 删除学生体育成绩管理信息
     *
     * @param studentScoreId 学生体育成绩管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentPeScoreByStudentScoreId(Long studentScoreId) {
        return sysStudentPeScoreMapper.deleteSysStudentPeScoreByStudentScoreId(studentScoreId);
    }

    @Override
    public void insertSysStudentPeScores(List<SysStudentPeScore> studentPeScores) {
         sysStudentPeScoreMapper.insertSysStudentPeScores(studentPeScores);

    }

    @Override
    public void deleteBySemesterId(String semesterId) {
        sysStudentPeScoreMapper.deleteBySemesterId(semesterId);
    }

    @Override
    public List<SysStudentPeScore> selectIndexScorePe(String semesterId) {
        return sysStudentPeScoreMapper.selectIndexScorePe(semesterId);
    }
}
