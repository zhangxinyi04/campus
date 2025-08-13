package com.z.system.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import com.z.common.core.domain.entity.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 活动对象 sys_events
 *
 * @author z
 * @date 2025-03-13
 */
public class SysEvents extends BaseEntity {
    private static final long serialVersionUID = 1L;
    //打卡
    public static final String CLOCK_IN = "0";
    //记录
    public static final String RECORDS = "1";

    public static final String DAY = "0";
    public static final String WEEK = "1";
    public static final String MONTH = "2";

    //校级
    public static final String UNIVERSITY = "0";
    //班级
    public static final String CLASS = "1";

    //活动状态 未开始
    public static final String NO_START = "0";

    //品德发展
    public static final String MORAL_DEVELOPMENT = "0";
    //学业发展
    public static final String ACADEMIC_DEVELOPMENT = "1";
    //身心发展
    public static final String PHYSICAL_AND_MENTAL_DEVELOPMENT = "2";
    //艺术素养
    public static final String ARTISTIC_LITERACY = "3";
    //劳动与社会实践
    public static final String LABOR_AND_SOCIAL_PRACTICE = "4";



    /**
     * 活动id
     */
    private Long eventId;
    /**
     * 奖章id
     */
    private Long medalId;

    /**
     * 学期id
     */
    @Excel(name = "学期id")
    private String semesterId;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private Long mark;

    /**
     * 活动标题
     */
    @Excel(name = "活动标题")
    private String eventName;

    /**
     * 活动简介
     */
    @Excel(name = "活动简介")
    private String eventIntro;

    /**
     * 注意事项
     */
    @Excel(name = "注意事项")
    private String precautions;

    /**
     * 活动图片
     */
    @Excel(name = "活动图片")
    private String eventPic;

    /**
     * 活动附件
     */
    @Excel(name = "活动附件")
    private String attachments;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 级别类型 0 校级 1 班级
     */
    @Excel(name = "级别类型 0 校级 1 班级")
    private String rank;

    /**
     * 活动类型
     */
    @Excel(name = "活动类型")
    private Long eventType;

    /**
     * 活动标签 0学科活动 1跨学科活动
     */
    @Excel(name = "活动标签 0学科活动 1跨学科活动")
    private String eventTag;

    /**
     * 类别 0打卡类 1记录类
     */
    @Excel(name = "类别 0打卡类 1记录类")
    private String eventCategory;

    /**
     * 打卡类周期 0天 2周 3月
     */
    @Excel(name = "打卡类周期 0天 2周 3月")
    private String eventCycle;

    /**
     * 是否上传记录 0否 1是
     */
    @Excel(name = "是否上传记录 0否 1是")
    private String records;

    /**
     * 是否可见 0否 1是
     */
    @Excel(name = "是否可见 0否 1是")
    private String visible;

    /**
     * 活动状态 0未开始 1进行中 2已结束
     */
    @Excel(name = "活动状态 0未开始 1进行中 2已结束")
    private String status;
    private String eventTagType;
    /**
     * 年级id
     */
    private Long[] gradeIds;
    /**
     * 班级id
     */
    private Long[] classIds;
    private Long[] statusArray;
    private SysEventMedal eventMedal;
    private List<SysEventGradeClass> eventGradeClass;
    private List<SysDept> depts;

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public Long getMark() {
        return mark;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventIntro(String eventIntro) {
        this.eventIntro = eventIntro;
    }

    public String getEventIntro() {
        return eventIntro;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setEventPic(String eventPic) {
        this.eventPic = eventPic;
    }

    public String getEventPic() {
        return eventPic;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public Long getEventType() {
        return eventType;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventCategory() {
        return eventCategory;
    }


    public void setRecords(String records) {
        this.records = records;
    }

    public String getRecords() {
        return records;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getVisible() {
        return visible;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("eventId", getEventId())
                .append("semesterId", getSemesterId())
                .append("mark", getMark())
                .append("eventName", getEventName())
                .append("eventIntro", getEventIntro())
                .append("precautions", getPrecautions())
                .append("eventPic", getEventPic())
                .append("attachments", getAttachments())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("rank", getRank())
                .append("eventType", getEventType())
                .append("eventTag", getEventTag())
                .append("eventCategory", getEventCategory())
                .append("records", getRecords())
                .append("visible", getVisible())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .toString();
    }

    public Long[] getGradeIds() {
        return gradeIds;
    }

    public void setGradeIds(Long[] gradeIds) {
        this.gradeIds = gradeIds;
    }

    public Long[] getClassIds() {
        return classIds;
    }

    public void setClassIds(Long[] classIds) {
        this.classIds = classIds;
    }

    public SysEventMedal getEventMedal() {
        return eventMedal;
    }

    public void setEventMedal(SysEventMedal eventMedal) {
        this.eventMedal = eventMedal;
    }

    public Long getMedalId() {
        return medalId;
    }

    public void setMedalId(Long medalId) {
        this.medalId = medalId;
    }

    public List<SysEventGradeClass> getEventGradeClass() {
        return eventGradeClass;
    }

    public void setEventGradeClass(List<SysEventGradeClass> eventGradeClass) {
        this.eventGradeClass = eventGradeClass;
    }

    public List<SysDept> getDepts() {
        return depts;
    }

    public void setDepts(List<SysDept> depts) {
        this.depts = depts;
    }

    public String getEventCycle() {
        return eventCycle;
    }

    public void setEventCycle(String eventCycle) {
        this.eventCycle = eventCycle;
    }

    public Long[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(Long[] statusArray) {
        this.statusArray = statusArray;
    }

    public String getEventTagType() {
        return eventTagType;
    }

    public void setEventTagType(String eventTagType) {
        this.eventTagType = eventTagType;
    }
}
