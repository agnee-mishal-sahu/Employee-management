package com.cg.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenceGeneratorDepartment")
	@SequenceGenerator(initialValue = 100,name="sequenceGeneratorDepartment")
	private int departmentId;
	private String departmentName;
	@OneToMany(mappedBy = "department",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Employee> employeeList;
	
}
