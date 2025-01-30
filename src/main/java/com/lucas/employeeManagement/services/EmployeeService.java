package com.lucas.employeeManagement.services;

import com.lucas.employeeManagement.dtos.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface EmployeeService {

  Set<EmployeeDto> getAllEmployees();

  Optional<EmployeeDto> getEmployeeById(Long employeeId);

  EmployeeDto createEmployee(EmployeeDto employeeDto);

  Optional<EmployeeDto> updateEmployee(Long employeeId, EmployeeDto employeeDtoDetails);

  boolean deleteEmployee(Long employeeId);

}
