package com.rabobank.exception;

public class ApplicationException  extends RuntimeException {

	public static final long serialVersionUID = 1L;

	public ApplicationException(String s) 
    { 
        super(s); 
    }
}
