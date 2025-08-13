package com.z.system.service;

import java.util.List;

import com.z.system.domain.SysEventCycle;
import com.z.system.domain.SysEventCycleStudent;
import com.z.system.domain.SysEvents;
import com.z.system.domain.SysEventsTemplate;

/**
 * 活动Service接口
 * 
 * @author z
 * @date 2025-03-13
 */
public interface ISysEventsService 
{
    /**
     * 查询活动
     * 
     * @param eventId 活动主键
     * @return 活动
     */
    public SysEvents selectSysEventsByEventId(Long eventId);

    /**
     * 查询活动列表
     * 
     * @param sysEvents 活动
     * @return 活动集合
     */
    public List<SysEvents> selectSysEventsList(SysEvents sysEvents);

    /**
     * 新增活动
     * 
     * @param sysEvents 活动
     * @return 结果
     */
    public void insertSysEvents(SysEvents sysEvents);

    /**
     * 修改活动
     * 
     * @param sysEvents 活动
     * @return 结果
     */
    public int updateSysEvents(SysEvents sysEvents);

    /**
     * 批量删除活动
     * 
     * @param eventIds 需要删除的活动主键集合
     * @return 结果
     */
    public int deleteSysEventsByEventIds(Long[] eventIds);

    /**
     * 删除活动信息
     * 
     * @param eventId 活动主键
     * @return 结果
     */
    public int deleteSysEventsByEventId(Long eventId);

    int addTemplate();

    List<SysEventsTemplate> listTemplate();

    List<SysEventCycle> cycleRecords(SysEventCycle eventCycle);

    List<SysEventCycleStudent> cycleRecordsByStudent(SysEventCycleStudent eventCycleStudent);

    List<SysEvents> selectCountByEventTagType(SysEvents events);

    List<SysEvents> selectEventIndex(String semesterId);
}
