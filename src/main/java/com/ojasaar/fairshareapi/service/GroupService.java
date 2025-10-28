package com.ojasaar.fairshareapi.service;

import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.repository.GroupRepo;
import com.ojasaar.fairshareapi.repository.UserRepo;
import com.ojasaar.fairshareapi.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public Set<Group> getGroups() {
        String userId = UserUtil.getUserIdfromContext();

        return groupRepo.findAllByOwner_Id(userId);
    }
}
