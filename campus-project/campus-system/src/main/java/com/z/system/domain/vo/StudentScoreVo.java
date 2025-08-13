package com.z.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class StudentScoreVo {
    private String  subject;
    private String excellentRate;
    private String goodRate;
    private String passRate;
    private String failRate;

    public String getExcellentRate() {
        return excellentRate;
    }

    public void setExcellentRate(String excellentRate) {
        this.excellentRate = excellentRate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(String goodRate) {
        this.goodRate = goodRate;
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    public String getFailRate() {
        return failRate;
    }

    public void setFailRate(String failRate) {
        this.failRate = failRate;
    }
}
