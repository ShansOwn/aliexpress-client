package com.shansown.aliexpress.service.mapper;

import com.shansown.aliexpress.api.response.AliProduct;
import com.shansown.aliexpress.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductMapper implements ModelMapper<AliProduct, Product> {

  private final PriceMapper priceMapper;
  private final DiscountMapper discountMapper;

  @Override
  public Product apply(AliProduct aliProduct) {
    return Product.builder()
        .id(aliProduct.getProductId())
        .title(aliProduct.getProductTitle())
        .imageUrl(aliProduct.getImageUrl())
        .productUrl(aliProduct.getProductUrl())
        .validTime(aliProduct.getValidTime())
        .evaluateScore(aliProduct.getEvaluateScore())
        .originalPrice(priceMapper.apply(aliProduct.getOriginalPrice()))
        .salePrice(priceMapper.apply(aliProduct.getSalePrice()))
        .discount(discountMapper.apply(aliProduct.getDiscount()))
        .build();
  }
}
