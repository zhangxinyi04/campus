package com.z.system.domain;

import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 活动奖章对象 sys_event_medal
 *
 * @author z
 * @date 2025-03-13
 */
public class SysEventMedal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 活动奖章ID
     */
    private Long eventMedalId;

    /**
     * 活动奖章名称
     */
    @Excel(name = "活动奖章名称")
    private String eventMedalName;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String url;

    /**
     * $column.columnComment
     */
    private String delFlag;
    private Integer num;

    public void setEventMedalId(Long eventMedalId) {
        this.eventMedalId = eventMedalId;
    }

    public Long getEventMedalId() {
        return eventMedalId;
    }

    public void setEventMedalName(String eventMedalName) {
        this.eventMedalName = eventMedalName;
    }

    public String getEventMedalName() {
        return eventMedalName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("eventMedalId", getEventMedalId())
                .append("eventMedalName", getEventMedalName())
                .append("url", getUrl())
                .append("createTime", getCreateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
