package com.B305.ogym;

import com.B305.ogym.common.properties.CacheProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
//@SpringBootApplication(
//    exclude = {RedisRepositoriesAutoConfiguration.class}
//)
@SpringBootApplication
@EnableConfigurationProperties(value = {CacheProperties.class})
public class OgymApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgymApplication.class, args);
    }

}
