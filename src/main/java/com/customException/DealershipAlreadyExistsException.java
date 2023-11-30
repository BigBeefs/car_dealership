package com.customException;

public class DealershipAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6023950346309634231L;

	public DealershipAlreadyExistsException(String message) {

		super(message);
	}

}
