package com.chinaoly.frm.core.configurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 *
 * @author zcc ON 2018/3/19
 **/
@Configuration
@EnableCaching // 开启注解
public class RedisConfiguration extends CachingConfigurerSupport {
	
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    /*@Value("${spring.redis.password}")
    private String password;*/
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
    	JedisPoolConfig poolConfig = new JedisPoolConfig();
    	poolConfig.setMinIdle(minIdle);
    	poolConfig.setMaxIdle(maxIdle);
    	return poolConfig;
    }

    
	/**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
	@Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };

    }

	/**
     * 缓存管理器.
     * @param redisTemplate
     * @return
     */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		CacheManager cacheManager = new RedisCacheManager(redisTemplate);
		return cacheManager;
		/*
		 * RedisCacheManager rcm = new RedisCacheManager(redisTemplate); //
		 * 多个缓存的名称,目前只定义了一个 rcm.setCacheNames(Arrays.asList("thisredis"));
		 * //设置缓存默认过期时间(秒) rcm.setDefaultExpiration(600); return rcm;
		 */
	}

	// 以下两种redisTemplate自由根据场景选择
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);

		template.setValueSerializer(serializer);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(connectionFactory);
		
		return stringRedisTemplate;
	}
	

}
