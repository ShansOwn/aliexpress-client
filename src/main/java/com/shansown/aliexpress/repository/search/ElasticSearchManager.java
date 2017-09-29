package com.shansown.aliexpress.repository.search;

import com.google.common.io.Resources;
import com.shansown.aliexpress.model.error.SearchError;
import com.shansown.aliexpress.service.ProductService;
import java.io.IOException;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static java.util.Collections.emptyMap;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ElasticSearchManager implements SearchManager {

  private static final int CURRENT_VERSION = 1;
  private static final String SETTINGS = "search/elastic_settings.json";

  private final RestHighLevelClient client;
  private final RestClient lowLevelClient;

  private final ProductService productService;

  private final SearchInfoRepository searchInfoRepository;

  @PostConstruct
  private void init() {
    log.info("Checking Elasticsearch connection...");
    boolean success = checkConnection();
    log.info("Elasticsearch connections is {}OK", success ? "" : "NOT ");
    int prevVersion = searchInfoRepository.getCurrentVersion().orElse(0);
    if (CURRENT_VERSION != prevVersion) {
      migrate(prevVersion, CURRENT_VERSION);
    }
  }

  @Override
  public boolean checkConnection() {
    try {
      return client.ping();
    } catch (IOException e) {
      throw new SearchError("Couldn't connect to Elasticsearch", e);
    }
  }

  @Override
  public void migrate(int fromVersion, int toVersion) {
    log.info("Migrating Elasticsearch index from {} to {}", fromVersion, toVersion);
    try {
      recreateSettings(); // Just recreate settings at this moment
      reindexAll();
      searchInfoRepository.setCurrentVersion(toVersion);
    } catch (IOException e) {
      throw new SearchError("Couldn't process Elasticsearch migration", e);
    }
  }

  private void recreateSettings() throws IOException {
    Map<String, String> params = emptyMap();
    String settings = Resources.toString(getResource(SETTINGS), UTF_8);
    HttpEntity entity = new NStringEntity(settings, APPLICATION_JSON);
    lowLevelClient.performRequest("DELETE", INDEX, params); // delete index
    lowLevelClient.performRequest("PUT", INDEX, params, entity); // create index with new settings
  }

  private void reindexAll() {
    productService.reindexAll().doOnSuccess(count -> log.info("{} products reindexed successfully", count)).subscribe();
  }
}
