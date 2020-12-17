package com.jenkins.ext.jvm.error;

/**
 * @author huangchengqian
 * @date 2020-12-16 13:05
 **/
public class BaseException extends RuntimeException {

    private int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
