package com.shansown.aliexpress.repository.search;

import com.shansown.aliexpress.config.Views;
import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.model.error.SearchError;
import com.shansown.aliexpress.service.mapper.ProductMapper;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.elasticsearch.action.DocWriteResponse.Result.CREATED;
import static org.elasticsearch.action.DocWriteResponse.Result.UPDATED;
import static org.elasticsearch.common.xcontent.XContentType.JSON;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductElasticSearch implements ProductSearch {

  @Value("${com.shansown.aliexpress.elasticsearch.timeout}")
  private long timeout;

  private final RestHighLevelClient client;
  private final ProductMapper mapper;

  @Override
  public boolean index(Product product) {
    IndexResponse response;
    try {
      response = client.index(indexRequest(product));
    } catch (IOException e) {
      throw new SearchError("Couldn't index product", e);
    }
    return response.getResult() == CREATED || response.getResult() == UPDATED;
  }

  @Override
  public boolean index(Iterable<Product> products) {
    BulkRequest bulkRequest = new BulkRequest();
    long count = StreamSupport.stream(products.spliterator(), false)
        .peek(p -> bulkRequest.add(indexRequest(p)))
        .count();
    log.debug("Indexing {} products", count);
    bulkRequest.timeout(TimeValue.timeValueMillis(5 * timeout));
    BulkResponse response;
    try {
      response = client.bulk(bulkRequest);
    } catch (IOException e) {
      throw new SearchError("Couldn't index products batch", e);
    }
    boolean success = !response.hasFailures();
    if (!success) {
      log.warn("Batch index has errors!");
      StreamSupport.stream(response.spliterator(), false)
          .map(BulkItemResponse::getFailure)
          .filter(Objects::nonNull)
          .forEach(f -> log.warn("Index failure: {}", f));
    }
    return success;
  }

  private IndexRequest indexRequest(Product product) {
    return new IndexRequest(INDEX, TYPE, String.valueOf(product.getId()))
        .source(mapper.apply(product, Views.Index.class), JSON)
        .timeout(TimeValue.timeValueMillis(timeout));
  }
}
