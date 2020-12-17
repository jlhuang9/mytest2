package io.jenkins.plugins.ext.entity;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:48
 **/
public class TaskEntity extends BaseEntity {

    public static final Integer START_TYPE = 0;
    public static final Integer BUILDING_TYPE = 1;
    public static final Integer SUCESS_TYPE = 2;

    private String name;
    private Integer number;
    private Integer type;
    private String log;
    private Long offset;
    private Long timestamp;
    private Long duration;

    /**
     * jenkins result type
     * 0-SUCCESS,1-UNSTABLE,2-FAILURE,3-NOT_BUILT,4-ABORTED
     */
    private String resultName;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
