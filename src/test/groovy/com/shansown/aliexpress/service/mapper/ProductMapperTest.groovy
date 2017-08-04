package com.shansown.aliexpress.service.mapper

import com.shansown.aliexpress.api.response.AliProduct
import com.shansown.aliexpress.model.Product
import spock.lang.Specification

class ProductMapperTest extends Specification {

  def productMapper = new ProductMapper(new PriceMapper(), new DiscountMapper())

  def "price should be mapped correctly"() {
    when:
    AliProduct aliProduct = AliProduct.builder()
            .productId(1)
            .productTitle("Title")
            .imageUrl("https://ae01.alicdn.com/kf/HTB1EJW.NXXXXXb0aXXXq6xXFXXXB/Meikon.jpg")
            .productUrl("\"https://www.aliexpress.com/item/Meikon/32729641596.html")
            .validTime(new Date())
            .evaluateScore(4.9F)
            .originalPrice("US \$100.00")
            .salePrice("US \$90.00")
            .discount("10%")
            .build()
    Product product = productMapper.apply(aliProduct)

    then:
    product.getId() == aliProduct.getProductId()
    product.getTitle() == aliProduct.getProductTitle()
    product.getImageUrl() == aliProduct.getImageUrl()
    product.getProductUrl() == aliProduct.productUrl
    product.getValidTime() == aliProduct.getValidTime()
    product.getEvaluateScore() == aliProduct.getEvaluateScore()
    product.getOriginalPrice() == 100F
    product.getSalePrice() == 90F
    product.getDiscount() == 10

    where:
    priceString || price
    "US \$2.99" || 2.99F
    "US \$1.00" || 1.0F
    "1.33"      || 1.33F
    "Not price" || null
  }
}
