package com.example.exam.controller;

import com.example.exam.exception.UserNotFoundException;
import com.example.exam.model.User;
import com.example.exam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UsersController {
    private final UserRepository userRepository;
    @Transactional
    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "userId") UUID userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id " + userId + " was not found"));
        return ResponseEntity.ok(user);
    }

    @Transactional
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        UUID userId = user.getUserId();
        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id " + userId + " was not found"));

        if (!user.equals(existingUser)) {
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
        }
        return ResponseEntity.ok(existingUser);
    }
    @Transactional
    @PutMapping("/password")
    public ResponseEntity<User> changePassword(@RequestBody User user) {
        UUID userId = user.getUserId();
        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id " + userId + " was not found"));

        if (!user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(user.getPassword());
        }
        return ResponseEntity.ok(user);
    }
    @Transactional
    @DeleteMapping("/profile/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable(name = "userId") UUID userId) {
        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id " + userId + " was not found"));
        userRepository.delete(existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.NO_CONTENT);
    }
}
