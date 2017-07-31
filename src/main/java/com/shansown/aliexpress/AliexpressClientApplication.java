package com.shansown.aliexpress;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AliAccessProperty.class)
public class AliexpressClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(AliexpressClientApplication.class, args);
  }
}
