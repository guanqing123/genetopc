package com.stylefeng.guns.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 阿里云相关配置
 * create by guanqing
 * 2018年7月14日 上午09:17:47
 */

@Data
@Configuration
@ConfigurationProperties(prefix = RedisProperties.REDIS_PREFIX)
public class RedisProperties {

	protected static final String REDIS_PREFIX = "spring.redis";
	
	// redis数据库索引(默认为0)
	private int database;
	// 主机地址
	private String host;
	// 端口
	private int port;
	// 密码没有不填写
	private String password;
	// 连接超时时间（毫秒）
	private int timeout;
	// 连接池
	private Pool pool;
	
	@Data
	public static class Pool {
		
		// 最大活跃连接
		private int maxActive;
		// 连接池最大阻塞等待时间(使用负值表示没有限制)
		private int maxWait;
		// 连接池中的最大空闲连接
	    public int maxIdle;
	    // 连接池中的最小空闲连接
	    public int minIdle;
	}	
}
