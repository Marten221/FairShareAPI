package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.dto.AddMemberRequest;
import com.ojasaar.fairshareapi.dto.GroupDTO;
import com.ojasaar.fairshareapi.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/group")
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @GetMapping("/groups")
    public List<GroupDTO> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping("/group/{groupId}/member")
    public ResponseEntity<Void> addMemberToGroup(
            @PathVariable String groupId,
            @RequestBody AddMemberRequest request) {
        groupService.addMemberToGroup(groupId, request.userId());
        return ResponseEntity.ok().build();
    }

}
