package com.example.demo.config.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisCacheConfig implements CacheConfig {
  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private String port;

  @Bean
  public RedisConnectionFactory redisConnectionFactory(){
    return new LettuceConnectionFactory(host, Integer.parseInt(port));
  }

  @Bean
  public RedisTemplate<?,?> redisTemplate(){
    RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }

}