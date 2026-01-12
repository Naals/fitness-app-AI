package com.project.fitnessapp.repository;

import com.project.fitnessapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByKeycloakId(String userId);

    Optional<User> findByEmail(String email);
}
