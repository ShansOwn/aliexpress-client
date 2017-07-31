package com.shansown.aliexpress.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.shansown.aliexpress.ali-access")
public class AliAccessProperty {
  private String apiKey;
  private String trackingId;
  private int callFrequency;
  private String signature;
}
