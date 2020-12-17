package com.jenkins.ext.jvm.controller.advice;

import com.jenkins.ext.jvm.entity.ResultDto;
import com.jenkins.ext.jvm.error.BaseException;
import com.jenkins.ext.jvm.error.ResponseCode;
import com.jenkins.ext.jvm.utils.RestUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangchengqian
 * @date 2020-12-16 13:04
 **/
@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDto handle(Exception ex) {
        return RestUtils.error(ResponseCode.ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultDto handle(RuntimeException ex) {
        return RestUtils.error(ResponseCode.ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultDto handle(BaseException ex) {
        return RestUtils.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultDto handle(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return RestUtils.error(ResponseCode.PARAMETER_VERIFICATION_ERROR, fieldError.getDefaultMessage());
    }
}
