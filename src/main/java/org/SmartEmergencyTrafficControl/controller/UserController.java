package org.SmartEmergencyTrafficControl.controller;

import org.SmartEmergencyTrafficControl.model.VehicleUser;
import org.SmartEmergencyTrafficControl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody VehicleUser user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<VehicleUser> loginUser(@RequestBody VehicleUser loginDetails) {
        Optional<VehicleUser> user = userRepository.findByUsername(loginDetails.getUsername());
        
        if (user.isPresent() && user.get().getPassword().equals(loginDetails.getPassword())) {
            // Login successful
            return ResponseEntity.ok(user.get());
        } else {
            // Login failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}