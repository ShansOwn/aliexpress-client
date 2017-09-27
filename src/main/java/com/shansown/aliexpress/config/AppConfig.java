package com.shansown.aliexpress.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;

@Configuration
public class AppConfig {

  @Bean
  ObjectMapper jsonMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DEFAULT_VIEW_INCLUSION);
    return mapper;
  }
}
