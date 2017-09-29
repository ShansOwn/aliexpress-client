package com.shansown.aliexpress.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.shansown.aliexpress.config.Views;
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
  @JsonView(Views.Index.class)
  private Set<Long> categoryIds;

  @JsonView(Views.Index.class)
  private String title;

  @Column(name = "image_url")
  @JsonView(Views.Index.class)
  private String imageUrl;

  @Column(name = "product_url")
  private String productUrl;

  @Column(name = "promotion_url")
  @JsonView(Views.Index.class)
  private String promotionUrl;

  @Column(name = "valid_time")
  @JsonView(Views.Index.class)
  private Date validTime;

  @Column(name = "evaluate_score")
  @JsonView(Views.Index.class)
  private Float evaluateScore;

  @Column(name = "original_price")
  @JsonView(Views.Index.class)
  private Float originalPrice;

  @Column(name = "sale_price")
  @JsonView(Views.Index.class)
  private Float salePrice;

  @JsonView(Views.Index.class)
  private Integer discount;

  @JsonView(Views.Index.class)
  private Date updated;
}
