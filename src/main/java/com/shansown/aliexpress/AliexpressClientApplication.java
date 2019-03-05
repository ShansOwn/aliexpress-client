package com.shansown.aliexpress;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import com.shansown.aliexpress.config.properties.ApiTrackingProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({AliAccessProperty.class, ApiTrackingProperty.class})
public class AliexpressClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AliexpressClientApplication.class, args);
  }
}
