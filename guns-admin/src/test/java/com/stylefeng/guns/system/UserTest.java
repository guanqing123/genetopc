package com.stylefeng.guns.system;

import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.config.properties.AliyunProperties;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 用户测试
 *
 * @author fengshuonan
 * @date 2017-04-27 17:05
 */
public class UserTest extends BaseJunit {

    @Resource
    UserMapper userMapper;
    
    @Autowired
    AliyunProperties aliyunProp;

    @Test
    public void userTest() throws Exception {
    	System.out.println(aliyunProp.getOss().getAccessKeyId());
    }

}
