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

import com.enigma.teamtasksapp.dto.user.UserRequestDTO;
import com.enigma.teamtasksapp.dto.user.UserResponseDTO;
import com.enigma.teamtasksapp.service.UserCrudService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCrudService userCrudService;
    
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UUID> create(@RequestBody UserRequestDTO userRequestDto) {
        return new ResponseEntity<>(userCrudService.create(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(
        value = "{uuid}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDTO> read(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userCrudService.read(uuid));
    }

    @GetMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserResponseDTO>> read(
        @RequestParam(required = false) Map<String, String> params
    ) {
        return ResponseEntity.ok(userCrudService.read(params));
    }

    @PutMapping(
        value = "{uuid}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO userRequestDto,
        @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(userCrudService.update(userRequestDto, uuid));
    }

    @DeleteMapping(
        value = "{uuid}"
    )
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        userCrudService.delete(uuid);
        return ResponseEntity.ok(null);
    }

}
