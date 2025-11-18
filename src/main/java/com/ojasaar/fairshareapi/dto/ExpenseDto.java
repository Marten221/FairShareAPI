package com.ojasaar.fairshareapi.dto;


public record ExpenseDto(
        String id,
        String description,
        double amount,
        UserDTO owner) {
}
