package com.ojasaar.fairshareapi.dto;

public record DebtDTO(
        String fromUserId,
        String fromUserName,
        String toUserId,
        String toUserName,
        double amount
) {}
