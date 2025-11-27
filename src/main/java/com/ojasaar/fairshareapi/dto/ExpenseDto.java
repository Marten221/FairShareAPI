package com.ojasaar.fairshareapi.dto;


import java.time.LocalDateTime;

public record ExpenseDto(
        String id,
        String description,
        double amount,
        LocalDateTime timestamp,
        UserDTO owner) {
}
