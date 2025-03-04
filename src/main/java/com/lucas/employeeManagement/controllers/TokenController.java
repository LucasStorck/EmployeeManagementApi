package com.lucas.employeeManagement.controllers;

import com.lucas.employeeManagement.dtos.LoginRequestDto;
import com.lucas.employeeManagement.dtos.LoginResponseDto;
import com.lucas.employeeManagement.entities.Role;
import com.lucas.employeeManagement.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class TokenController {

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Operation(summary = "User authentication",
          description = "This method allows you to authenticate a user on the system")
  @PostMapping("/api/login")
  public ResponseEntity<LoginResponseDto> loginResponseDto(@RequestBody LoginRequestDto loginRequestDto) {

    var user = userRepository.findByUsername(loginRequestDto.username());

    if (user.isEmpty() || user.get().isLoginCorrect(loginRequestDto, bCryptPasswordEncoder)) {
      throw new BadCredentialsException("USERNAME, EMAIL OR PASSWORD IS INCORRECT");
    }

    var now = Instant.now();
    var expiresIn = 300L;

    var scopes = user.get().getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining(" "));

    var claims = JwtClaimsSet.builder()
            .issuer("my-backend")
            .subject(user.get().getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();
    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return ResponseEntity.ok(new LoginResponseDto(jwtValue, expiresIn));
  }
}
