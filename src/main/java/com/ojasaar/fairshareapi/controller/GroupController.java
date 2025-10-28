package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/group")
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @GetMapping("/groups")
    public Set<Group> getGroups() {
        return groupService.getGroups();
    }
}
