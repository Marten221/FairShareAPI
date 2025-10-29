package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.domain.model.Group;
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
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.createGroup(group));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupDTO>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable String groupId) {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @PostMapping("/group/{groupId}")
    public ResponseEntity<Void> joinGroup(@PathVariable String groupId) {
        groupService.addMemberToGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
