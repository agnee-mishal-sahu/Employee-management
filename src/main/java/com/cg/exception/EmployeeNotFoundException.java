package com.cg.exception;

@SuppressWarnings("serial")
public class EmployeeNotFoundException extends Exception{
	
	public EmployeeNotFoundException(String msg){
		super(msg);
	}

}
