package com.z.system.mapper;

import com.z.common.core.domain.entity.SysSemesterManage;

import java.util.List;

/**
 * 调度任务信息 数据层
 *
 * @author ruoyi
 */
public interface SysSemesterManageMapper {
    /**
     * 查询调度任务日志集合
     *
     * @param job 调度信息
     * @return 操作日志集合
     */

    SysSemesterManage selectSemester();

    void insertSemester(List<SysSemesterManage> sysSemesterManages);

    SysSemesterManage selectSemesterByConditions(SysSemesterManage manage);

    void updateSemester(SysSemesterManage manage);

    List<SysSemesterManage> selectAllSemester();


}
