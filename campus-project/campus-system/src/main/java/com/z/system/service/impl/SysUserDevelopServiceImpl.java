package com.z.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.mapper.SysStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysUserDevelopMapper;
import com.z.system.domain.SysUserDevelop;
import com.z.system.service.ISysUserDevelopService;

/**
 * 学生发展管理Service业务层处理
 *
 * @author z
 * @date 2025-03-18
 */
@Service
public class SysUserDevelopServiceImpl implements ISysUserDevelopService {
    @Autowired
    private SysUserDevelopMapper sysUserDevelopMapper;
    @Autowired
    private SysStudentMapper studentMapper;

    /**
     * 查询学生发展管理
     *
     * @param developId 学生发展管理主键
     * @return 学生发展管理
     */
    @Override
    public SysUserDevelop selectSysUserDevelopByDevelopId(Long developId) {
        return sysUserDevelopMapper.selectSysUserDevelopByDevelopId(developId);
    }

    /**
     * 查询学生发展管理列表
     *
     * @param sysUserDevelop 学生发展管理
     * @return 学生发展管理
     */
    @Override
    public List<SysUserDevelop> selectSysUserDevelopList(SysUserDevelop sysUserDevelop) {
        return sysUserDevelopMapper.selectSysUserDevelopList(sysUserDevelop);
    }

    /**
     * 新增学生发展管理
     *
     * @param sysUserDevelop 学生发展管理
     * @return 结果
     */
    @Override
    public int insertSysUserDevelop(SysUserDevelop sysUserDevelop) {
        return sysUserDevelopMapper.insertSysUserDevelop(sysUserDevelop);
    }

    /**
     * 修改学生发展管理
     *
     * @param sysUserDevelop 学生发展管理
     * @return 结果
     */
    @Override
    public int updateSysUserDevelop(SysUserDevelop sysUserDevelop) {
        if ("1".equals(sysUserDevelop.getStatus())) {
            List<SysStudentMarkRecords> markRecords = new ArrayList<>();
            SysStudentMarkRecords studentMarkRecords = new SysStudentMarkRecords();
            studentMarkRecords.setMark(sysUserDevelop.getMark());
            studentMarkRecords.setStudentId(sysUserDevelop.getStudentId());
            studentMarkRecords.setSemesterId(sysUserDevelop.getSemesterId());
            markRecords.add(studentMarkRecords);
            studentMapper.addStudentMark(markRecords);
        }
        return sysUserDevelopMapper.updateSysUserDevelop(sysUserDevelop);
    }

    /**
     * 批量删除学生发展管理
     *
     * @param developIds 需要删除的学生发展管理主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDevelopByDevelopIds(Long[] developIds) {
        return sysUserDevelopMapper.deleteSysUserDevelopByDevelopIds(developIds);
    }

    /**
     * 删除学生发展管理信息
     *
     * @param developId 学生发展管理主键
     * @return 结果
     */
    @Override
    public int deleteSysUserDevelopByDevelopId(Long developId) {
        return sysUserDevelopMapper.deleteSysUserDevelopByDevelopId(developId);
    }

    @Override
    public List<SysUserDevelop> selectSysUserDevelopStatistics(SysUserDevelop userDevelop) {
        return sysUserDevelopMapper.selectSysUserDevelopStatistics(userDevelop);
    }

    @Override
    public List<SysUserDevelop> selectDevelopsIndex(String semesterId) {
        return sysUserDevelopMapper.selectDevelopsIndex(semesterId);
    }
}
