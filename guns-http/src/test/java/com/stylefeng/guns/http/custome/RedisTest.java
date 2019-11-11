package com.stylefeng.guns.http.custome;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.core.util.RedisUtil;
import com.stylefeng.guns.http.base.BaseJunit;

public class RedisTest extends BaseJunit {

	@Autowired
	private RedisUtil redisUtil;
	
	@Test
	public void test() {
		redisUtil.set("name", "guanqing");
		System.out.println(redisUtil.get("name"));
	}
	
}
