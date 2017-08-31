package com.shansown.aliexpress.model;

import java.util.Date;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ali_product")
@Builder(builderClassName = "Builder")
public class Product {

  @Id
  private Long id;

  @Column(name = "category_id")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "ali_category_ali_product", joinColumns = @JoinColumn(name = "product_id"))
  private Set<Long> categoryIds;

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
