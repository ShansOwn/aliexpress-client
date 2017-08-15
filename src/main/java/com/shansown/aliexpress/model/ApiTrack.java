package com.shansown.aliexpress.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PACKAGE)
@Table(name = "ali_api_track")
@Builder(builderClassName = "Builder")
public class ApiTrack {

  @Id
  private String method;

  private Integer requests;

  @Column(name = "last_update")
  private Date lastUpdate;
}
