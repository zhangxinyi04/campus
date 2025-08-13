package com.z.system.mapper;

import java.util.List;
import com.z.system.domain.SysStudentComment;

/**
 * 学生家长评语管理Mapper接口
 * 
 * @author z
 * @date 2025-04-14
 */
public interface SysStudentCommentMapper 
{
    /**
     * 查询学生家长评语管理
     * 
     * @param studentCommentId 学生家长评语管理主键
     * @return 学生家长评语管理
     */
    public SysStudentComment selectSysStudentCommentByStudentCommentId(Long studentCommentId);

    /**
     * 查询学生家长评语管理列表
     * 
     * @param sysStudentComment 学生家长评语管理
     * @return 学生家长评语管理集合
     */
    public List<SysStudentComment> selectSysStudentCommentList(SysStudentComment sysStudentComment);

    /**
     * 新增学生家长评语管理
     * 
     * @param sysStudentComment 学生家长评语管理
     * @return 结果
     */
    public int insertSysStudentComment(SysStudentComment sysStudentComment);

    /**
     * 修改学生家长评语管理
     * 
     * @param sysStudentComment 学生家长评语管理
     * @return 结果
     */
    public int updateSysStudentComment(SysStudentComment sysStudentComment);

    /**
     * 删除学生家长评语管理
     * 
     * @param studentCommentId 学生家长评语管理主键
     * @return 结果
     */
    public int deleteSysStudentCommentByStudentCommentId(Long studentCommentId);

    /**
     * 批量删除学生家长评语管理
     * 
     * @param studentCommentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysStudentCommentByStudentCommentIds(Long[] studentCommentIds);
}
