package com.inspark.sabeel.user.application.controller;

import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.domain.port.input.UsersUseCases;
import com.inspark.sabeel.user.application.dto.UserDto;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.inspark.sabeel.common.AuthUtils.getCurrentAuthenticatedUser;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersUseCases usersUseCases;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDto> findCurrentUser() {
        User user = getCurrentAuthenticatedUser(userMapper);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.toUserDto(usersUseCases.findById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String criteria,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Pageable pageable;
        if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        }
        return ResponseEntity.ok(usersUseCases.findAllWithPaginationAndFiltering(criteria, pageable).map(userMapper::toUserDto));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id,@RequestBody String currentUserRole) {
        usersUseCases.deleteById(id, currentUserRole);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        User user = getCurrentAuthenticatedUser(userMapper);
        User updatedUser = usersUseCases.updateProfile(user.getId(), userDto);
        return ResponseEntity.ok(userMapper.toUserDto(updatedUser));
    }

    @PatchMapping("toggle/{id}")
    public ResponseEntity<Void> toggleEnableDisable(@PathVariable UUID id) {
        usersUseCases.toggleEnableDisable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
