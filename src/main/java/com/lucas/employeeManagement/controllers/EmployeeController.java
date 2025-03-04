package com.lucas.employeeManagement.controllers;

import com.lucas.employeeManagement.dtos.EmployeeDto;
import com.lucas.employeeManagement.services.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
@EnableMethodSecurity
public class EmployeeController {

  @Autowired
  private EmployeeServiceImpl employeeService;

  @Operation(summary = "Gets all employees",
          description = "This method returns a list of all employees registered in the system. Users with 'SUPERUSER' or 'USER' authority can access this feature.")
  @GetMapping
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER') or hasAuthority('SCOPE_USER')")
  public Set<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @Operation(summary = "Get employees by ID",
          description = "This method returns employees registered in the system by their ID. Users with 'SUPERUSER' or 'USER' authority can access this resource.")
  @GetMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER') or hasAuthority('SCOPE_USER')")
  public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
    return employeeService.getEmployeeById(employeeId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Create a new employee",
          description = "This method allows the creation of a new employee in the system. Only users with the 'SUPERUSER' authority can perform this action.")
  @PostMapping
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody @Validated EmployeeDto employeeDto) {
    EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Employee Created Successfully");
    response.put("employee", createdEmployee);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Update employee information",
          description = "This method allows you to update employee information registered in the system using their ID. Only users with the 'SUPERUSER' authority can perform this action.")
  @PutMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<EmployeeDto> updateEmployee(
          @PathVariable Long employeeId,
          @RequestBody @Validated EmployeeDto employeeDto) {

    return employeeService.updateEmployee(employeeId, employeeDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete an employee by their ID",
          description = "This method deletes an employee registered in the system by their ID. Only users with the 'SUPERUSER' authority can perform this action.")
  @DeleteMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {

    return employeeService.deleteEmployee(employeeId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
  }
}
