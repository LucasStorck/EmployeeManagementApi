package com.lucas.employeeManagement.dtos;

public record LoginResponseDto(String accessToken, Long expiresIn) {
}
