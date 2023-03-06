package com.enigma.teamtasksapp.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.teamtasksapp.dto.task.TaskResponseDTO;
import com.enigma.teamtasksapp.service.TaskManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/manage")
@RequiredArgsConstructor
public class ManagerController {
    
    private final TaskManagerService taskManagerService;

    @PutMapping(
        value = "task/{taskUuid}/advance",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaskResponseDTO> advance(@PathVariable UUID taskUuid) {
        return ResponseEntity.ok(taskManagerService.advance(taskUuid));
    }

    @PutMapping(
        value = "user/{userUuid}/assignTo/{taskUuid}"
    )
    public ResponseEntity<?> assignTo(@PathVariable UUID userUuid, @PathVariable UUID taskUuid) {
        taskManagerService.assignUserToTask(userUuid, taskUuid);
        return ResponseEntity.ok(null);
    }

    @PutMapping(
        value = "user/{userUuid}/removeFrom/{taskUuid}"
    )
    public ResponseEntity<?> removeFrom(@PathVariable UUID userUuid, @PathVariable UUID taskUuid) {
        taskManagerService.removeUserFromTask(userUuid, taskUuid);
        return ResponseEntity.ok(null);
    }

}
