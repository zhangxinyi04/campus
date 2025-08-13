package com.z.system.domain;

import com.z.common.core.domain.BaseEntity;
import com.z.common.core.domain.entity.SysCourse;
import com.z.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 用户信息对象 sys_user_document
 *
 * @author z
 * @date 2025-03-04
 */
public class SysUserDocument extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;
    //课程id
    private Long courseId;
    //年级id
    private Long gradeId;
    //被下载用户id
    private Long receiveUserId;
    private Long documentId;
    //资源库id
    private Long resourcesId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String fileName;
    private String nickName;

    /**
     * 文件路径
     */
    private String url;
    private String pdfUrl;
    private String remark;
    /**
     * 审核状态（审核状态 0未审核 1通过 2拒绝 ）
     */
    private String status;
    private SysUser user;
    private SysCourse course;
    /**
     * 用户id
     */
    private Long userId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("delFlag", getDelFlag())
                .append("createTime", getCreateTime())
                .append("url", getUrl())
                .append("fileName", getFileName())
                .append("nickName", getNickName())
                .append("userId", getUserId())
                .toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public SysUser getSysUser() {
        return user;
    }

    public void setSysUser(SysUser sysUser) {
        this.user = sysUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public SysCourse getCourse() {
        return course;
    }

    public void setCourse(SysCourse course) {
        this.course = course;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(Long resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
