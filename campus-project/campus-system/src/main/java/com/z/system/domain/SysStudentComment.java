package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 学生家长评语管理对象 sys_student_comment
 *
 * @author z
 * @date 2025-04-14
 */
public class SysStudentComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long studentCommentId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学期表
     */
    private String semesterId;

    /**
     * 内容
     */
    private String content;
    private String teacherContent;
    private Integer[] studentIds;
    private String studentIdByString;

    public void setStudentCommentId(Long studentCommentId) {
        this.studentCommentId = studentCommentId;
    }

    public Long getStudentCommentId() {
        return studentCommentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("studentCommentId", getStudentCommentId())
                .append("studentId", getStudentId())
                .append("semesterId", getSemesterId())
                .append("content", getContent())
                .toString();
    }


    public String getTeacherContent() {
        return teacherContent;
    }

    public void setTeacherContent(String teacherContent) {
        this.teacherContent = teacherContent;
    }

    public Integer[] getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Integer[] studentIds) {
        this.studentIds = studentIds;
    }

    public String getStudentIdByString() {
        return studentIdByString;
    }

    public void setStudentIdByString(String studentIdByString) {
        this.studentIdByString = studentIdByString;
    }
}
