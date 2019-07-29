package com.example.learn.config;

//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * CacheManager 缓存管理器
     * @param connectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 初始化一个 RedisWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 设置默认超时时间是 1 天
        defaultCacheConfig.entryTtl(Duration.ofDays(1));
        // 初始化 RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
        return cacheManager;
    }

    /**
     * RedisTemplate 相关配置
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(connectionFactory);

        // 使用 Jackson2JsonRedisSerializer 进行序列化和反序列化 (将对象序列化为JSON)
        // Jackson2JsonRedisSerializer jacksonSerialize = new Jackson2JsonRedisSerializer(Object.class);
        // ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域， field get set 以及修饰范围， any 是都有包括 private 和 public
        // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // jacksonSerialize.setObjectMapper(om);


        // 值采用 json 序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        // template.setHashKeySerializer(new Jackson2JsonRedisSerializer(Object.class));
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对 hash 类型的操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对 redis 字符串类型的操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 无序集合类型的操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}
