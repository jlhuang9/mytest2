package com.jenkins.ext.jvm.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author huangchengqian
 * @date 2020-12-11 10:06
 **/
@Document("workspace")
public class WorksapceEntity {
    @MongoId
    private String name;

    private Long updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
