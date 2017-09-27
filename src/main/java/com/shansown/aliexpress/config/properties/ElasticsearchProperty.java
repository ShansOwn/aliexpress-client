package com.shansown.aliexpress.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.shansown.aliexpress.elasticsearch")
public class ElasticsearchProperty {
  private String host;
  private int port;
  private String scheme;
  private String user;
  private String password;
  private long timeout;
}
