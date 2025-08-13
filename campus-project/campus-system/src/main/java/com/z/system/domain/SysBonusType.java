package com.z.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 加分类型模版对象 sys_bonus_type
 * 
 * @author z
 * @date 2025-04-17
 */
public class SysBonusType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动模版id */
    private Long bonusTypeId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String image;

    /** 类型 0加分 1减分 */
    @Excel(name = "类型 0加分 1减分")
    private String type;

    /** 逻辑删除 */
    private String delFlag;
    private Long userId;

    /** 分数 */
    @Excel(name = "分数")
    private String mark;
    private String counts;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    public void setBonusTypeId(Long bonusTypeId) 
    {
        this.bonusTypeId = bonusTypeId;
    }

    public Long getBonusTypeId() 
    {
        return bonusTypeId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setMark(String mark) 
    {
        this.mark = mark;
    }

    public String getMark() 
    {
        return mark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("bonusTypeId", getBonusTypeId())
            .append("title", getTitle())
            .append("image", getImage())
            .append("type", getType())
            .append("delFlag", getDelFlag())
            .append("mark", getMark())
            .toString();
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
