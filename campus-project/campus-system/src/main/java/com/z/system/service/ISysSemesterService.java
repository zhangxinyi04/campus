package com.z.system.service;


/**
 * 定时任务调度信息信息 服务层
 *
 * @author ruoyi
 */
public interface ISysSemesterService {
    /**
     * 获取quartz调度器的计划任务
     *
     * @param job 调度信息
     * @return 调度任务集合
     */

    void semesterManage();

    void checkSemesterStatus();


    void checkCycleStatus();
    void autoChangeClasses();



}
