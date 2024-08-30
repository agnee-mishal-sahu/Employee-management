package com.cg.service;

import java.util.List;

import com.cg.dto.DepartmentDto;
import com.cg.dto.EmployeeDto;
import com.cg.entity.Department;
import com.cg.exception.DepartmentNotFoundException;

public interface DepartmentService {

	public DepartmentDto addDepartment(Department department) throws DepartmentNotFoundException;
	public String updateDepartment(String departmentId,Department department) throws DepartmentNotFoundException;
	public String deleteDepartment(String departmentId) throws DepartmentNotFoundException;
	public List<DepartmentDto> getAllDepartment();
	public DepartmentDto getDepartmentById(String departmentId) throws DepartmentNotFoundException;
	public DepartmentDto getDepartmentByName(String departmentName) throws DepartmentNotFoundException;
	public List<EmployeeDto> getEmployeesByDepartment(String departmentId) throws DepartmentNotFoundException;
	public List<DepartmentDto> getDepartmentWithMaxEmployee();
}
