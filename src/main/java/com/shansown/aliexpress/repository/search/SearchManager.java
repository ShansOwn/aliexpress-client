package com.shansown.aliexpress.repository.search;

public interface SearchManager {

  String INDEX = "reseller";

  boolean checkConnection();

  void migrate(int fromVersion, int toVersion);
}
