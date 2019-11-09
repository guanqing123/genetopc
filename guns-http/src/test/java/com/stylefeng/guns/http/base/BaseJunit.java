package com.stylefeng.guns.http.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.stylefeng.guns.http.GunsHttpApplicationTests;


/**
 * 基础测试类
 *
 * @author guanqing
 * @Date 2018/7/17 11:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GunsHttpApplicationTests.class)
@WebAppConfiguration
//@Transactional //打开的话测试之后数据可自动回滚
public class BaseJunit {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void initDatabase(){
    }
}
