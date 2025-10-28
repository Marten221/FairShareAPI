package com.ojasaar.fairshareapi.repository;

import com.ojasaar.fairshareapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, String> {
}
