package com.shansown.aliexpress.repository.search;

import com.shansown.aliexpress.model.Product;

public interface ProductSearch {

  String INDEX = "reseller";
  String TYPE = "product";

  boolean index(Product product);

  boolean index(Iterable<Product> products);
}
