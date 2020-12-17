package com.jenkins.ext.jvm.service.impl;

import com.jenkins.ext.jvm.entity.PageResult;
import com.jenkins.ext.jvm.entity.TaskEntity;
import com.jenkins.ext.jvm.entity.WorksapceEntity;
import com.jenkins.ext.jvm.entity.query.QueryConsole;
import com.jenkins.ext.jvm.entity.query.QueryTask;
import com.jenkins.ext.jvm.entity.query.QueryTopWorksapce;
import com.jenkins.ext.jvm.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author huangchengqian
 * @date 2020-12-10 09:54
 **/
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void buildData(TaskEntity taskEntity) {
        mongoTemplate.insert(taskEntity);
        WorksapceEntity worksapceEntity = new WorksapceEntity();
        worksapceEntity.setName(taskEntity.getName());
        worksapceEntity.setUpdateTime(System.currentTimeMillis());
        mongoTemplate.save(worksapceEntity);
    }

    @Override
    public PageResult<WorksapceEntity> pageWorkspace(PageResult page) {
        Long pageSize = page.getPageSize();
        Query query = Query.query(new Criteria());
        long count = mongoTemplate.count(query, WorksapceEntity.class);
        query.skip((page.getPageIndex() - 1) * page.getPageSize())
                .limit(pageSize.intValue());
        List<WorksapceEntity> worksapceEntities = mongoTemplate.find(query, WorksapceEntity.class);
        page.setTotal(count);
        page.setRows(worksapceEntities);
        return page;
    }


    @Override
    public PageResult<TaskEntity> pageTask(PageResult page, TaskEntity taskEntity) {
        Long pageSize = page.getPageSize();
        Criteria criteria = Criteria.where("type").is(TaskEntity.START_TYPE);
        if (!StringUtils.isEmpty(taskEntity.getName())) {
            criteria.and("name").is(taskEntity.getName());
        }
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query, TaskEntity.class);
        query.skip((page.getPageIndex() - 1) * pageSize)
                .limit(pageSize.intValue()).with(Sort.by(Sort.Direction.DESC, "number"));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, TaskEntity.class);
        page.setRows(taskEntities);
        page.setTotal(count);
        return page;
    }



    @Override
    public List<TaskEntity> getConsole(QueryConsole taskEntity) {
        String name = taskEntity.getName();
        Integer number = taskEntity.getNumber();
        Long offset = taskEntity.getOffset();
        if (Objects.isNull(offset)) {
            offset = 0L;
        }
        Criteria criteria = Criteria.where("name").is(name)
                .and("number").is(number)
                .and("type").is(TaskEntity.BUILDING_TYPE)
                .and("offset").gt(offset);
        Query query = Query.query(criteria).limit(20).with(Sort.by(Sort.Direction.ASC, "offset"));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, TaskEntity.class);
        return taskEntities;
    }

    @Override
    public List<WorksapceEntity> getTopWorkspaces(QueryTopWorksapce queryTopWorksapce) {
        String name = queryTopWorksapce.getName();
        int size = queryTopWorksapce.getSize();
        Query query = new Query();
        if (!StringUtils.isEmpty(name)) {
            Pattern pattern= Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        }
        query.limit(size);
        List<WorksapceEntity> worksapceEntities = mongoTemplate.find(query, WorksapceEntity.class);
        return worksapceEntities;
    }

    @Override
    public List<TaskEntity> getTasks(QueryTask queryTask) {
        String name = queryTask.getName();
        int type = queryTask.getType();
        Query query = new Query();
        if (!StringUtils.isEmpty(name)) {
            query.addCriteria(Criteria.where("name").is(name));
        }
        query.addCriteria(Criteria.where("type").is(type));
        query.with(Sort.by("timestamp"));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, TaskEntity.class);
        return taskEntities;
    }
}
