package com.example.exam.controller;

import com.example.exam.exception.UserAlreadyExistsException;
import com.example.exam.model.User;
import com.example.exam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    @Transactional
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " is already existing");
        }
        User userToBeSaved = User.builder()
                .name(user.getName())
                .username(user.getUsername())
                .surname(user.getSurname())
                .password(user.getPassword())
                .build();
        return ResponseEntity.ok(userRepository.save(userToBeSaved));
    }
}
