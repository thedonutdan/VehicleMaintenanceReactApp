package io.thedonutdan.vehiclemaintenance.controller;

import io.thedonutdan.vehiclemaintenance.DAO.UserDAO;
import io.thedonutdan.vehiclemaintenance.DTO.LoginRequest;
import io.thedonutdan.vehiclemaintenance.DTO.RegisterRequest;
import io.thedonutdan.vehiclemaintenance.model.User;
import io.thedonutdan.vehiclemaintenance.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

/**
 * Controller for authorization. Handles user registration and logon
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserDAO userDAO, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user with given username and password
     * @param req the registration request containing username and password
     * @return HTTP 200 if successful, 409 if username exists
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        if (userDAO.findByUsername(req.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is not available.");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername(req.getUsername());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        userDAO.insert(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        // Check login info
        User user = userDAO.findByUsername(req.getUsername());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect username or password");
        }

        // Set expiry and JWT token
        Duration expiry = req.getRememberMe() ? Duration.ofDays(30) : Duration.ofDays(1);
        String jwt = jwtUtil.generateToken(user.getUserId(), expiry);
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(expiry)
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteJWTCookie = ResponseCookie.from("jwt", "")
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(0)
            .sameSite("Strict")
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, deleteJWTCookie.toString())
            .body("Logged out");
    }
}
