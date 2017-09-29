package com.shansown.aliexpress.config;

import com.shansown.aliexpress.config.properties.ElasticsearchProperty;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.http.auth.AuthScope.ANY;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperty.class)
public class SearchConfig {

  @Bean
  RestHighLevelClient elasticClient(RestClient lowLevelRestClient) {
    return new RestHighLevelClient(lowLevelRestClient);
  }

  @Bean
  RestClient lowLevelRestClient(ElasticsearchProperty property) {
    final CredentialsProvider creds = new BasicCredentialsProvider();
    creds.setCredentials(ANY, new UsernamePasswordCredentials(property.getUser(), property.getPassword()));
    return RestClient.builder(new HttpHost(property.getHost(), property.getPort(), property.getScheme()))
        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(creds))
        .build();
  }
}
