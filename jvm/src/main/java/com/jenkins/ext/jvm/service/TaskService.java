package com.jenkins.ext.jvm.service;

import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.query.QueryConsole;
import com.jenkins.ext.jvm.entity.query.QueryTask;
import com.jenkins.ext.jvm.entity.query.QueryTopWorksapce;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.entity.WorksapceEntity;

import java.util.List;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:53
 **/
public interface TaskService {
    /**
     * jenkins build data
     * @param taskEntity
     */
    void buildData(TaskEntity taskEntity);

    PageResult<WorksapceEntity> pageWorkspace(PageResult page);

    /**
     * pageTask
     * @param page
     * @param taskEntity
     * @return
     */
    PageResult<TaskEntity> pageTask(PageResult page, TaskEntity taskEntity);

    /**
     * console output
     * @param taskEntity
     * @return
     */
    List<TaskEntity> getConsole(QueryConsole taskEntity);

    /**
     * getTasks
     * @param queryTask
     * @return
     */
    List<TaskEntity> getTasks(QueryTask queryTask);

    /**
     * getTopWorkspaces
     * @param queryTopWorksapce query
     * @return
     */
    List<WorksapceEntity> getTopWorkspaces(QueryTopWorksapce queryTopWorksapce);


}
