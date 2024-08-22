package com.inspark.sabeel.auth.application.controller;

import com.inspark.sabeel.auth.domain.model.AccessToken;
import com.inspark.sabeel.auth.domain.port.input.AuthUseCases;
import com.inspark.sabeel.auth.application.dto.request.ResetPasswordDto;
import com.inspark.sabeel.auth.application.dto.request.SignInDto;
import com.inspark.sabeel.auth.application.dto.request.SignUpDto;
import com.inspark.sabeel.auth.application.dto.response.SignUpResponseDto;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCases authUseCases;
    private final UserMapper userMapper;


    @PostMapping("/register")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpDto signupDto) throws MessagingException {
        User authUser = userMapper.toAuthUserFromDto(signupDto);
        authUser = authUseCases.signUp(authUser);
        return new ResponseEntity<>(
                userMapper.toSignUpResponseDto(authUser),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> signIn(@RequestBody SignInDto user) {
        return new ResponseEntity<>(authUseCases.signIn(user.email(), user.password()), HttpStatus.OK);
    }

    @PostMapping("/activate-account/{code}")
    public ResponseEntity<Void> activateAccount(@PathVariable String code) throws MessagingException {
        authUseCases.activateAccount(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send-activation-code")
    public ResponseEntity<Void> sendActivationCode(@RequestParam String email) throws MessagingException {
        authUseCases.sendActivationCode(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/send-reset-password-code")
    public ResponseEntity<Void> sendResetPasswordCode(@RequestParam String email) throws MessagingException {
        authUseCases.sendResetPasswordCode(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        authUseCases.resetPassword(
                resetPasswordDto.code(),
                resetPasswordDto.password(),
                resetPasswordDto.confirmPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        //  Get the current user authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());
        authUseCases.changePassword(id, oldPassword, newPassword, confirmPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
