package com.shansown.aliexpress.service.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.shansown.aliexpress.api.response.AliProduct
import com.shansown.aliexpress.config.AppConfig
import com.shansown.aliexpress.config.Views
import com.shansown.aliexpress.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [AppConfig.class])
class ProductMapperTest extends Specification {

  @Autowired
  ObjectMapper jsonMapper

  def "ali product should be mapped to product"() {
    given:
    def productMapper = new ProductMapper(new PriceMapper(), new DiscountMapper(), jsonMapper)

    when:
    AliProduct aliProduct = createAliProduct()
    Product product = productMapper.apply(aliProduct)

    then:
    product.id == aliProduct.productId
    product.title == aliProduct.productTitle
    product.imageUrl == aliProduct.imageUrl
    product.productUrl == aliProduct.productUrl
    product.validTime == aliProduct.validTime
    product.evaluateScore == aliProduct.evaluateScore
    product.originalPrice == 100F
    product.salePrice == 90F
    product.discount == 10
  }

  def "product should be mapped to json bytes"() {
    given:
    def view = Views.Public
    def productMapper = new ProductMapper(new PriceMapper(), new DiscountMapper(), jsonMapper)

    when:
    Product src = createProduct()
    Product result = jsonMapper.readerFor(Product.class).readValue(productMapper.apply(src, view))

    then:
    result.id == src.id
    result.title == src.title
    result.imageUrl == src.imageUrl
    result.productUrl == null // NOT Views.Public
    result.validTime == src.validTime
    result.evaluateScore == src.evaluateScore
    result.originalPrice == src.originalPrice
    result.salePrice == src.salePrice
    result.discount == src.discount
  }

  private static AliProduct createAliProduct() {
    AliProduct.builder()
            .productId(1)
            .productTitle("Title")
            .imageUrl("https://ae01.alicdn.com/kf/HTB1EJW.NXXXXXb0aXXXq6xXFXXXB/Meikon.jpg")
            .productUrl("https://www.aliexpress.com/item/Meikon/32729641596.html")
            .validTime(new Date())
            .evaluateScore(4.9F)
            .originalPrice("US \$100.00")
            .salePrice("US \$90.00")
            .discount("10%")
            .build()
  }

  private static Product createProduct() {
    Product.builder()
            .id(1)
            .title("Title")
            .imageUrl("https://ae01.alicdn.com/kf/HTB1EJW.NXXXXXb0aXXXq6xXFXXXB/Meikon.jpg")
            .productUrl("https://www.aliexpress.com/item/Meikon/32729641596.html")
            .validTime(new Date())
            .evaluateScore(4.9F)
            .originalPrice(100F)
            .salePrice(90F)
            .discount(10)
            .build()
  }
}
