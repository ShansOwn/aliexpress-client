package com.shansown.aliexpress.repository.specification;

import com.shansown.aliexpress.model.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class ProductsByValidAndUpdatedTimeSpecification implements Specification<Product> {

  @Override
  public Predicate toPredicate(@Nonnull Root<Product> root, @Nonnull CriteriaQuery<?> query,
                               @Nonnull CriteriaBuilder criteria) {
    Date minimumValidTime = minimumValidTime();
    Date minimumUpdatedTime = minimumUpdatedTime();
    return criteria.and(
        criteria.greaterThanOrEqualTo(root.get("validTime"), minimumValidTime),
        criteria.greaterThanOrEqualTo(root.get("updated"), minimumUpdatedTime)
    );
  }

  protected abstract Date minimumUpdatedTime();

  private Date minimumValidTime() {
    LocalTime midnight = LocalTime.MIDNIGHT;
    LocalDate today = LocalDate.now();
    LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
    return Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant());
  }
}
