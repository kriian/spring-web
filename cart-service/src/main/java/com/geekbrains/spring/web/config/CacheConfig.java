package com.geekbrains.spring.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class CacheConfig {

    @Value("${spring.cache.default.expire-time}")
    private int defaultCacheExpireTime;
    @Value("${spring.cache.user.expire-time}")
    private int userCacheExpireTime;
    @Value("${spring.cache.user.name}")
    private String userCacheName;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        defaultConfiguration = defaultConfiguration.entryTtl(Duration.ofSeconds(defaultCacheExpireTime))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(userCacheName);
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put(userCacheName, defaultConfiguration.entryTtl(Duration.ofSeconds(userCacheExpireTime)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfiguration)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configurationMap)
                .build();
    }
}
