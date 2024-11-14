package com.ElectronicStore.Exceptions;

public class BadApiRequest extends RuntimeException{

	
	private static final long serialVersionUID = 7699924978631070983L;

	public BadApiRequest(String message) {
		super(message);
	}
	
	public BadApiRequest() {
		super("Bad Request !! ");
	}
	
}
