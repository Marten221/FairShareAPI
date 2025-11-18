package com.ojasaar.fairshareapi.dto;

public record UserBalanceDTO(
        String userId,
        String userName,
        double totalOwes,
        double totalIsOwed,
        double net // totalIsOwed - totalOwes
) {}

