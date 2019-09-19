package com.example.vpk;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ElasticSearchCustomer {
    public static RestHighLevelClient createClient(){

        //https://quybax1ypc:qmj4pgst6@kafka-cluster-6950634811.ap-southeast-2.bonsaisearch.net:443
        final String hostName = "kafka-cluster-6950634811.ap-southeast-2.bonsaisearch.net";
        final String username = "quybax1ypc";
        final String password = "qmj4pgst6";

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));

        RestClientBuilder restClientBuilder = RestClient.builder( new HttpHost(hostName,443,"https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = createClient();
        String jsonString = "{\"health\": \"good\",\"strength\": \"excellent\"}";
        IndexRequest indexRequest = new IndexRequest("status","device","123")
                .source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        indexResponse.getId();
        client.close();
    }
}
