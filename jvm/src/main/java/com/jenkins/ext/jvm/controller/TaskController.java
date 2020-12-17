package com.jenkins.ext.jvm.controller;

import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:45
 **/
@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("build_data")
    public void buildData(@RequestBody TaskEntity taskEntity) {
        taskService.buildData(taskEntity);
    }

    @GetMapping("health")
    public void health() {
    }

}
