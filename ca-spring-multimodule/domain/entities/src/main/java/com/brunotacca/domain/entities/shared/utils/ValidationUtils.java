package com.brunotacca.domain.entities.shared.utils;

import java.util.Collection;

public class ValidationUtils {

  protected ValidationUtils() {}
  
  public boolean isNull(Object object) {
    return object == null;
  }

  public boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

  public boolean isNullOrEmpty(String string) {
		return isNull(string) || string.trim().isBlank();
	}

}
