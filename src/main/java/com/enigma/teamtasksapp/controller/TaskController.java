package com.enigma.teamtasksapp.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.teamtasksapp.dto.task.TaskRequestDTO;
import com.enigma.teamtasksapp.dto.task.TaskResponseDTO;
import com.enigma.teamtasksapp.service.TaskCrudService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCrudService taskCrudService;
    
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UUID> create(@RequestBody TaskRequestDTO taskRequestDto) {
        return new ResponseEntity<>(taskCrudService.create(taskRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(
        value = "{uuid}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaskResponseDTO> read(@PathVariable UUID uuid) {
        return ResponseEntity.ok(taskCrudService.read(uuid));
    }

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TaskResponseDTO>> read(
        @RequestParam(required = false) Map<String, String> params
    ) {
        return ResponseEntity.ok(taskCrudService.read(params));
    }

    @PutMapping(
        value = "{uuid}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaskResponseDTO> update(@RequestBody TaskRequestDTO taskRequestDto, @PathVariable UUID uuid) {
        return ResponseEntity.ok(taskCrudService.update(taskRequestDto, uuid));
    }

    @DeleteMapping(
        value = "{uuid}"
    )
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        taskCrudService.delete(uuid);
        return ResponseEntity.ok(null);
    }

}
