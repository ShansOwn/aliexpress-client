package com.shansown.aliexpress.repository.search;

import com.shansown.aliexpress.model.SearchInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SearchInfoRepository extends Repository<SearchInfo, Long> {

  @Query("select i.version from SearchInfo i where i.id = 1")
  Optional<Integer> getCurrentVersion();

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO search_info (id, version) VALUES (1, ?1) ON CONFLICT (id) DO UPDATE SET version = ?1", nativeQuery = true)
  void setCurrentVersion(@Param("version") int version);
}
