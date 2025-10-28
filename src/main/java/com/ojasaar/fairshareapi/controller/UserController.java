package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/inf")
    public String inf() {
        return "inf";
    }

}
