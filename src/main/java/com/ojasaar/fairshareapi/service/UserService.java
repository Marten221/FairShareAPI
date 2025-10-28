package com.ojasaar.fairshareapi.service;

import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.exception.InvalidCredentialsException;
import com.ojasaar.fairshareapi.repository.UserRepo;
import com.ojasaar.fairshareapi.util.JwtUtil;
import com.ojasaar.fairshareapi.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<String> register(User user) {
        validateRegistration(user);

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        user = userRepo.save(user);

        ResponseCookie cookie = jwtUtil.generateCookie(user.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("User registered successfully");
    }

    public void validateRegistration(User user) {
        if (!UserUtil.validateEmail(user.getEmail())) {
            throw new InvalidCredentialsException("Incorrect email");
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new InvalidCredentialsException("This email is already in use");
        }
    }

    public ResponseEntity<String> loginUser(User userCredentials) {
        String email = userCredentials.getEmail().toLowerCase();
        String rawPassword = userCredentials.getPassword();
        User user = userRepo.findByEmail(email);
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        ResponseCookie cookie = jwtUtil.generateCookie(user.getId());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("User logged in successfully");
    }
}
