package com.lucas.employeeManagement.controllers;

import com.lucas.employeeManagement.dtos.CreateUserDto;
import com.lucas.employeeManagement.entities.Role;
import com.lucas.employeeManagement.entities.User;
import com.lucas.employeeManagement.repositories.RoleRepository;
import com.lucas.employeeManagement.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Operation(summary = "Registro de usuário",
          description = "Esse método permite registar um novo usuário no sistema")
  @PostMapping("/api/users")
  @Transactional
  public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
    var roleUser = roleRepository.findByName(Role.Values.USER.name());
    var userRepo = userRepository.findByUsername(createUserDto.username());

    if (userRepo.isPresent()) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    var user = new User();
    user.setUsername(createUserDto.username());
    user.setEmail(createUserDto.email());
    user.setPassword(bCryptPasswordEncoder.encode(createUserDto.password()));
    user.setRoles(Set.of(roleUser));

    userRepository.save(user);

    return ResponseEntity.ok().build();
  }
}
