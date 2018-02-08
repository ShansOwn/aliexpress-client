package com.shansown.aliexpress.repository.specification

import com.shansown.aliexpress.model.Product
import com.shansown.aliexpress.repository.ProductRepository
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicLong

@DataJpaTest
class ValidProductsByUpdatedTimeSpecificationTest extends Specification {

    private static final AtomicLong Ids = new AtomicLong()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    private ProductRepository productRepository

    def "specification should return only updated products"() {
        given:
        def updatedFrom = DateTime.now().minusDays(1)
        def now = DateTime.now()
        def updatedIncludeLow = updatedFrom.plusMinutes(1).toDate()
        def updatedExcludeLow = updatedFrom.minusMinutes(1).toDate()
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
        org.springframework.data.jpa.domain.Specification<Product> validProductsByUpdatedTimeSpecification =
                new ValidProductsByUpdatedTimeSpecification(updatedFrom.toDate())
        def pageRequest = new PageRequest(0, 10)
        def products = productRepository.findAll(validProductsByUpdatedTimeSpecification, pageRequest)

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
