package com.cg.service;

import java.util.List;

import com.cg.dto.DepartmentDto;
import com.cg.dto.EmployeeDto;
import com.cg.entity.Employee;
import com.cg.exception.DepartmentNotFoundException;
import com.cg.exception.EmployeeNotFoundException;

public interface EmployeeService {

	public EmployeeDto addEmployee(Employee employee) throws DepartmentNotFoundException, EmployeeNotFoundException;
	public String updateEmployee(String employeeId,Employee employee) throws EmployeeNotFoundException, DepartmentNotFoundException;
	public String deleteEmployee(String employeeId) throws EmployeeNotFoundException ;
	public List<EmployeeDto> getAllEmployees();
	public EmployeeDto getEmployeeById(String employeeId) throws EmployeeNotFoundException;
	public List<EmployeeDto> getEmployeeByName(String employeeName) throws EmployeeNotFoundException;
	public DepartmentDto getDepartmentOfEmployee(String employeeId) throws EmployeeNotFoundException;
	public List<EmployeeDto> getEmployeeByDesignation(String designation);
	public EmployeeDto getEmployeeByEmail(String email);
	public List<EmployeeDto> getBySalaryMoreThan(String salary) throws EmployeeNotFoundException;
}
