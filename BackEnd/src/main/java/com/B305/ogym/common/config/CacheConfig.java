package com.B305.ogym.common.config;


import com.B305.ogym.common.properties.CacheProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
    Redis 캐시서버를 위한 환경설정
 */


@RequiredArgsConstructor
@EnableCaching
@Configuration
public class CacheConfig {

    private final CacheProperties cacheProperties;

    @Value("${spring.redis.cache.host}")
    private String redisHost;

    @Value("${spring.redis.cache.port}")
    private int redisPort;

    @Bean(name = "redisCacheConnectionFactory")
    public RedisConnectionFactory redisCacheConnectionFactory() {
        return new LettuceConnectionFactory(redisHost,
            redisPort);
    }


    private ObjectMapper objectMapper() {
        // jackson 2.10이상 3.0버전까지 적용 가능
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(Object.class)
            .build();

        return JsonMapper.builder()
            .polymorphicTypeValidator(ptv)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .addModule(new JavaTimeModule())
            .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
            .build();
    }

    // redisCacheManager에 여러 옵션을 부여할 수 있는 오브젝트
    // 캐시의 key/value를 직렬화 - 역직렬화하는 Pair
    private RedisCacheConfiguration redisCacheDefaultConfiguration() {
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(
                    new GenericJackson2JsonRedisSerializer(objectMapper())));
    }

    private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        for (Entry<String, Long> cacheNameAndTimeout : cacheProperties.getTtl().entrySet()) {
            cacheConfigurations
                .put(cacheNameAndTimeout.getKey(), redisCacheDefaultConfiguration().entryTtl(
                    Duration.ofSeconds(cacheNameAndTimeout.getValue())));
        }
        return cacheConfigurations;
    }

    @Bean
    public CacheManager redisCacheManager(
        @Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheDefaultConfiguration())
            .withInitialCacheConfigurations(redisCacheConfigurationMap()).build();
    }


}
