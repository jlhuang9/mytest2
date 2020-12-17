package com.jenkins.ext.jvm.entity.query;

import com.jenkins.ext.jvm.entity.BaseEntity;

/**
 * @author huangchengqian
 * @date 2020-12-15 11:28
 **/
public class QueryTopWorksapce extends BaseEntity {

    private String name;
    private int size = 20;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
