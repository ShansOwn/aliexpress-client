package com.shansown.aliexpress.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ali_product")
@Builder(builderClassName = "Builder")
public class Product {

  @Id
  private Long id;

  @Column(name = "category_id")
  private Long categoryId;

  private String title;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "product_url")
  private String productUrl;

  @Column(name = "promotion_url")
  private String promotionUrl;

  @Column(name = "valid_time")
  private Date validTime;

  @Column(name = "evaluate_score")
  private Float evaluateScore;

  @Column(name = "original_price")
  private Float originalPrice;

  @Column(name = "sale_price")
  private Float salePrice;

  private Integer discount;
}
