package com.cg.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.DepartmentDto;
import com.cg.dto.EmployeeDto;
import com.cg.entity.Department;
import com.cg.exception.DepartmentNotFoundException;
import com.cg.repository.DepartmentRepository;
import com.cg.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	String regexp = "^[a-zA-Z]+[a-zA-Z\\s]*$";

	@Override
	public DepartmentDto addDepartment(Department department) throws DepartmentNotFoundException {
		if (department.getDepartmentName().isEmpty()) {
			throw new DepartmentNotFoundException("Department name should not be empty");
		}

		if (!department.getDepartmentName().matches(regexp)) {
			throw new DepartmentNotFoundException("Department name must contain letters and whitespaces only");
		}
		departmentRepository.save(department);
		return mapDepartmentToDto(department);
	}

	@Override
	public String updateDepartment(String departmentId, Department department) throws DepartmentNotFoundException {
		try {
			int deptId = Integer.parseInt(departmentId);
			Optional<Department> object = departmentRepository.findById(deptId);
			if (object.isEmpty()) {
				throw new DepartmentNotFoundException("Invalid department ID");
			}
			if (department.getDepartmentName().isEmpty()) {
				throw new DepartmentNotFoundException("Department name should not be empty");
			} else if (!department.getDepartmentName().matches(regexp)) {
				throw new DepartmentNotFoundException("Department name must contain letters and whitespaces only");
			} else {
				object.get().setDepartmentName(department.getDepartmentName());
			}
			object.get().setEmployeeList(department.getEmployeeList());
			departmentRepository.save(object.get());

			return object.get().getDepartmentName() + " Department updated successfully";

		} catch (NumberFormatException e) {
			return "Invalid department ID. Please provide a valid integer value.";
		}
	}

	@Override
	public String deleteDepartment(String departmentId) throws DepartmentNotFoundException {
		try {
			int deptId = Integer.parseInt(departmentId);
			Optional<Department> object = departmentRepository.findById(deptId);

			if (object.isEmpty()) {
				throw new DepartmentNotFoundException("Invalid department ID");
			}
			String dName = object.get().getDepartmentName();
			departmentRepository.delete(object.get());

			return dName + " Department deleted";

		} catch (NumberFormatException e) {
			return "Invalid department ID. Please provide a valid integer value.";
		}
	}

	@Override
	public List<DepartmentDto> getAllDepartment() {
		return departmentRepository.findAll().stream().map(e -> mapDepartmentToDto(e)).collect(Collectors.toList());
	}

	@Override
	public DepartmentDto getDepartmentById(String departmentId) throws DepartmentNotFoundException {

		try {
			int deptId = Integer.parseInt(departmentId);
			Optional<Department> object = departmentRepository.findById(deptId);
			if (object.isEmpty()) {
				throw new DepartmentNotFoundException("Invalid department ID");
			}
			return mapDepartmentToDto(object.get());
		} catch (NumberFormatException e) {

			throw new DepartmentNotFoundException("Invalid department ID. Please provide a valid integer value.");
		}

	}

	@Override
	public DepartmentDto getDepartmentByName(String departmentName) throws DepartmentNotFoundException {
		Optional<DepartmentDto> object = departmentRepository.findAll().stream()
				.filter(e -> e.getDepartmentName().toLowerCase().equals(departmentName.toLowerCase())).findAny()
				.map(e -> mapDepartmentToDto(e));
		if (object.isEmpty()) {
			throw new DepartmentNotFoundException("Invalid Department name");
		}

		return object.get();
	}

	@Override
	public List<EmployeeDto> getEmployeesByDepartment(String departmentId) throws DepartmentNotFoundException {

		try {
			int deptId = Integer.parseInt(departmentId);
			Optional<Department> object = departmentRepository.findById(deptId);

			if (object.isEmpty()) {
				throw new DepartmentNotFoundException("Invalid department ID");
			}
			return object.get().getEmployeeList().stream().map(e -> employeeServiceImpl.mapEmployeeToDto(e))
					.collect(Collectors.toList());
		} catch (NumberFormatException e) {
			throw new DepartmentNotFoundException("Invalid department ID. Please provide a valid integer value.");
		}
	}
	@Override
	public List<DepartmentDto> getDepartmentWithMaxEmployee() {
		List<DepartmentDto> list = departmentRepository.findWithMaxEmployees().stream().map(e -> mapDepartmentToDto(e))
				.toList();
		list.forEach(e->e.setNumberOfEmployees(departmentRepository.findById(e.getDepartmentId()).get().getEmployeeList().size()));
		return list;
	}

	private DepartmentDto mapDepartmentToDto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentId(department.getDepartmentId());
		departmentDto.setDepartmentName(department.getDepartmentName());
		return departmentDto;

	}

}
