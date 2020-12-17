package com.jenkins.ext.jvm.entity;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:48
 */

public class PageResult<T> extends BaseEntity {

    private Long total;
    private List<T> rows;
    @Max(value = 100,message = "pageSize needs to be between 10 and 100")
    @Min(value = 10,message = "pageSize needs to be between 10 and 100")
    private Long pageSize = 10L;
    private Long pageIndex = 1L;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows, Long pageSize, Long pageIndex) {
        this.total = total;
        this.rows = rows;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }
}
