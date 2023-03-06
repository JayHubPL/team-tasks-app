package com.enigma.teamtasksapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.teamtasksapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByUuid(UUID uuid);

    public Optional<User> findByEmail(String email);

}
