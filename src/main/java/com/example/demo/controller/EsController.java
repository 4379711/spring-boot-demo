package com.example.demo.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.example.demo.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author YaLong
 * @date 2022/6/26 12:33
 */
@RestController
@RequestMapping("/test/es")
public class EsController {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String INDEX_NAME = "test_index20220626";

    @GetMapping("/query")
    public void testSearch() throws IOException {
        SearchRequest request = SearchRequest.of(searchRequest ->
                searchRequest
                        .index(INDEX_NAME)
                        .from(0)
                        .size(20)
                        .query(q -> q.term(
                                t -> t.field("account")
                                        .value(v -> v.stringValue("yalong")))));
        SearchResponse<User> searchResponse = elasticsearchClient.search(request, User.class);
        HitsMetadata<User> hits = searchResponse.hits();
        System.out.println(hits);
    }

    @GetMapping("/get")
    public void testGetDocument() throws IOException {
        GetResponse<User> getResponse = elasticsearchClient.get(getRequest -> getRequest.index(INDEX_NAME).id("aaa"), User.class);
        System.out.println(getResponse);
    }

    @GetMapping("/add")
    public void testAddDocument() throws IOException {

        IndexResponse indexResponse = elasticsearchClient.index(indexRequest -> indexRequest.index(INDEX_NAME).document(new User().setAccount("yalong").setId(1).setPassword("yalong").setSalt("I am salt")));
        System.out.println(indexResponse);

    }


}


