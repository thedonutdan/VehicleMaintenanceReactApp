package io.thedonutdan.vehiclemaintenance.controller;

import io.thedonutdan.vehiclemaintenance.DAO.UserDAO;
import io.thedonutdan.vehiclemaintenance.DTO.RegisterRequest;
import io.thedonutdan.vehiclemaintenance.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

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

}
