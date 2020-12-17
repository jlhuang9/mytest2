package com.jenkins.ext.jvm.controller;

import com.jenkins.ext.jvm.BaseControllerMock;
import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.ResultDto;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.entity.WorksapceEntity;
import com.jenkins.ext.jvm.entity.query.QueryConsole;
import com.jenkins.ext.jvm.entity.query.QueryTask;
import com.jenkins.ext.jvm.entity.query.QueryTopWorksapce;
import com.jenkins.ext.jvm.error.ResponseCode;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ReportControllerTest extends BaseControllerMock {

    private static final String REPORT_PAGE_QUERY = "/report/pageQuery";
    private static final String REPORT_PAGE_TASK_QUERY = "/report/pageTaskQuery";
    private static final String REPORT_GET_CONSOLE_LOG = "/report/getConsoleLog";
    private static final String REPORT_GET_TOP_WORKSPACES = "/report/getTopWorkspaces";
    private static final String REPORT_GET_TASKS = "/report/getTasks";

    private static final String NAME = "test";

    @Test
    public void testPageQuery() throws Exception {
        PageResult pageResult = new PageResult();
        ResultDto<?> response = this.getResponse(REPORT_PAGE_QUERY, WorksapceEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.SUCCESS);
    }

    @Test
    public void testPageQueryPageSizeMoreThan100() throws Exception {
        PageResult pageResult = new PageResult();
        pageResult.setPageSize(1000L);
        ResultDto<?> response = this.getResponse(REPORT_PAGE_QUERY, WorksapceEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "pageSize needs to be between 10 and 100");
    }

    @Test
    public void testPageQueryPageSizeLessThan100() throws Exception {
        PageResult pageResult = new PageResult();
        pageResult.setPageSize(9L);
        ResultDto<?> response = this.getResponse(REPORT_PAGE_QUERY, WorksapceEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "pageSize needs to be between 10 and 100");
    }

    @Test
    public void testPageTaskQuery() throws Exception {
        PageResult pageResult = new PageResult();
        ResultDto<?> response = this.getResponse(REPORT_PAGE_TASK_QUERY, TaskEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.SUCCESS);
    }

    @Test
    public void testPageTaskQueryPageSizeMoreThan100() throws Exception {
        PageResult pageResult = new PageResult();
        pageResult.setPageSize(101L);
        ResultDto<?> response = this.getResponse(REPORT_PAGE_TASK_QUERY, TaskEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "pageSize needs to be between 10 and 100");
    }

    @Test
    public void testPageTaskQueryPageSizeLessThan100() throws Exception {
        PageResult pageResult = new PageResult();
        pageResult.setPageSize(9L);
        ResultDto<?> response = this.getResponse(REPORT_PAGE_TASK_QUERY, TaskEntity.class, pageResult);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "pageSize needs to be between 10 and 100");
    }

    @Test
    public void testGetConsole() throws Exception {
        QueryConsole queryConsole = new QueryConsole();
        queryConsole.setName(NAME);
        queryConsole.setNumber(53);
        ResultDto<?> response = this.getResponse(REPORT_GET_CONSOLE_LOG, TaskEntity.class, queryConsole);
        assertEquals(response.getCode(), ResponseCode.SUCCESS);
    }

    @Test
    public void testGetConsoleWithOutName() throws Exception {
        QueryConsole queryConsole = new QueryConsole();
        queryConsole.setNumber(53);
        ResultDto<?> response = this.getResponse(REPORT_GET_CONSOLE_LOG, TaskEntity.class, queryConsole);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "name cannot be null");
    }

    @Test
    public void testGetConsoleWithOutNumber() throws Exception {
        QueryConsole queryConsole = new QueryConsole();
        queryConsole.setName(NAME);
        ResultDto<?> response = this.getResponse(REPORT_GET_CONSOLE_LOG, TaskEntity.class, queryConsole);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "number cannot be null");
    }



    @Test
    public void testGetTopWorkspaces() throws Exception {
        QueryTopWorksapce queryConsole = new QueryTopWorksapce();
        queryConsole.setName("a");
        ResultDto<?> response = this.getResponse(REPORT_GET_TOP_WORKSPACES, TaskEntity.class, queryConsole);
        assertEquals(response.getCode(), ResponseCode.SUCCESS);
    }

    @Test
    public void testGetTasks() throws Exception {
        QueryTask queryTask = new QueryTask();
        queryTask.setName(NAME);
        ResultDto<?> response = this.getResponse(REPORT_GET_TASKS, TaskEntity.class, queryTask);
        assertEquals(response.getCode(), ResponseCode.SUCCESS);
    }


    @Test
    public void testGetTasksWithOutName() throws Exception {
        QueryTask queryTask = new QueryTask();
        ResultDto<?> response = this.getResponse(REPORT_GET_TASKS, TaskEntity.class, queryTask);
        assertEquals(response.getCode(), ResponseCode.PARAMETER_VERIFICATION_ERROR);
        assertEquals(response.getMessage(), "name cannot be null");
    }

    @Test
    public void testGetTasksWithType() throws Exception {
        QueryTask queryTask = new QueryTask();
        queryTask.setName(NAME);
        queryTask.setType(TaskEntity.SUCESS_TYPE);
        ResultDto<?> response = this.getResponse(REPORT_GET_TASKS, TaskEntity.class, queryTask);

        assertEquals(response.getCode(), ResponseCode.SUCCESS);
        Object data = response.getData();
        if (Objects.nonNull(data)) {
            List<TaskEntity> list = (List<TaskEntity>) data;
            for (TaskEntity taskEntity : list) {
                assertEquals(taskEntity.getType(), TaskEntity.SUCESS_TYPE);
            }
        }
    }
}