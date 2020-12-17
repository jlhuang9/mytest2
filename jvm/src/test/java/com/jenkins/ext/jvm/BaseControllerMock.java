package com.jenkins.ext.jvm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jenkins.ext.jvm.controller.ReportController;
import com.jenkins.ext.jvm.controller.advice.ErrorControllerAdvice;
import com.jenkins.ext.jvm.entity.ResultDto;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * @author huangchengqian
 * @date 2020-12-16 12:44
 **/
public class BaseControllerMock extends BaseTest {

    @Resource
    private ReportController reportController;
    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reportController)
                .setControllerAdvice(new ErrorControllerAdvice())
                .build();
    }

    protected ResultDto<?> getResponse(String path, Class resultClazz, Object... params) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(path)
                .contentType(MediaType.APPLICATION_JSON).params(objectToMultiValueMap(params)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content=mvcResult.getResponse().getContentAsString();
        ResultDto response = JSON.parseObject(content, ResultDto.class);
        if (response.getData() != null) {
            if (response.getData().getClass() == JSONObject.class) {
                response.setData(((JSONObject) response.getData()).toJavaObject(resultClazz));
            } else if (response.getData().getClass() == JSONArray.class) {
                response.setData(((JSONArray) response.getData()).toJavaList(resultClazz));
            }
        }
        return response;
    }

    private MultiValueMap<String, String> objectToMultiValueMap(Object... params) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        for (Object param : params) {
            String jsonParm = JSONObject.toJSONString(param);
            JSONObject jsonObject = JSONObject.parseObject(jsonParm);
            Iterator<String> iterator = jsonObject.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                multiValueMap.add(key, jsonObject.getString(key));
            }
        }
        return multiValueMap;
    }

}
