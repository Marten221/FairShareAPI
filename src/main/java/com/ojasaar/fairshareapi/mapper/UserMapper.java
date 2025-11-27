package com.ojasaar.fairshareapi.mapper;

import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getEmail());
    }
}
