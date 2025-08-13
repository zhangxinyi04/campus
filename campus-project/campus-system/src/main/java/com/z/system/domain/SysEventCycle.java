package com.z.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 活动记录周期表对象
 *
 * @author z
 * @date 2025-03-08
 */
public class SysEventCycle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动记录周期ID
     */
    private Long eventCycleId;
    /**
     * 活动ID
     */
    private Long eventId;
    //周期
    private Integer eventCycle;
    private String status;
    private Long[] statusArray;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private SysEventCycleStudent cycleStudent;

    public Long getEventCycleId() {
        return eventCycleId;
    }

    public void setEventCycleId(Long eventCycleId) {
        this.eventCycleId = eventCycleId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(Long[] statusArray) {
        this.statusArray = statusArray;
    }

    public Integer getEventCycle() {
        return eventCycle;
    }

    public void setEventCycle(Integer eventCycle) {
        this.eventCycle = eventCycle;
    }

    public SysEventCycleStudent getCycleStudent() {
        return cycleStudent;
    }

    public void setCycleStudent(SysEventCycleStudent cycleStudent) {
        this.cycleStudent = cycleStudent;
    }
}
