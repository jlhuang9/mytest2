package com.jenkins.ext.jvm.controller;

import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.ResultDto;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.entity.query.QueryConsole;
import com.jenkins.ext.jvm.entity.query.QueryTask;
import com.jenkins.ext.jvm.entity.query.QueryTopWorksapce;
import com.jenkins.ext.jvm.service.TaskService;
import com.jenkins.ext.jvm.utils.RestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author huangchengqian
 * @date 2020-12-11 08:50
 **/
@RestController
@RequestMapping("report")
public class ReportController {

    @Resource
    private TaskService taskService;


    @GetMapping("pageQuery")
    public ResultDto<?> pageQuery(@Valid PageResult pageResult) {
        return RestUtils.ok(taskService.pageWorkspace(pageResult));
    }

    @GetMapping("pageTaskQuery")
    public ResultDto<?> pageTaskQuery(@Valid PageResult pageResult, TaskEntity taskEntity) {
        return RestUtils.ok(taskService.pageTask(pageResult, taskEntity));
    }

    @GetMapping("getConsoleLog")
    public ResultDto<?> getConsole(@Valid QueryConsole queryConsole) {
        return RestUtils.ok(taskService.getConsole(queryConsole));
    }

    @GetMapping("getTopWorkspaces")
    public ResultDto<?> getTopWorkspaces(QueryTopWorksapce queryTopWorksapce) {
        return RestUtils.ok(taskService.getTopWorkspaces(queryTopWorksapce));
    }

    @GetMapping("getTasks")
    public ResultDto<?> getTasks(@Valid QueryTask queryTask) {
        return RestUtils.ok(taskService.getTasks(queryTask));
    }
}
