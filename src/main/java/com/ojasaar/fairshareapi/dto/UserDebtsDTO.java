package com.ojasaar.fairshareapi.dto;

import java.util.List;

public record UserDebtsDTO(
        List<DebtDTO> owe,   // I owe these people
        List<DebtDTO> owed   // These people owe me
) {}
