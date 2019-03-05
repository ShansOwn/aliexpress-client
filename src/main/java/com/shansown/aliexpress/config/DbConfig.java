package com.shansown.aliexpress.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class DbConfig {

  @Value("${spring.datasource.hikari.maximum-pool-size}")
  private int maxPoolSize;

  @Bean
  Scheduler dbScheduler() {
    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("db-pool-%d").build();
    return Schedulers.fromExecutorService(Executors.newFixedThreadPool(maxPoolSize, threadFactory));
  }
}
