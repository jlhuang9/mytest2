package com.jenkins.ext.jvm.entity.query;

import com.jenkins.ext.jvm.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author huangchengqian
 * @date 2020-12-16 14:11
 **/
public class QueryConsole extends BaseEntity {

    @NotBlank(message = "name cannot be null")
    private String name;
    @NotNull(message = "number cannot be null")
    private Integer number;
    private Long offset = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
