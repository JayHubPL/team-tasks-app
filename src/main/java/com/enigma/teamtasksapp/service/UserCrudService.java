package com.enigma.teamtasksapp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.enigma.teamtasksapp.dto.user.UserRequestDTO;
import com.enigma.teamtasksapp.dto.user.UserResponseDTO;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterKeyException;
import com.enigma.teamtasksapp.exception.user.EmailTakenException;
import com.enigma.teamtasksapp.exception.user.InvalidEmailFormatException;
import com.enigma.teamtasksapp.exception.user.UserNotFoundException;
import com.enigma.teamtasksapp.mapper.UserMapper;
import com.enigma.teamtasksapp.model.User;
import com.enigma.teamtasksapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final Pattern emailRegex = Pattern.compile(
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    );
    
    public UUID create(UserRequestDTO requestDto) {
        validateRequestDto(requestDto);
        Optional<User> emailOpt = userRepository.findByEmail(requestDto.getEmail());
        if (emailOpt.isPresent()) {
            throw new EmailTakenException(requestDto.getEmail());
        }
        UUID uuid = UUID.randomUUID();
        User entity = userMapper.requestDtoToEntity(requestDto);
        entity.setUuid(uuid);
        entity = userRepository.save(entity);
        return entity.getUuid();
    }

    public UserResponseDTO read(UUID uuid) {
        User entity = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new UserNotFoundException(uuid));
        return userMapper.entityToResponseDto(entity);
    }

    public List<UserResponseDTO> read(Map<String, String> params) {
        return userRepository.findAll().stream()
            .filter(paramsToPredicate(params))
            .map(userMapper::entityToResponseDto)
            .toList();
    }

    public UserResponseDTO update(UserRequestDTO requestDto, UUID uuid) {
        validateRequestDto(requestDto);
        User entity = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new UserNotFoundException(uuid));
        Optional<User> emailOpt = userRepository.findByEmail(requestDto.getEmail());
        if (emailOpt.isPresent() && !emailOpt.get().getUuid().equals(uuid)) {
            throw new EmailTakenException(requestDto.getEmail());
        }
        entity.setName(requestDto.getName());
        entity.setSurname(requestDto.getSurname());
        entity.setEmail(requestDto.getEmail());
        entity = userRepository.save(entity);
        return userMapper.entityToResponseDto(entity);
    }

    public void delete(UUID uuid) {
        User entity = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new UserNotFoundException(uuid));
        userRepository.delete(entity);
    }

    private void validateRequestDto(UserRequestDTO requestDto) {
        validateEmail(requestDto.getEmail());
    }

    private void validateEmail(String email) {
        if (!emailRegex.matcher(email).matches()) {
            throw new InvalidEmailFormatException(email);
        }
    }

    private Predicate<User> paramsToPredicate(Map<String, String> params) {
        return params.entrySet().stream()
            .<Predicate<User>>map(entry -> {
                return switch (entry.getKey().toLowerCase()) {
                    case "name" -> usr -> usr.getName().equals(entry.getValue());
                    case "surname" -> usr -> usr.getSurname().equals(entry.getValue());
                    case "email" -> usr -> usr.getEmail().equals(entry.getValue());
                    default -> throw new InvalidRequestParameterKeyException(entry.getKey());
                };
            })
            .reduce(x -> true, Predicate::and);
    }

}