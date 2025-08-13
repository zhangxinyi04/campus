package com.z.system.service.impl;

import java.util.List;

import com.z.common.core.domain.entity.SysDept;
import com.z.common.core.domain.entity.SysUser;
import com.z.common.core.domain.model.LoginUser;
import com.z.common.utils.DateUtils;
import com.z.common.utils.SecurityUtils;
import com.z.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysCourseMapper;
import com.z.common.core.domain.entity.SysCourse;
import com.z.system.service.ISysCourseService;

/**
 * 课程Service业务层处理
 *
 * @author z
 * @date 2025-03-04
 */
@Service
public class SysCourseServiceImpl implements ISysCourseService {
    @Autowired
    private SysCourseMapper sysCourseMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询课程
     *
     * @param courseId 课程主键
     * @return 课程
     */
    @Override
    public SysCourse selectSysCourseByCourseId(Long courseId) {
        return sysCourseMapper.selectSysCourseByCourseId(courseId);
    }

    /**
     * 查询课程列表
     *
     * @param sysCourse 课程
     * @return 课程
     */
    @Override
    public List<SysCourse> selectSysCourseList(SysCourse sysCourse) {
        return sysCourseMapper.selectSysCourseList(sysCourse);
    }

    /**
     * 新增课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    @Override
    public int insertSysCourse(SysCourse sysCourse) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        sysCourse.setCreateBy(user.getUserName());
        sysCourse.setCreateTime(DateUtils.getNowDate());
        return sysCourseMapper.insertSysCourse(sysCourse);
    }

    /**
     * 修改课程
     *
     * @param sysCourse 课程
     * @return 结果
     */
    @Override
    public int updateSysCourse(SysCourse sysCourse) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        sysCourse.setUpdateBy(user.getUserName());
        sysCourse.setUpdateTime(DateUtils.getNowDate());
        return sysCourseMapper.updateSysCourse(sysCourse);
    }

    /**
     * 批量删除课程
     *
     * @param courseIds 需要删除的课程主键
     * @return 结果
     */
    @Override
    public int deleteSysCourseByCourseIds(Long[] courseIds) {
        return sysCourseMapper.deleteSysCourseByCourseIds(courseIds);
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程主键
     * @return 结果
     */
    @Override
    public int deleteSysCourseByCourseId(Long courseId) {
        return sysCourseMapper.deleteSysCourseByCourseId(courseId);
    }

    @Override
    public List<SysDept> gradeList() {
        SysDept dept = new SysDept();
        dept.setAncestors("0");
        return deptMapper.selectDeptList(dept);
    }

    @Override
    public List<SysCourse> selectCourseByUserId(Long userId) {
        return sysCourseMapper.selectCourseByUserId(userId);
    }

    @Override
    public Integer selectCourseCountIndex() {
        return sysCourseMapper.selectCourseCountIndex();
    }
}
