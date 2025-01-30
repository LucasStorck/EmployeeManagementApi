package com.lucas.employeeManagement.dtos;

import jakarta.annotation.Nonnull;

public record EmployeeDto(
        @Nonnull String name,
        @Nonnull String jobTitle,
        @Nonnull String jobType,
        @Nonnull String team) {
}
