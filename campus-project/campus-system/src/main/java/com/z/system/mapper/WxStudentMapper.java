package com.z.system.mapper;

import com.z.common.core.domain.entity.SysUser;
import com.z.system.domain.SysEventCycleStudent;
import com.z.system.domain.SysStudent;
import com.z.system.domain.SysStudentMarkRecords;
import com.z.system.domain.vo.ClassCircleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author ruoyi
 */
public interface WxStudentMapper {

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);



    SysStudent selectStudentByUserId(Long userId);


    void clockIn(SysEventCycleStudent eventCycleStudent);

    Long selectMarkByStudentId(@Param("semesterId") String semesterId,@Param("studentId") Long studentId);

    List<ClassCircleVo> classCircle(SysStudentMarkRecords studentMarkRecords);
}
