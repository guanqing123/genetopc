package com.stylefeng.guns.system;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.modular.custom.dao.BaseCityHospitalMapper;
import com.stylefeng.guns.modular.custom.model.CityHospital;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import com.stylefeng.guns.base.BaseJunit;

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
    BaseCityHospitalMapper baseCityHospitalMapper;
    
    @Test
    public void userTest() throws Exception {

    }
    
    @Test
    public void cityTest() {
    	List<CityHospital> list = baseCityHospitalMapper.cityTree();
    	for (CityHospital cityHospital : list) {
			System.out.println(cityHospital);
		}
    }

}