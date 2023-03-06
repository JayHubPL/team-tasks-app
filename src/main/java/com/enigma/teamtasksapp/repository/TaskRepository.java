package com.enigma.teamtasksapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.teamtasksapp.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    public Optional<Task> findByUuid(UUID uuid);

}
