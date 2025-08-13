package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生体育成绩管理对象 sys_student_pe_score
 * 
 * @author z
 * @date 2025-04-10
 */
public class SysStudentPeScore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long studentScoreId;

    /** 学生id */
    private Long studentId;
    private Long deptId;

    /** 学期表 */
    @Excel(name = "学期")
    private String semesterId;
    @Excel(name = "年级")
    private String deptName;
    @Excel(name = "学生姓名")
    private String studentName;
    /** 总分 */
    @Excel(name = "总分")
    private String score;

    /** 等级 0优秀 1良好 2及格 3不及格 */
    @Excel(name = "等级")
    private String level;

    /** 测试成绩 */
    @Excel(name = "测试成绩")
    private String testScore;

    /** 测试等级 0优秀 1良好 2及格 3不及格 */
    @Excel(name = "测试等级")
    private String testLevel;

    /** 附加分数 */
    @Excel(name = "附加分数")
    private String appendScore;

    /** 身高 */
    @Excel(name = "身高")
    private String height;

    /** 体重 */
    @Excel(name = "体重")
    private String weight;

    /** 肺活量 */
    @Excel(name = "肺活量")
    private String vitalCapacity;

    /** 身高体重指数 */
    @Excel(name = "身高体重指数")
    private String bmi;

    /** 50米跑 */
    @Excel(name = "50米跑")
    private String run;

    /** 体前屈 */
    @Excel(name = "体前屈")
    private String sitReach;

    /** 跳绳 */
    @Excel(name = "跳绳")
    private String jump;

    /** 仰卧起坐 */
    @Excel(name = "仰卧起坐")
    private String sitUp;

    public void setStudentScoreId(Long studentScoreId) 
    {
        this.studentScoreId = studentScoreId;
    }

    public Long getStudentScoreId() 
    {
        return studentScoreId;
    }
    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setSemesterId(String semesterId) 
    {
        this.semesterId = semesterId;
    }

    public String getSemesterId() 
    {
        return semesterId;
    }
    public void setScore(String score) 
    {
        this.score = score;
    }

    public String getScore() 
    {
        return score;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setTestScore(String testScore) 
    {
        this.testScore = testScore;
    }

    public String getTestScore() 
    {
        return testScore;
    }
    public void setTestLevel(String testLevel) 
    {
        this.testLevel = testLevel;
    }

    public String getTestLevel() 
    {
        return testLevel;
    }
    public void setAppendScore(String appendScore) 
    {
        this.appendScore = appendScore;
    }

    public String getAppendScore() 
    {
        return appendScore;
    }
    public void setHeight(String height) 
    {
        this.height = height;
    }

    public String getHeight() 
    {
        return height;
    }
    public void setWeight(String weight) 
    {
        this.weight = weight;
    }

    public String getWeight() 
    {
        return weight;
    }
    public void setVitalCapacity(String vitalCapacity) 
    {
        this.vitalCapacity = vitalCapacity;
    }

    public String getVitalCapacity() 
    {
        return vitalCapacity;
    }
    public void setBmi(String bmi) 
    {
        this.bmi = bmi;
    }

    public String getBmi() 
    {
        return bmi;
    }
    public void setRun(String run) 
    {
        this.run = run;
    }

    public String getRun() 
    {
        return run;
    }
    public void setSitReach(String sitReach) 
    {
        this.sitReach = sitReach;
    }

    public String getSitReach() 
    {
        return sitReach;
    }
    public void setJump(String jump) 
    {
        this.jump = jump;
    }

    public String getJump() 
    {
        return jump;
    }
    public void setSitUp(String sitUp) 
    {
        this.sitUp = sitUp;
    }

    public String getSitUp() 
    {
        return sitUp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("studentScoreId", getStudentScoreId())
            .append("studentId", getStudentId())
            .append("semesterId", getSemesterId())
            .append("score", getScore())
            .append("level", getLevel())
            .append("testScore", getTestScore())
            .append("testLevel", getTestLevel())
            .append("appendScore", getAppendScore())
            .append("height", getHeight())
            .append("weight", getWeight())
            .append("vitalCapacity", getVitalCapacity())
            .append("bmi", getBmi())
            .append("run", getRun())
            .append("sitReach", getSitReach())
            .append("jump", getJump())
            .append("sitUp", getSitUp())
            .toString();
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
