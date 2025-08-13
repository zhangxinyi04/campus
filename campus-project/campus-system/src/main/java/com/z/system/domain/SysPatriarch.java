//package com.z.system.domain;
//
//import java.util.Date;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.z.common.annotation.Excel;
//import com.z.common.core.domain.BaseEntity;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//
///**
// * 家长信息对象 sys_patriarch
// *
// * @author z
// * @date 2025-03-08
// */
//public class SysPatriarch extends BaseEntity {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 家长ID
//     */
//    private Long patriarchId;
//    private Long studentId;
//
//
//    /**
//     * 家长姓名
//     */
//    @Excel(name = "家长姓名")
//    private String name;
//
//    /**
//     * 用户类型（0父亲 1母亲）
//     */
//    @Excel(name = "用户类型", readConverterExp = "0=父亲,1=母亲")
//    private String userType;
//
//    /**
//     * 手机号码
//     */
//    @Excel(name = "手机号码")
//    private String phone;
//
//
//    /**
//     * 头像地址
//     */
//    @Excel(name = "头像地址")
//    private String avatar;
//
//    /**
//     * 密码
//     */
//    @Excel(name = "密码")
//    private String password;
//
//    /**
//     * 帐号状态（0正常 1停用）
//     */
//    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
//    private String status;
//
//    /**
//     * 删除标志（0代表存在 2代表删除）
//     */
//    private String delFlag;
//
//    /**
//     * 最后登录IP
//     */
//    @Excel(name = "最后登录IP")
//    private String loginIp;
//
//    /**
//     * 最后登录时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date loginDate;
//
//    public void setPatriarchId(Long patriarchId) {
//        this.patriarchId = patriarchId;
//    }
//
//    public Long getPatriarchId() {
//        return patriarchId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setDelFlag(String delFlag) {
//        this.delFlag = delFlag;
//    }
//
//    public String getDelFlag() {
//        return delFlag;
//    }
//
//    public void setLoginIp(String loginIp) {
//        this.loginIp = loginIp;
//    }
//
//    public String getLoginIp() {
//        return loginIp;
//    }
//
//    public void setLoginDate(Date loginDate) {
//        this.loginDate = loginDate;
//    }
//
//    public Date getLoginDate() {
//        return loginDate;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
//                .append("patriarchId", getPatriarchId())
//                .append("name", getName())
//                .append("userType", getUserType())
//                .append("phone", getPhone())
//                .append("avatar", getAvatar())
//                .append("password", getPassword())
//                .append("status", getStatus())
//                .append("delFlag", getDelFlag())
//                .append("loginIp", getLoginIp())
//                .append("loginDate", getLoginDate())
//                .append("createBy", getCreateBy())
//                .append("createTime", getCreateTime())
//                .append("updateBy", getUpdateBy())
//                .append("updateTime", getUpdateTime())
//                .append("remark", getRemark())
//                .toString();
//    }
//
//    public Long getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(Long studentId) {
//        this.studentId = studentId;
//    }
//}
