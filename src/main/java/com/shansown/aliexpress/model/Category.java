package com.shansown.aliexpress.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ali_category")
public class Category {

  @Id
  private Long id;

  private String name;
}
