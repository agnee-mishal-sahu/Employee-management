package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.DepartmentDto;
import com.cg.dto.EmployeeDto;
import com.cg.entity.Employee;
import com.cg.exception.DepartmentNotFoundException;
import com.cg.exception.EmployeeNotFoundException;
import com.cg.serviceImpl.EmployeeServiceImpl;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) throws DepartmentNotFoundException, EmployeeNotFoundException {
		
		return new ResponseEntity<>(employeeServiceImpl.addEmployee(employee), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Employee employee)
			throws EmployeeNotFoundException, DepartmentNotFoundException {
			return new ResponseEntity<>(employeeServiceImpl.updateEmployee(id, employee), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws EmployeeNotFoundException {
		
			return new ResponseEntity<>(employeeServiceImpl.deleteEmployee(id), HttpStatus.OK);

	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable String id) throws EmployeeNotFoundException {
		
			return new ResponseEntity<>(employeeServiceImpl.getEmployeeById(id), HttpStatus.OK);

	}

	@GetMapping("/getall")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		return new ResponseEntity<>(employeeServiceImpl.getAllEmployees(), HttpStatus.OK);
	}

	@GetMapping("/department/{employeeId}")
	public ResponseEntity<?> getDepartmentOfEmployee(@PathVariable String employeeId) throws EmployeeNotFoundException {
	
			DepartmentDto department = employeeServiceImpl.getDepartmentOfEmployee(employeeId);
			return ResponseEntity.ok(department);
		
	}
	@GetMapping("/getbyname/{name}")
	public ResponseEntity<List<EmployeeDto>> getEmployeeByName(@PathVariable String name)
			throws EmployeeNotFoundException {
		return new ResponseEntity<>(employeeServiceImpl.getEmployeeByName(name), HttpStatus.OK);
	}
	@GetMapping("/getbydesignation/{designation}")
	public ResponseEntity<List<EmployeeDto>> getEmployeeByDesignation(@PathVariable String designation)
			throws EmployeeNotFoundException {
		return new ResponseEntity<>(employeeServiceImpl.getEmployeeByDesignation(designation), HttpStatus.OK);
	}

	@GetMapping("/getbyemail/{email}")
	public ResponseEntity<EmployeeDto> getEmployeeByMail(@PathVariable String email) throws EmployeeNotFoundException {
		return new ResponseEntity<>(employeeServiceImpl.getEmployeeByEmail(email), HttpStatus.OK);
	}
	
	@GetMapping("/getbysalary/{salary}")
	public ResponseEntity<List<EmployeeDto>> getBySalaryMoreThan(@PathVariable String salary) throws EmployeeNotFoundException{
		return ResponseEntity.ok(employeeServiceImpl.getBySalaryMoreThan(salary));
	}

	@RequestMapping("/**")
	public ResponseEntity<String> handleInvalidRequests() {
		return new ResponseEntity<>("Invalid URL. Please check your request.", HttpStatus.NOT_FOUND);
	}
}
