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
import com.cg.entity.Department;
import com.cg.exception.DepartmentNotFoundException;
import com.cg.serviceImpl.DepartmentServiceImpl;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	DepartmentServiceImpl departmentServiceImpl;

	@PostMapping("/add")
	public ResponseEntity<?> addDepartment(@RequestBody Department department) throws DepartmentNotFoundException {
		return new ResponseEntity<>(departmentServiceImpl.addDepartment(department), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateDepartment(@PathVariable("id") String id, @RequestBody Department department)
			throws DepartmentNotFoundException {

		return new ResponseEntity<>(departmentServiceImpl.updateDepartment(id, department), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable String id) throws DepartmentNotFoundException {

		return ResponseEntity.ok(departmentServiceImpl.deleteDepartment(id));
	}

	@GetMapping("/getall")
	public ResponseEntity<List<DepartmentDto>> getAllDepartment() {
		return ResponseEntity.ok(departmentServiceImpl.getAllDepartment());
	}

	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getByDepartmentId(@PathVariable String id) throws DepartmentNotFoundException {
		return new ResponseEntity<>(departmentServiceImpl.getDepartmentById(id), HttpStatus.OK);
	}

	@GetMapping("/byname/{name}")
	public ResponseEntity<DepartmentDto> getByDepartmentName(@PathVariable String name)throws DepartmentNotFoundException {
		return ResponseEntity.ok(departmentServiceImpl.getDepartmentByName(name));
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getEmployeesBydepartment(@PathVariable String id) throws DepartmentNotFoundException {

		return new ResponseEntity<>(departmentServiceImpl.getEmployeesByDepartment(id), HttpStatus.OK);
	}
	@GetMapping("/maxemployee")
	public ResponseEntity<List<DepartmentDto>> getByMaxEmployee(){
		return ResponseEntity.ok(departmentServiceImpl.getDepartmentWithMaxEmployee());
	}
	@RequestMapping("/**")
	public ResponseEntity<String> handleInvalidRequests() {
		return new ResponseEntity<>("Invalid URL. Please check your request.", HttpStatus.NOT_FOUND);
	}

}
