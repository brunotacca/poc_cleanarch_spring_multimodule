package com.brunotacca.domain.entities.shared.exceptions.causes;

import com.brunotacca.domain.entities.shared.exceptions.DomainException;

public class RequiredFieldException extends DomainException {

  public final static String PATTERN = "Field %s is required.";
  
	public RequiredFieldException(String fieldName) {
		super(String.format(PATTERN, fieldName));
	}



}
