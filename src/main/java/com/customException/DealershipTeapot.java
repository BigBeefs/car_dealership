package com.customException;

public class DealershipTeapot extends RuntimeException {

	private static final long serialVersionUID = 278859563795936296L;

	public DealershipTeapot(String message) {

		super(message);
	}

}
