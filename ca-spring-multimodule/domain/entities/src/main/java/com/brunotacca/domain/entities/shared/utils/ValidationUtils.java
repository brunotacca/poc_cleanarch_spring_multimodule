package com.brunotacca.domain.entities.shared.utils;

import java.util.Collection;

public class ValidationUtils {
  
  private ValidationUtils() {}

  public static boolean isNull(Object object) {
    return object == null;
  }

  public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

  public static boolean isNullOrEmpty(String string) {
		return isNull(string) || string.trim().isBlank();
	}

}
