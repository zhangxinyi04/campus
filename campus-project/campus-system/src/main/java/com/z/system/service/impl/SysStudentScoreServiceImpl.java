package com.z.system.service.impl;

import java.util.List;

import com.z.system.domain.vo.StudentScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysStudentScoreMapper;
import com.z.system.domain.SysStudentScore;
import com.z.system.service.ISysStudentScoreService;

/**
 * 学生成绩管理Service业务层处理
 *
 * @author z
 * @date 2025-04-10
 */
@Service
public class SysStudentScoreServiceImpl implements ISysStudentScoreService {
    @Autowired
    private SysStudentScoreMapper sysStudentScoreMapper;

    /**
     * 查询学生成绩管理
     *
     * @param studentScoreId 学生成绩管理主键
     * @return 学生成绩管理
     */
    @Override
    public SysStudentScore selectSysStudentScoreByStudentScoreId(Long studentScoreId) {
        return sysStudentScoreMapper.selectSysStudentScoreByStudentScoreId(studentScoreId);
    }

    /**
     * 查询学生成绩管理列表
     *
     * @param sysStudentScore 学生成绩管理
     * @return 学生成绩管理
     */
    @Override
    public List<SysStudentScore> selectSysStudentScoreList(SysStudentScore sysStudentScore) {
        return sysStudentScoreMapper.selectSysStudentScoreList(sysStudentScore);
    }

    /**
     * 新增学生成绩管理
     *
     * @param sysStudentScore 学生成绩管理
     * @return 结果
     */
    @Override
    public int insertSysStudentScore(SysStudentScore sysStudentScore) {
        return sysStudentScoreMapper.insertSysStudentScore(sysStudentScore);
    }

    /**
     * 修改学生成绩管理
     *
     * @param sysStudentScore 学生成绩管理
     * @return 结果
     */
    @Override
    public int updateSysStudentScore(SysStudentScore sysStudentScore) {
        return sysStudentScoreMapper.updateSysStudentScore(sysStudentScore);
    }

    /**
     * 批量删除学生成绩管理
     *
     * @param studentScoreIds 需要删除的学生成绩管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentScoreByStudentScoreIds(Long[] studentScoreIds) {
        return sysStudentScoreMapper.deleteSysStudentScoreByStudentScoreIds(studentScoreIds);
    }

    /**
     * 删除学生成绩管理信息
     *
     * @param studentScoreId 学生成绩管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentScoreByStudentScoreId(Long studentScoreId) {
        return sysStudentScoreMapper.deleteSysStudentScoreByStudentScoreId(studentScoreId);
    }

    @Override
    public void insertSysStudentScores(List<SysStudentScore> studentScores) {
        sysStudentScoreMapper.insertSysStudentScores(studentScores);

    }

    @Override
    public void deleteBySemesterId(String semesterId) {
        sysStudentScoreMapper.deleteBySemesterId(semesterId);

    }

    @Override
    public List<StudentScoreVo> selectIndexScore(String semesterId) {
        return  sysStudentScoreMapper.selectIndexScore(semesterId);
    }
}
