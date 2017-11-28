package com.shansown.aliexpress.repository.specification

import com.shansown.aliexpress.model.Product
import com.shansown.aliexpress.repository.ProductRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicLong

@DataJpaTest
@TestPropertySource(properties = "com.shansown.aliexpress.general.product-valid-hours=24")
@Import(ProductForSaleSpecification.class)
class ProductForSaleSpecificationTest extends Specification {

  private static final AtomicLong Ids = new AtomicLong()

  @Value('${com.shansown.aliexpress.general.product-valid-hours}')
  private int productValidHours

  @Autowired
  private TestEntityManager entityManager

  @Autowired
  private ProductRepository productRepository

  @Autowired
  private org.springframework.data.jpa.domain.Specification<Product> productForSaleSpecification

  def "specification should return only valid for sale products"() {
    given:
    def now = DateTime.now()
    def updatedIncludeLow = now.minusHours(productValidHours).plusMinutes(1).toDate()
    def updatedExcludeLow = now.minusHours(productValidHours).minusMinutes(1).toDate()
    def updatedIncludeHigh = now.minusMinutes(1).toDate()

    def validTimeIncludeLow = now.withTimeAtStartOfDay().toDate()
    def validTimeExcludeLow = now.withTimeAtStartOfDay().minusMinutes(1).toDate()
    def validTimeIncludeHigh = now.minusMinutes(1).toDate()

    entityManager.persist(createWithDates(updatedIncludeLow, validTimeIncludeLow)) // should be returned
    entityManager.persist(createWithDates(updatedIncludeLow, validTimeExcludeLow))
    entityManager.persist(createWithDates(updatedIncludeLow, validTimeIncludeHigh)) // should be returned

    entityManager.persist(createWithDates(updatedExcludeLow, validTimeIncludeLow))
    entityManager.persist(createWithDates(updatedExcludeLow, validTimeExcludeLow))
    entityManager.persist(createWithDates(updatedExcludeLow, validTimeIncludeHigh))

    entityManager.persist(createWithDates(updatedIncludeHigh, validTimeIncludeLow)) // should be returned
    entityManager.persist(createWithDates(updatedIncludeHigh, validTimeExcludeLow))
    entityManager.persist(createWithDates(updatedIncludeHigh, validTimeIncludeHigh)) // should be returned

    when:
    def pageRequest = new PageRequest(0, 10)
    def products = productRepository.findAll(productForSaleSpecification, pageRequest)

    then:
    products.size() == 4
  }

  private static Product createWithDates(Date updated, Date validTime) {
    Product.builder()
            .id(Ids.incrementAndGet())
            .updated(updated)
            .validTime(validTime)
            .build()
  }
}
