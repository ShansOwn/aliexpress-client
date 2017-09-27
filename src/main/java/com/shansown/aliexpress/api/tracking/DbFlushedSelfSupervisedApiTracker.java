package com.shansown.aliexpress.api.tracking;

import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.config.properties.ApiTrackingProperty;
import com.shansown.aliexpress.model.ApiTrack;
import com.shansown.aliexpress.repository.ApiTrackRepository;
import com.shansown.aliexpress.service.mapper.ApiTrackMapper;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DbFlushedSelfSupervisedApiTracker implements ApiTracker {

  private final ApiTrackingProperty apiTrackingProperty;
  private final ApiTracker apiTracker;
  private final ApiTrackMapper apiTrackMapper;
  private final ApiTrackRepository apiTrackRepository;

  @PostConstruct
  public void init() {
    log.info("Initializing...");
    if (isMissScheduledReset()) {
      resetStatistics().subscribe();
    } else {
      apiTracker.setStatistics(mapStatistics(apiTrackRepository.findAll())).subscribe();
    }
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroying...");
    flushStatistics();
  }

  @Scheduled(cron = "${com.shansown.aliexpress.api-track.flush-statistics}")
  public void flushStatistics() {
    final Date update = new Date();
    log.info("Flush api track statistics with date: {}", update);
    apiTracker.getStatistics()
        .map(s -> mapStatistics(s, update))
        .subscribe(apiTrackRepository::saveAll);
  }

  @Scheduled(cron = "${com.shansown.aliexpress.api-track.reset-statistics}")
  public void resetStatisticsBySchedule() {
    resetStatistics().subscribe();
  }

  @Override
  public Mono<Boolean> track(AliRequest<?> request) {
    return apiTracker.track(request);
  }

  @Override
  public Mono<Map<AliApiMethod, Integer>> getStatistics() {
    return apiTracker.getStatistics();
  }

  @Override
  public Mono<Void> setStatistics(Map<AliApiMethod, Integer> statistics) {
    return apiTracker.setStatistics(statistics).doOnSuccess(__ -> flushStatistics());
  }

  @Override
  public Mono<Void> resetStatistics() {
    final Date update = new Date();
    log.info("Reset api track statistics with date: {}", update);
    return apiTracker.resetStatistics()
        .doOnSuccess(__ -> apiTrackRepository.resetRequests(update));
  }

  private Collection<ApiTrack> mapStatistics(Map<AliApiMethod, Integer> statistics, Date update) {
    return statistics.entrySet().stream()
        .map(apiTrackMapper::apply)
        .peek(t -> t.setLastUpdate(update))
        .collect(toSet());
  }

  private Map<AliApiMethod, Integer> mapStatistics(Iterable<ApiTrack> statistics) {
    return StreamSupport.stream(statistics.spliterator(), false)
        .collect(toMap(t -> AliApiMethod.byMethodName(t.getMethod()), ApiTrack::getRequests));
  }

  private boolean isMissScheduledReset() {
    Date nextExecution =
        new CronTrigger(apiTrackingProperty.getResetStatistics()).nextExecutionTime(new SimpleTriggerContext());
    Objects.requireNonNull(nextExecution, "Reset statistics cron is wrong");

    return StreamSupport.stream(apiTrackRepository.findAll().spliterator(), false)
        .map(ApiTrack::getLastUpdate)
        .filter(Objects::nonNull)
        .map(DateTime::new)
        .allMatch(update -> update.plusDays(1).isBefore(nextExecution.getTime()));
  }
}
