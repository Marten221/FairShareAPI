package com.ojasaar.fairshareapi.service;

import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.dto.GroupDTO;
import com.ojasaar.fairshareapi.dto.UserDTO;
import com.ojasaar.fairshareapi.exception.NotFoundException;
import com.ojasaar.fairshareapi.repository.GroupRepo;
import com.ojasaar.fairshareapi.repository.UserRepo;
import com.ojasaar.fairshareapi.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepo groupRepo;
    private final UserRepo userRepo;

    public Group createGroup(Group group) {
        String userId = UserUtil.getUserIdfromContext();
        User owner = userRepo.getReferenceById(userId);

        group.setId(null);
        group.setOwner(owner);
        return groupRepo.save(group);
    }

    public List<GroupDTO> getGroups() {
        String userId = UserUtil.getUserIdfromContext();

        List<Group> groups = groupRepo.findAllByOwnerOrMember(userId);

        return groups.stream()
                .map(g -> new GroupDTO(
                        g.getId(),
                        g.getName(),
                        new UserDTO(g.getOwner().getId(), g.getOwner().getEmail()),
                        g.getMembers().stream()
                                .map(m -> new UserDTO(m.getId(), m.getEmail()))
                                .collect(Collectors.toSet())
                ))
                .toList();
    }

    @Transactional
    public void addMemberToGroup(String groupId) {
        String userId = UserUtil.getUserIdfromContext();

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        group.getMembers().add(user);
        user.getMemberGroups().add(group);

        groupRepo.save(group);
    }

    public GroupDTO getGroupById(String groupId) {
        String userId = UserUtil.getUserIdfromContext();

        if (!hasAccessToGroup(userId, groupId)) {
            throw new AuthorizationDeniedException("You don't have access to this group");
        }

        Group group = groupRepo.findGroupById(groupId); // todo: Make mappers
        return new GroupDTO(
                group.getId(),
                group.getName(),
                new UserDTO(group.getOwner().getId(), group.getOwner().getEmail()),
                group.getMembers().stream()
                        .map(m -> new UserDTO(m.getId(), m.getEmail()))
                        .collect(Collectors.toSet())
        );
    }

    private boolean hasAccessToGroup(String userId, String groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        return group.getOwner().getId().equals(userId) ||
                group.getMembers().stream()
                        .anyMatch(member -> member.getId().equals(userId));
    }
}
