package com.shansown.aliexpress.repository.specification;

import com.shansown.aliexpress.model.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductForSaleSpecification implements Specification<Product> {

  @Value("${com.shansown.aliexpress.general.product-valid-hours}")
  private int productValidHours;

  @Override
  public Predicate toPredicate(@Nonnull Root<Product> root, @Nonnull CriteriaQuery<?> query,
      @Nonnull CriteriaBuilder criteria) {
    Date minimumValidTime = computeMinimumValidTime();
    Date minimumUpdatedTime = computeMinimumUpdatedTime();
    return criteria.and(
        criteria.greaterThanOrEqualTo(root.get("validTime"), minimumValidTime),
        criteria.greaterThanOrEqualTo(root.get("updated"), minimumUpdatedTime)
    );
  }

  private Date computeMinimumValidTime() {
    LocalTime midnight = LocalTime.MIDNIGHT;
    LocalDate today = LocalDate.now();
    LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
    return Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant());
  }

  private Date computeMinimumUpdatedTime() {
    LocalDateTime oldestValid = LocalDateTime.now().minusHours(productValidHours);
    return Date.from(oldestValid.atZone(ZoneId.systemDefault()).toInstant());
  }
}
