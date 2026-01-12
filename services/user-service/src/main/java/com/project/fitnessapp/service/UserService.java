package com.project.fitnessapp.service;

import com.project.fitnessapp.dto.RegisterRequest;
import com.project.fitnessapp.dto.UserResponse;
import com.project.fitnessapp.model.User;
import com.project.fitnessapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse register(RegisterRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseGet(() -> createAndSaveUser(request));

        return mapToResponse(user);
    }

    public UserResponse getUserProfile(String userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    public boolean existsByUserId(String userId) {
        log.info("Validating user existence for keycloakId: {}", userId);
        return repository.existsByKeycloakId(userId);
    }

    /* ----------------- Helper methods ----------------- */

    private User createAndSaveUser(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setKeycloakId(request.getKeycloakId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return repository.save(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setKeycloakId(user.getKeycloakId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
