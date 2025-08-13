package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生成绩管理对象 sys_student_score
 *
 * @author z
 * @date 2025-04-10
 */
public class SysStudentScore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long studentScoreId;

    /**
     * 学生id
     */
    private Long studentId;
    private Long deptId;

    @Excel(name = "学期")
    private String semesterId;
    @Excel(name = "年级")
    private String deptName;
    @Excel(name = "学生姓名")
    private String studentName;

    /**
     * 语文
     */
    @Excel(name = "语文")
    private String chinese;

    /**
     * 数学
     */
    @Excel(name = "数学")
    private String math;

    /**
     * 英语
     */
    @Excel(name = "英语")
    private String english;

    /**
     * 美术
     */
    @Excel(name = "美术")
    private String art;

    /**
     * 音乐
     */
    @Excel(name = "音乐")
    private String music;

    /**
     * 体育与健康
     */
    @Excel(name = "体育与健康")
    private String sports;

    /**
     * 综合实践
     */
    @Excel(name = "综合实践")
    private String practice;

    /**
     * 信息科技
     */
    @Excel(name = "信息科技")
    private String computer;

    /**
     * 劳动与技术
     */
    @Excel(name = "劳动与技术")
    private String labour;

    /**
     * 道德与法治
     */
    @Excel(name = "道德与法治")
    private String morality;

    /**
     * 心理健康
     */
    @Excel(name = "心理健康")
    private String health;

    /**
     * 科学
     */
    @Excel(name = "科学")
    private String science;

    /**
     * 书法
     */
    @Excel(name = "书法")
    private String calligraphy;

    /**
     * 舞蹈
     */
    @Excel(name = "舞蹈")
    private String dance;

    /**
     * 阅读
     */
    @Excel(name = "阅读")
    private String readbook;

    public void setStudentScoreId(Long studentScoreId) {
        this.studentScoreId = studentScoreId;
    }

    public Long getStudentScoreId() {
        return studentScoreId;
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

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getMath() {
        return math;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEnglish() {
        return english;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getArt() {
        return art;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMusic() {
        return music;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getSports() {
        return sports;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getPractice() {
        return practice;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getComputer() {
        return computer;
    }

    public void setLabour(String labour) {
        this.labour = labour;
    }

    public String getLabour() {
        return labour;
    }

    public void setMorality(String morality) {
        this.morality = morality;
    }

    public String getMorality() {
        return morality;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHealth() {
        return health;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public String getScience() {
        return science;
    }

    public void setCalligraphy(String calligraphy) {
        this.calligraphy = calligraphy;
    }

    public String getCalligraphy() {
        return calligraphy;
    }

    public void setDance(String dance) {
        this.dance = dance;
    }

    public String getDance() {
        return dance;
    }

    public void setReadbook(String readbook) {
        this.readbook = readbook;
    }

    public String getReadbook() {
        return readbook;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("studentScoreId", getStudentScoreId())
                .append("studentId", getStudentId())
                .append("semesterId", getSemesterId())
                .append("chinese", getChinese())
                .append("math", getMath())
                .append("english", getEnglish())
                .append("art", getArt())
                .append("music", getMusic())
                .append("sports", getSports())
                .append("practice", getPractice())
                .append("computer", getComputer())
                .append("labour", getLabour())
                .append("morality", getMorality())
                .append("health", getHealth())
                .append("science", getScience())
                .append("calligraphy", getCalligraphy())
                .append("dance", getDance())
                .append("readbook", getReadbook())
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
