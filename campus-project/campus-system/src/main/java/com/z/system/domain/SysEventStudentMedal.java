package com.z.system.domain;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author ruoyi
 */
public class SysEventStudentMedal {
    private Long studentMedalId;
    private Long studentId;
    private Long medalId;
    private String semesterId;

    public Long getStudentMedalId() {
        return studentMedalId;
    }

    public void setStudentMedalId(Long studentMedalId) {
        this.studentMedalId = studentMedalId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getMedalId() {
        return medalId;
    }

    public void setMedalId(Long medalId) {
        this.medalId = medalId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }
}
