package com.example.demo.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YaLong
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty({"redisson.host"})
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonConfig {
    private String host;

    private String port;

    private String password;

    private Integer dbIndex;

    /**
     * 连接池配置
     */
    private Integer connectPoolSize;
    private Integer idleSize;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port)
                .setDatabase(dbIndex)
                .setConnectionMinimumIdleSize(idleSize)
                .setConnectionPoolSize(connectPoolSize);
        if (password != null && !"".equals(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

}
