package com.z.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 活动记录周期表对象
 *
 * @author z
 * @date 2025-03-08
 */
public class SysEventCycleStudent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动记录周期ID
     */
    private Long eventCycleStudentId;
    /**
     * 活动ID
     */
    private Long eventCycleId;
    //周期
    private String description;
    private String images;
    private Long studentId;
    private Long mark;


    public Long getEventCycleStudentId() {
        return eventCycleStudentId;
    }

    public void setEventCycleStudentId(Long eventCycleStudentId) {
        this.eventCycleStudentId = eventCycleStudentId;
    }

    public Long getEventCycleId() {
        return eventCycleId;
    }

    public void setEventCycleId(Long eventCycleId) {
        this.eventCycleId = eventCycleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }
}
