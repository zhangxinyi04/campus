package com.z.system.service;

import java.util.List;
import com.z.system.domain.SysUserDevelop;

/**
 * 学生发展管理Service接口
 * 
 * @author z
 * @date 2025-03-18
 */
public interface ISysUserDevelopService 
{
    /**
     * 查询学生发展管理
     * 
     * @param developId 学生发展管理主键
     * @return 学生发展管理
     */
    public SysUserDevelop selectSysUserDevelopByDevelopId(Long developId);

    /**
     * 查询学生发展管理列表
     * 
     * @param sysUserDevelop 学生发展管理
     * @return 学生发展管理集合
     */
    public List<SysUserDevelop> selectSysUserDevelopList(SysUserDevelop sysUserDevelop);

    /**
     * 新增学生发展管理
     * 
     * @param sysUserDevelop 学生发展管理
     * @return 结果
     */
    public int insertSysUserDevelop(SysUserDevelop sysUserDevelop);

    /**
     * 修改学生发展管理
     * 
     * @param sysUserDevelop 学生发展管理
     * @return 结果
     */
    public int updateSysUserDevelop(SysUserDevelop sysUserDevelop);

    /**
     * 批量删除学生发展管理
     * 
     * @param developIds 需要删除的学生发展管理主键集合
     * @return 结果
     */
    public int deleteSysUserDevelopByDevelopIds(Long[] developIds);

    /**
     * 删除学生发展管理信息
     * 
     * @param developId 学生发展管理主键
     * @return 结果
     */
    public int deleteSysUserDevelopByDevelopId(Long developId);

    List<SysUserDevelop> selectSysUserDevelopStatistics(SysUserDevelop userDevelop);

    List<SysUserDevelop> selectDevelopsIndex(String semesterId);
}
