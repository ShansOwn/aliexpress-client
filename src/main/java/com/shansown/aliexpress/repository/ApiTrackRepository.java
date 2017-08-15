package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.ApiTrack;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApiTrackRepository extends CrudRepository<ApiTrack, String> {

  @Modifying
  @Query("update ApiTrack t set t.requests = 0, t.lastUpdate = :date")
  void resetRequests(@Param("date") Date date);
}
