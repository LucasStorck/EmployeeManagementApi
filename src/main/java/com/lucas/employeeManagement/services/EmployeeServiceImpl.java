package com.lucas.employeeManagement.services;

import com.lucas.employeeManagement.dtos.EmployeeDto;
import com.lucas.employeeManagement.entities.Employee;
import com.lucas.employeeManagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public Set<EmployeeDto> getAllEmployees() {
    Set<Employee> employees = new HashSet<>(employeeRepository.findAll());
    return employees.stream()
            .map(this::mapToDto)
            .collect(Collectors.toSet());
  }

  @Override
  public Optional<EmployeeDto> getEmployeeById(Long employeeId) {
    Optional<Employee> employee = employeeRepository.findById(employeeId);
    return employee.map(this::mapToDto);
  }

  @Override
  public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    Employee employee = mapToEntity(employeeDto);
    Employee savedEmployee = employeeRepository.save(employee);
    return mapToDto(savedEmployee);
  }

  @Override
  public Optional<EmployeeDto> updateEmployee(Long employeeId, EmployeeDto employeeDtoDetails) {
    Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);

    if (existingEmployee.isPresent()) {
      Employee employee = existingEmployee.get();
      employee.setName(employeeDtoDetails.name());
      employee.setJobTitle(employeeDtoDetails.jobTitle());
      employee.setJobType(employeeDtoDetails.jobType());
      employee.setTeam(employeeDtoDetails.team());

      Employee updatedEmployee = employeeRepository.save(employee);

      return Optional.of(mapToDto(updatedEmployee));
    }

    return Optional.empty();
  }

  @Override
  public boolean deleteEmployee(Long employeeId) {
    Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);

    if (existingEmployee.isPresent()) {
      employeeRepository.delete(existingEmployee.get());
      return true;
    }

    return false;
  }

  private EmployeeDto mapToDto(Employee employee) {
    return new EmployeeDto(
            employee.getName(),
            employee.getJobTitle(),
            employee.getJobType(),
            employee.getTeam()
    );
  }

  private Employee mapToEntity(EmployeeDto employeeDto) {
    return new Employee(
            employeeDto.name(),
            employeeDto.jobTitle(),
            employeeDto.jobType(),
            employeeDto.team()
    );
  }
}
