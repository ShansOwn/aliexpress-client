package com.shansown.aliexpress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpConfig {

  @Bean
  WebClient asyncHttpClient() {
    return WebClient.create();
  }
}
