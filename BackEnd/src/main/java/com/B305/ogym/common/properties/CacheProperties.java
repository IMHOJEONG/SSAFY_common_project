package com.B305.ogym.common.properties;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
    캐시 데이터의 만료 기간을 설정하기 위한 속성
 */


@Getter
@ConfigurationProperties(prefix = "cache.redis")
public class CacheProperties {

    private final Map<String, Long> ttl = new HashMap<>();
}
