package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import com.z.common.core.domain.entity.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生信息对象 sys_student
 * 
 * @author z
 * @date 2025-03-08
 */
public class SysStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long studentId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 学籍号 */
    @Excel(name = "学籍号")
    private String studentNumber;
    private String group;
    private SysDept dept;
    private SysStudentMarkRecords markRecords ;

    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setStudentNumber(String studentNumber) 
    {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() 
    {
        return studentNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("studentId", getStudentId())
            .append("deptId", getDeptId())
            .append("studentName", getStudentName())
            .append("sex", getSex())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("studentNumber", getStudentNumber())
            .toString();
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public SysStudentMarkRecords getMarkRecords() {
        return markRecords;
    }

    public void setMarkRecords(SysStudentMarkRecords markRecords) {
        this.markRecords = markRecords;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
