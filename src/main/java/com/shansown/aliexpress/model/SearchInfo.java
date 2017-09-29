package com.shansown.aliexpress.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "search_info")
public class SearchInfo {

  @Id
  private long id = 1;

  private int version;
}
