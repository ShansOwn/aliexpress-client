package com.shansown.aliexpress.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.shansown.aliexpress.api-track")
public class ApiTrackingProperty {
  private String resetStatistics;
  private String flushStatistics;
}
