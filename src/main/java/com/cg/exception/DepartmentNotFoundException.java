package com.cg.exception;

@SuppressWarnings("serial")
public class DepartmentNotFoundException extends Exception {
	public DepartmentNotFoundException(String msg) {
		super(msg);
	}
}
