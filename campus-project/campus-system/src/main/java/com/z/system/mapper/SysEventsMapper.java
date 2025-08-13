package com.z.system.mapper;

import java.util.List;

import com.z.system.domain.*;
import org.apache.ibatis.annotations.Param;

/**
 * 活动Mapper接口
 * 
 * @author z
 * @date 2025-03-13
 */
public interface SysEventsMapper 
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
    public int insertSysEvents(SysEvents sysEvents);

    /**
     * 修改活动
     * 
     * @param sysEvents 活动
     * @return 结果
     */
    public int updateSysEvents(SysEvents sysEvents);

    /**
     * 删除活动
     * 
     * @param eventId 活动主键
     * @return 结果
     */
    public int deleteSysEventsByEventId(Long eventId);

    /**
     * 批量删除活动
     * 
     * @param eventIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEventsByEventIds(Long[] eventIds);

    void insertSysEventCycle(List<SysEventCycle> sysEventCycles);


    int insertSysEventGrade(List<SysEventGradeClass> sysEventGradeClasses);

    int addTemplate(List<SysEventsTemplate> sysEventsTemplates);

    List<SysEventsTemplate> listTemplate();

    void deleteCycleByEventIds(Long[] eventIds);

    void deleteMedalByEventIds(Long[] eventIds);

    void deleteGradeByEventIds(Long[] eventIds);

    List<SysEventCycle> cycleRecords(SysEventCycle eventCycle);

    void updateSysEventsCycle(List<SysEventCycle> result);

    List<SysEventCycleStudent> cycleRecordsByStudent(SysEventCycleStudent eventCycleStudent);

    void updateSysEventsStatus(List<SysEvents> eventsResult);

    List<SysEventCycle> selectEventsCycle(SysEvents sysEvent);

    List<Long> selectStudentEventsCycle(@Param("studentId") Long studentId,@Param("eventId") Long eventId);

    List<SysEvents> selectCountByEventTagType(SysEvents events);

    List<SysEvents> selectEventIndex(String semesterId);
}
