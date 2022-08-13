package com.brunotacca.domain.entities.shared.exceptions.causes;

import com.brunotacca.domain.entities.shared.exceptions.BusinessException;

public class RequiredFieldException extends BusinessException {

  public static final String PATTERN = "Field %s is required.";
  
	public RequiredFieldException(String fieldName) {
		super(String.format(PATTERN, fieldName));
	}



}
