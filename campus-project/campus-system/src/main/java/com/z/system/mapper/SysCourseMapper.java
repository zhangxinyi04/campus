package com.z.system.mapper;

import java.util.List;

import com.z.common.core.domain.entity.SysCourse;

/**
 * 课程Mapper接口
 * 
 * @author z
 * @date 2025-03-04
 */
public interface SysCourseMapper 
{
    /**
     * 查询课程
     * 
     * @param courseId 课程主键
     * @return 课程
     */
    public SysCourse selectSysCourseByCourseId(Long courseId);

    /**
     * 查询课程列表
     * 
     * @param sysCourse 课程
     * @return 课程集合
     */
    public List<SysCourse> selectSysCourseList(SysCourse sysCourse);

    /**
     * 新增课程
     * 
     * @param sysCourse 课程
     * @return 结果
     */
    public int insertSysCourse(SysCourse sysCourse);

    /**
     * 修改课程
     * 
     * @param sysCourse 课程
     * @return 结果
     */
    public int updateSysCourse(SysCourse sysCourse);

    /**
     * 删除课程
     * 
     * @param courseId 课程主键
     * @return 结果
     */
    public int deleteSysCourseByCourseId(Long courseId);

    /**
     * 批量删除课程
     * 
     * @param courseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCourseByCourseIds(Long[] courseIds);


    List<SysCourse> selectCourseByUserId(Long userId);

    Integer selectCourseCountIndex();


//    List<SysCourse> selectCoursesByUserId(Long userId);
}
