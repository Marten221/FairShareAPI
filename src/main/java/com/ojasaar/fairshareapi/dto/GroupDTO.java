package com.ojasaar.fairshareapi.dto;

import java.util.Set;

public record GroupDTO(
        String id,
        String name,
        UserDTO owner,
        Set<UserDTO> members) {
}
