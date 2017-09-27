package com.shansown.aliexpress.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shansown.aliexpress.api.response.AliProduct;
import com.shansown.aliexpress.config.Views;
import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.model.error.MappingError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductMapper implements ModelMapper<AliProduct, Product>, JsonMapper<Product, Class<?>, byte[]> {

  private final PriceMapper priceMapper;
  private final DiscountMapper discountMapper;
  private final ObjectMapper jsonMapper;

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

  @Override
  public byte[] apply(Product product, Class<?> view) {
    try {
      return jsonMapper.writerWithView(Views.Index.class).writeValueAsBytes(product);
    } catch (JsonProcessingException e) {
      throw new MappingError(String.format("Couldn't map next product: %s", product), e);
    }
  }
}
