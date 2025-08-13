package com.z.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 学生分数记录对象 sys_student_mark_records
 *
 * @author z
 * @date 2025-03-20
 */
public class SysStudentMarkRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 教师ID
     */
    @Excel(name = "教师ID")
    private Long teacherId;

    /**
     * 学生ID
     */
    @Excel(name = "学生ID")
    private Long studentId;
    private Long counts;
    private String courseName;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private Long mark;

    private Long courseId;
    private Long deptId;
    private Long bonusTypeId;
    private Integer type;

    /**
     * 学期id
     */
    @Excel(name = "学期id")
    private String semesterId;

    /**
     * 评价内容
     */
    @Excel(name = "评价内容")
    private String content;

    /**
     * 评价图片
     */
    @Excel(name = "评价图片")
    private String image;
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public Long getMark() {
        return mark;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("teacherId", getTeacherId())
                .append("studentId", getStudentId())
                .append("mark", getMark())
                .append("createTime", getCreateTime())
                .append("semesterId", getSemesterId())
                .append("content", getContent())
                .append("image", getImage())
                .toString();
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getBonusTypeId() {
        return bonusTypeId;
    }

    public void setBonusTypeId(Long bonusTypeId) {
        this.bonusTypeId = bonusTypeId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
