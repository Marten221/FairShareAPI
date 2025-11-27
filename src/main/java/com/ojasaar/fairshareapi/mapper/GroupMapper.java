package com.ojasaar.fairshareapi.mapper;

import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.dto.GroupDTO;
import com.ojasaar.fairshareapi.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final UserMapper userMapper;

    public GroupDTO toGroupDto(Group group) {
        if (group == null) {
            return null;
        }

        return new GroupDTO(
                group.getId(),
                group.getName(),
                userMapper.toDto(group.getOwner()),
                toUserDtoSet(group.getMembers())
        );
    }

    private Set<UserDTO> toUserDtoSet(Set<User> users) {
        if (users == null) {
            return Set.of();
        }
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toSet());
    }
}
