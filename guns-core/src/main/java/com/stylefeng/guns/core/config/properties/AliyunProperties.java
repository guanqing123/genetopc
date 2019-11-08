package com.stylefeng.guns.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
  * 阿里云相关配置
  * create by guanqing
  * 2018年7月20日 上午10:17:47
  */
@Data
@Component
@ConfigurationProperties(prefix = AliyunProperties.ALIYUNCONF_PREFIX)
public class AliyunProperties {
	public static final String ALIYUNCONF_PREFIX = "aliyun";

	private Oss oss;
	
	@Data
	public static class Oss {
		// 资源空间名
		private String bucket;
		// 资源自定义域名
		private String domain;
		// accessKeyId
		private String accessKeyId;
		// accessKeySecret
		private String accessKeySecret;
	}
}