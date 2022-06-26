package com.example.demo.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YaLong
 * @date 2022/6/26 12:23
 */
@Configuration
public class ElasticsearchClientConfig {

    @Bean
    public RestClient restClient() {

        // 转换成 HttpHost 数组
        HttpHost httpHost0 = new HttpHost("192.168.1.29", 9200,"http");
        HttpHost[] httpHost = new HttpHost[]{httpHost0};

        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(10000);
            requestConfigBuilder.setSocketTimeout(10000);
            requestConfigBuilder.setConnectionRequestTimeout(10000);
            return requestConfigBuilder;
        });
        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(100);
            httpClientBuilder.setMaxConnPerRoute(100);
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
            basicCredentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic","changeme"));
            httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
            return httpClientBuilder;
        });
        return builder.build();
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        return new RestClientTransport(
                restClient, new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        return new ElasticsearchClient(transport);
    }
}
