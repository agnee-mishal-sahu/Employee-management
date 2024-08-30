package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.Employee;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query(value="select * from employees where employee_name like %:employeeName%",nativeQuery=true)
	List<Employee> findByEmployeeName(String employeeName);
	List<Employee> findByDesignation(String designation);
	
	@Query(value="Select * from employees where salary> :salary",nativeQuery = true)
	List<Employee> findBySalaryGreaterThan(double salary);
}
