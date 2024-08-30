package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query(value = "select * from departments d where(select count(*) from employees e where e.department_department_id = d.department_id) = (select max(emp_count) from (select count(*) as emp_count from employees group by department_department_id) AS max_counts)", nativeQuery = true)
	List<Department> findWithMaxEmployees();
	
}
