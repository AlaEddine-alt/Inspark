package com.inspark.sabeel.user.web.controller;

import com.inspark.sabeel.user.domain.port.input.UsersUseCases;
import com.inspark.sabeel.user.infrastructure.dto.UserDto;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersUseCases usersUseCases;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDto> findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(userMapper.toUserDto(usersUseCases.findById(userId)));
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


}
