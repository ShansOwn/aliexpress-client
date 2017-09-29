package com.shansown.aliexpress.repository.search;

import com.shansown.aliexpress.model.Product;

public interface ProductSearch {

  String TYPE = "product";

  boolean index(Product product);

  boolean index(Iterable<Product> products);
}
