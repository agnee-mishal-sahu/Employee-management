package com.cg.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.DepartmentDto;
import com.cg.dto.EmployeeDto;
import com.cg.entity.Department;
import com.cg.entity.Employee;
import com.cg.exception.DepartmentNotFoundException;
import com.cg.exception.EmployeeNotFoundException;
import com.cg.repository.DepartmentRepository;
import com.cg.repository.EmployeeRepository;
import com.cg.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	String regexp ="^[a-zA-Z]+[a-zA-Z\\s]*$";
	String emailRegex= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	@Override
	public EmployeeDto addEmployee(Employee employee) throws DepartmentNotFoundException, EmployeeNotFoundException {
		
		validateEmployee(employee);
		employeeRepository.save(employee);
		EmployeeDto employeeDto = mapEmployeeToDto(employee);
		return employeeDto;
	}

	@Override
	public String updateEmployee(String employeeId, Employee employee) throws EmployeeNotFoundException, DepartmentNotFoundException {
		
		try {
			int empId=Integer.parseInt(employeeId);
			Optional<Employee> object = employeeRepository.findById(empId);
			if (object.isEmpty()) {
				throw new EmployeeNotFoundException("Invalid Employee Id");
			}
			validateEmployee(employee);
			object.get().setEmployeeName(employee.getEmployeeName());
			object.get().setDesignation(employee.getDesignation());
			object.get().setAge(employee.getAge());
			object.get().setEmail(employee.getEmail());
			object.get().setSalary(employee.getSalary());
			object.get().setDepartment(employee.getDepartment());

			employeeRepository.save(object.get());

			return "Employee details updated Successfully";
		}catch(NumberFormatException e) {
			return "Invalid employee ID. Please provide a valid integer value.";
		}
	}

	@Override
	public String deleteEmployee(String employeeId) throws EmployeeNotFoundException {
		try {
			int empId=Integer.parseInt(employeeId);
			Optional<Employee> object = employeeRepository.findById(empId);
			if (object.isEmpty()) {
				throw new EmployeeNotFoundException("Invalid Employee Id");
			}
			employeeRepository.deleteById(empId);
			return "Employee deleted successfully";
		}catch(NumberFormatException e) {
			return "Invalid employee ID. Please provide a valid integer value.";
		}
		
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		return employeeRepository.findAll().stream().map(e -> mapEmployeeToDto(e)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto getEmployeeById(String employeeId) throws EmployeeNotFoundException {
		
		try {
			int empId= Integer.parseInt(employeeId);
			Optional<Employee> object = employeeRepository.findById(empId);
			if (object.isEmpty()) {
				throw new EmployeeNotFoundException("Invalid Employee Id");
			}

			return mapEmployeeToDto(object.get());
		}catch(NumberFormatException e) {
			throw new EmployeeNotFoundException("Invalid employee ID. Please provide a valid integer value.");
		}
		
	}

	@Override
	public List<EmployeeDto> getEmployeeByName(String employeeName) throws EmployeeNotFoundException {

		List<EmployeeDto> elist=employeeRepository.findByEmployeeName(employeeName).stream().map(e -> mapEmployeeToDto(e))
				.collect(Collectors.toList());
		if(elist.size()==0) {
			throw new EmployeeNotFoundException("No employee found for "+employeeName);
		}
		return elist;
	}

	@Override
	public DepartmentDto getDepartmentOfEmployee(String employeeId) throws EmployeeNotFoundException {
		try {
			
			int empId=Integer.parseInt(employeeId);
			Optional<Employee> object = employeeRepository.findById(empId);
			if (object.isEmpty()) {
				throw new EmployeeNotFoundException("Invalid Employee Id");
			}
			DepartmentDto department = new DepartmentDto();
			department.setDepartmentId(object.get().getDepartment().getDepartmentId());
			department.setDepartmentName(object.get().getDepartment().getDepartmentName());

			return department;
			
		}catch(NumberFormatException e) {
			throw new EmployeeNotFoundException("Invalid employee ID. Please provide a valid integer value.");
		}
		
	}

	@Override
	public List<EmployeeDto> getEmployeeByDesignation(String designation) {
		// TODO Auto-generated method stub
		return employeeRepository.findByDesignation(designation).stream().map(e -> mapEmployeeToDto(e))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDto getEmployeeByEmail(String email) {

		EmployeeDto employee = employeeRepository.findAll().stream().filter(e -> e.getEmail().equals(email))
				.map(e -> mapEmployeeToDto(e)).findFirst().get();

		return employee;
	}
	

	@Override
	public List<EmployeeDto> getBySalaryMoreThan(String salary) throws EmployeeNotFoundException {
		
		try {
			double sal= Double.parseDouble(salary);
			return employeeRepository.findBySalaryGreaterThan(sal).stream().map(e->mapEmployeeToDto(e)).toList();
		}catch(NumberFormatException e) {
			throw new EmployeeNotFoundException("Invalid input.Please provide salary in numbers.");
		}

	}

	  public EmployeeDto mapEmployeeToDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmployeeId(employee.getEmployeeId());
		employeeDto.setEmployeeName(employee.getEmployeeName());
		employeeDto.setDesignation(employee.getDesignation());
		employeeDto.setAge(employee.getAge());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setSalary(employee.getSalary());

		return employeeDto;

	}
	  private boolean validateEmployee(Employee employee) throws EmployeeNotFoundException, DepartmentNotFoundException {
		  if(employee.getEmployeeName().isEmpty()) {
				throw new EmployeeNotFoundException("Employee name should not be empty.");
			}
			if(!employee.getEmployeeName().matches(regexp)) {
				throw new EmployeeNotFoundException("Employee name must contain letters and whitespaces only.");
			}
			if(employee.getEmail().isEmpty()) {
				throw new EmployeeNotFoundException("Employee email shoulf not be empty.");
			}
			if(!employee.getEmail().matches(emailRegex)) {
				throw new EmployeeNotFoundException("Enter a valid email.");
			}
			if(employee.getAge()<18 || employee.getAge()>150) {
				throw new EmployeeNotFoundException("Enter a valid age(18-150).");
			}
			int deptId=employee.getDepartment().getDepartmentId();
			Optional<Department> department=departmentRepository.findById(deptId);
			if(department.isEmpty()) {
				throw new DepartmentNotFoundException("Department with "+deptId+" doesn't exist");
			}
			return true;
	  }


}
