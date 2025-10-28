package com.ojasaar.fairshareapi.repository;

import com.ojasaar.fairshareapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepo extends JpaRepository<Group, String> {
    Set<Group> findAllByOwner_Id(String ownerId);
}
