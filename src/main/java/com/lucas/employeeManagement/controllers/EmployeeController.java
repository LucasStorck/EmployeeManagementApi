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

import java.util.Set;

@RestController
@RequestMapping("/api/employees")
@EnableMethodSecurity
public class EmployeeController {

  @Autowired
  private EmployeeServiceImpl employeeService;

  @Operation(summary = "Obtém todos os funcionários",
          description = "Este método retorna uma lista de todos os funcionários cadastrados no sistema. Usuários com autoridade 'SUPERUSER' ou 'USER' podem acessar esse recurso.")
  @GetMapping
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER') or hasAuthority('SCOPE_USER')")
  public Set<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @Operation(summary = "Obtém os funcionários pelo Id",
          description = "Este método retorna funcionários cadastrados no sistema pelo seu Id. Usuários com autoridade 'SUPERUSER' ou 'USER' podem acessar esse recurso.")
  @GetMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER') or hasAuthority('SCOPE_USER')")
  public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
    return employeeService.getEmployeeById(employeeId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Cria um novo funcionário",
          description = "Este método permite a criação de um novo funcionário no sistema. Somente usuários com a autoridade 'SUPERUSER' podem executar essa ação.")
  @PostMapping
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Validated EmployeeDto employeeDto) {
    EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
  }

  @Operation(summary = "Atualizar as informações do funcionário",
          description = "Esse método permite atualizar as informações do funcionário cadastrado no sistema pelo seu Id. Somente usuários com a autoridade 'SUPERUSER' podem executar essa ação.")
  @PutMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<EmployeeDto> updateEmployee(
          @PathVariable Long employeeId,
          @RequestBody @Validated EmployeeDto employeeDto) {

    return employeeService.updateEmployee(employeeId, employeeDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Detetar um funcionário pelo seu Id",
          description = "Este método deleta um funcionário cadastrado no sistema pelo seu Id. Somente usuários com a autoridade 'SUPERUSER' podem executar essa ação.")
  @DeleteMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('SCOPE_SUPERUSER')")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
    return employeeService.deleteEmployee(employeeId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
  }
}
