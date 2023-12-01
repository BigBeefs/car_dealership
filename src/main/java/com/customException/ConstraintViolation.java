package com.customException;

public class ConstraintViolation extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public ConstraintViolation(String message) {
		super(message);
	}
}
