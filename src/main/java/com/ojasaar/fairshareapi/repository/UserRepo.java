package com.ojasaar.fairshareapi.repository;

import com.ojasaar.fairshareapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    User findByEmail(String email);
}
