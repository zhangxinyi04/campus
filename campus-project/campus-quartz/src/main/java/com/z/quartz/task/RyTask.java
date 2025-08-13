package com.z.quartz.task;

import com.z.system.service.ISysSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.z.common.utils.StringUtils;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    @Autowired
    private ISysSemesterService sysSemesterService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 学期管理 每年度自动生成学期
     */
    public void semesterManage() {
        sysSemesterService.semesterManage();
        System.out.println("自动生成学期完成");
    }

    /**
     * 检查当前学期标识是否正确
     */
    public void checkSemesterStatus() {
        sysSemesterService.checkSemesterStatus();
        System.out.println("检查当前学期标识完成");
    }

    /**
     * 检查活动和打卡周期状态
     */
    public void checkCycleStatus() {
        sysSemesterService.checkCycleStatus();
        System.out.println("检查活动和打卡周期状态");
    }

    /**
     * 学生自动升班
     */
    public void autoChangeClasses() {
        sysSemesterService.autoChangeClasses();
        System.out.println("学生自动升班");
    }
}
