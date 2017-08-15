package com.shansown.aliexpress;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import com.shansown.aliexpress.config.properties.ApiTrackingProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({AliAccessProperty.class, ApiTrackingProperty.class})
public class AliexpressClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AliexpressClientApplication.class, args);
  }
}
