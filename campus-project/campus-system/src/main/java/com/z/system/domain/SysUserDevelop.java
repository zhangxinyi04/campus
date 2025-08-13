package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 学生发展管理对象 sys_user_develop
 *
 * @author z
 * @date 2025-03-18
 */
public class SysUserDevelop extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long developId;

    /**
     * 学生ID
     */
    @Excel(name = "学生ID")
    private Long studentId;

    /**
     * 指标 0品德发展 1学业发展 2身心发展 3 艺术素养 4 劳动与社会实践
     */
    @Excel(name = "指标 0品德发展 1学业发展 2身心发展 3 艺术素养 4 劳动与社会实践")
    private String developIndex;

    /**
     * 机构级别 0国际级 1国家级 2省级 3 市级 4 区县级 5 校级 6班级
     */
    @Excel(name = "机构级别 0国际级 1国家级 2省级 3 市级 4 区县级 5 校级 6班级")
    private String institutionRank;

    /**
     * 获奖等级 0一等奖 1 二等奖 2三等奖
     */
    @Excel(name = "获奖等级 0一等奖 1 二等奖 2三等奖")
    private String awardGrade;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 上传地址
     */
    @Excel(name = "上传地址")
    private String url;

    /**
     * 审核状态（0待审核 1通过 2拒绝）
     */
    @Excel(name = "审核状态", readConverterExp = "0=待审核,1=通过,2=拒绝")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 学期id
     */
    private String semesterId;
    private String deptName;
    private String studentName;
    private String qrCode;
    private Long mark;

    public void setDevelopId(Long developId) {
        this.developId = developId;
    }

    public Long getDevelopId() {
        return developId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setInstitutionRank(String institutionRank) {
        this.institutionRank = institutionRank;
    }

    public String getInstitutionRank() {
        return institutionRank;
    }

    public void setAwardGrade(String awardGrade) {
        this.awardGrade = awardGrade;
    }

    public String getAwardGrade() {
        return awardGrade;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("developId", getDevelopId()).append("studentId", getStudentId()).append("developIndex", getDevelopIndex()).append("institutionRank", getInstitutionRank()).append("awardGrade", getAwardGrade()).append("content", getContent()).append("url", getUrl()).append("status", getStatus()).append("delFlag", getDelFlag()).toString();
    }

    public String getDevelopIndex() {
        return developIndex;
    }

    public void setDevelopIndex(String developIndex) {
        this.developIndex = developIndex;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
