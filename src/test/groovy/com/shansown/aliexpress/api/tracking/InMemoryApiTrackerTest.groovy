package com.shansown.aliexpress.api.tracking

import com.shansown.aliexpress.api.request.GetPromotionLinksRequest
import com.shansown.aliexpress.api.request.GetPromotionProductDetailRequest
import com.shansown.aliexpress.api.request.ListPromotionProductRequest
import com.shansown.aliexpress.config.properties.AliAccessProperty
import spock.lang.Specification

import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.stream.IntStream
import java.util.stream.Stream

import static java.util.stream.Collectors.toList

class InMemoryApiTrackerTest extends Specification {

  static final Num = 10

  def apiTracker = Spy(InMemoryApiTracker)
  def aliAccess = new AliAccessProperty()
  def executor = Executors.newCachedThreadPool()

  def "concurrent access shouldn't harm"() {
    when:
    List<Future<?>> tasks1 = submitTasks(createListPromotionRequest())
    List<Future<?>> tasks2 = submitTasks(createGetPromotionLinksRequest())
    List<Future<?>> tasks3 = submitTasks(createGetPromotionProductDetailRequest())

    Stream.of(tasks1, tasks2, tasks3).flatMap({it.stream()}).forEach({ t -> t.get() })

    then:
    noExceptionThrown()
    Num * apiTracker.track(_ as GetPromotionLinksRequest)
    Num * apiTracker.track(_ as ListPromotionProductRequest)
    Num * apiTracker.track(_ as GetPromotionProductDetailRequest)
  }

  List<Future<?>> submitTasks(request) {
    IntStream.range(0, Num).boxed()
        .map({ i -> executor.submit({ -> apiTracker.track(request) }) })
        .collect(toList())
  }

  def createListPromotionRequest() {
    ListPromotionProductRequest.builder().access(aliAccess).fields("").keywords("").build()
  }

  def createGetPromotionLinksRequest() {
    GetPromotionLinksRequest.builder().access(aliAccess).fields("").build()
  }

  def createGetPromotionProductDetailRequest() {
    GetPromotionProductDetailRequest.builder().access(aliAccess).fields("").productId("").build()
  }
}
