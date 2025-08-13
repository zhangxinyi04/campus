package com.z.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.z.common.annotation.Excel;
import com.z.common.core.domain.BaseEntity;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动对象 sys_events
 *
 * @author z
 * @date 2025-03-13
 */
public class SysEventsTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long eventTemplateId;
    private String title;
    private String image;
    private String tags;
    private String intro;

    public Long getEventTemplateId() {
        return eventTemplateId;
    }

    public void setEventTemplateId(Long eventTemplateId) {
        this.eventTemplateId = eventTemplateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
