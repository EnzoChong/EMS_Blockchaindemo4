
/**
 * Project Name:ems-srv-demo 
 * File Name:RedisConfig.java <br/><br/>  
 * Description: RedisConfig
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Feb 7, 2018 1:41:53 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * ClassName: RedisConfig <br/>
 * <br/>
 * Description: RedisConfig
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Configuration
public class RedisConfig
{

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(300);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(600);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(10);
        jedisPoolConfig.setTestOnBorrow(false);
        
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("10.11.241.63");
        jedisConnectionFactory.setPassword("k3-NgMzYbuzfZ0xl");
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setPort(43661);
        jedisConnectionFactory.setTimeout(6000);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
