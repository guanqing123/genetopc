package com.stylefeng.guns.core.config;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stylefeng.guns.core.config.properties.RedisProperties;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	
	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
		jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
		jedisPoolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
		jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(redisProperties.getHost());
		jedisConnectionFactory.setPort(redisProperties.getPort());
		jedisConnectionFactory.setPassword(redisProperties.getPassword());
		jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
		jedisConnectionFactory.setTimeout(redisProperties.getTimeout());
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}
	
	public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
		public final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
		private Class<T> clazz;
		
		public FastJson2JsonRedisSerializer(Class<T> clazz) {
			// TODO Auto-generated constructor stub
			super();
			this.clazz = clazz;
		}
		
		@Override
		public byte[] serialize(T t) throws SerializationException {
			// TODO Auto-generated method stub
			if (t == null) {
				return new byte[0];
			}
			return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
		}

		@Override
		public T deserialize(byte[] bytes) throws SerializationException {
			// TODO Auto-generated method stub
			if (bytes == null || bytes.length <= 0) {
				return null;
			}
			String str = new String(bytes, DEFAULT_CHARSET);
			return JSON.parseObject(str, clazz);
		}
		
	}
	
	@Bean
	public RedisSerializer<?> fastJson2JsonRedisSerializer() {
		ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
		return new FastJson2JsonRedisSerializer<Object>(Object.class);
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory, RedisSerializer<?> fastJson2JsonRedisSerializer){
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		//key序列化方式;(不然会出现乱码),但是如果方法上有Long等非String类型的话,会报类型转换错误
		//所以在没有自己定义key生成策略的时候,以下这个代码建议不要这么写,可以不配置或者自己实现ObjectRedisSerializer或者JdkSerializationRedisSerializer序列化方式;
		RedisSerializer<String> redisSerializer = new StringRedisSerializer(); //Long类型不可以 会出现异常信息
		redisTemplate.setKeySerializer(redisSerializer);
		redisTemplate.setHashKeySerializer(redisSerializer);
		
		redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	/**
	 * 缓存管理器
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// 设置默认过期时间为:30分钟
		cacheManager.setDefaultExpiration(60 * 30);
		
		Map<String, Long> expires = new HashMap<>();
		expires.put("identifyCode", Long.valueOf(60 * 10));
		cacheManager.setExpires(expires);
		return cacheManager;
	}
	
	/**
	 * 自定义key
	 * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样,key也会不一样。
	 */
	@Override
	public KeyGenerator keyGenerator() {
		// TODO Auto-generated method stub
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... params) {
				// TODO Auto-generated method stub
				StringBuilder builder = new StringBuilder();
				builder.append(target.getClass().getName())
						.append(method.getName());
				for (Object param : params) {
					if (param != null) {
						builder.append(param.toString());
					}
				}
				return builder.toString();
			}
		};
	}
}
