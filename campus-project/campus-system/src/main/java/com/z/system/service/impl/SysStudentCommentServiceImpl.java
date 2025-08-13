package com.z.system.service.impl;

import java.util.Arrays;
import java.util.List;

import com.z.common.core.domain.entity.SysSemesterManage;
import com.z.system.mapper.SysSemesterManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.z.system.mapper.SysStudentCommentMapper;
import com.z.system.domain.SysStudentComment;
import com.z.system.service.ISysStudentCommentService;

/**
 * 学生家长评语管理Service业务层处理
 *
 * @author z
 * @date 2025-04-14
 */
@Service
public class SysStudentCommentServiceImpl implements ISysStudentCommentService {
    @Autowired
    private SysStudentCommentMapper sysStudentCommentMapper;
    @Autowired
    private SysSemesterManageMapper semesterManageMapper;

    /**
     * 查询学生家长评语管理
     *
     * @param studentCommentId 学生家长评语管理主键
     * @return 学生家长评语管理
     */
    @Override

    public SysStudentComment selectSysStudentCommentByStudentCommentId(Long studentCommentId) {
        return sysStudentCommentMapper.selectSysStudentCommentByStudentCommentId(studentCommentId);
    }

    /**
     * 查询学生家长评语管理列表
     *
     * @param sysStudentComment 学生家长评语管理
     * @return 学生家长评语管理
     */
    @Override
    public List<SysStudentComment> selectSysStudentCommentList(SysStudentComment sysStudentComment) {
        String studentIdByString = sysStudentComment.getStudentIdByString();
        if (studentIdByString != null) {
            Integer[] arr = Arrays.stream(studentIdByString.split(","))
                    .map(Integer::valueOf)
                    .toArray(Integer[]::new);
            sysStudentComment.setStudentIds(arr);
        }


        return sysStudentCommentMapper.selectSysStudentCommentList(sysStudentComment);
    }

    /**
     * 新增学生家长评语管理
     *
     * @param sysStudentComment 学生家长评语管理
     * @return 结果
     */
    @Override
    public int insertSysStudentComment(SysStudentComment sysStudentComment) {
        SysSemesterManage sysSemester = new SysSemesterManage();
        sysSemester.setStatus("1");
        SysSemesterManage sysSemesterManage = semesterManageMapper.selectSemesterByConditions(sysSemester);
        sysStudentComment.setSemesterId(sysSemesterManage.getSemesterId());
        return sysStudentCommentMapper.insertSysStudentComment(sysStudentComment);
    }

    /**
     * 修改学生家长评语管理
     *
     * @param sysStudentComment 学生家长评语管理
     * @return 结果
     */
    @Override
    public int updateSysStudentComment(SysStudentComment sysStudentComment) {
        return sysStudentCommentMapper.updateSysStudentComment(sysStudentComment);
    }

    /**
     * 批量删除学生家长评语管理
     *
     * @param studentCommentIds 需要删除的学生家长评语管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentCommentByStudentCommentIds(Long[] studentCommentIds) {
        return sysStudentCommentMapper.deleteSysStudentCommentByStudentCommentIds(studentCommentIds);
    }

    /**
     * 删除学生家长评语管理信息
     *
     * @param studentCommentId 学生家长评语管理主键
     * @return 结果
     */
    @Override
    public int deleteSysStudentCommentByStudentCommentId(Long studentCommentId) {
        return sysStudentCommentMapper.deleteSysStudentCommentByStudentCommentId(studentCommentId);
    }
}
