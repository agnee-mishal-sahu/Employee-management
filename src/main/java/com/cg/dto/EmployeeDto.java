package com.cg.dto;

import lombok.Data;

@Data
public class EmployeeDto {
	private int employeeId;
	private String employeeName;
	private String designation;
	private String email;
	private double salary;
	private int age;
}
