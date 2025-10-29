package com.ojasaar.fairshareapi.repository;

import com.ojasaar.fairshareapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, String> {
    @Query("""
    SELECT DISTINCT g 
    FROM Group g 
    LEFT JOIN g.members m 
    WHERE g.owner.id = :userId 
       OR m.id = :userId
""")
    List<Group> findAllByOwnerOrMember(@Param("userId") String userId);

    Group findGroupById(String id);
}
