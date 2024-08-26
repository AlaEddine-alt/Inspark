package com.inspark.sabeel.auth.infrastructure.adapter.persistence;

import com.inspark.sabeel.auth.domain.enums.CodeStatus;
import com.inspark.sabeel.auth.domain.enums.EmailType;
import com.inspark.sabeel.auth.domain.enums.RoleType;
import com.inspark.sabeel.auth.domain.model.AccessToken;
import com.inspark.sabeel.auth.domain.model.Code;
import com.inspark.sabeel.auth.domain.port.output.Auth;
import com.inspark.sabeel.auth.domain.port.output.Emails;
import com.inspark.sabeel.auth.domain.utils.Generate6digitsCode;
import com.inspark.sabeel.auth.infrastructure.entity.CodeEntity;
import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import com.inspark.sabeel.auth.infrastructure.mapper.CodeMapper;
import com.inspark.sabeel.auth.infrastructure.mapper.RoleMapper;
import com.inspark.sabeel.auth.infrastructure.repository.CodeRepository;
import com.inspark.sabeel.auth.infrastructure.repository.RolesRepository;
import com.inspark.sabeel.auth.infrastructure.utils.TokenUtils;
import com.inspark.sabeel.security.JwtService;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import com.inspark.sabeel.user.infrastructure.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthJpaAdapter implements Auth {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final RoleMapper roleMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CodeRepository codeRepository;
    private final CodeRepository codes;
    private final Emails emails;
    private final CodeMapper codeMapper;
    private final TokenUtils tokenUtils;

    @Override
    public User signUp(User authUser) {
//        Encrypt password
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
//        Get Role from database
        RoleEntity roleEntity = rolesRepository.findByName(RoleType.USER.name());
        var role = roleMapper.toRole(roleEntity);
//        Set role to the current user
        authUser.setRoles(Set.of(role));
        UserEntity userEntity = userMapper.toUserEntity(authUser);
        return userMapper.toUser(userRepository.save(userEntity));
    }

    @Override
    public AccessToken signIn(String email, String password) {
        log.info("Signing in user: {}", email);
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var claims = new HashMap<String, Object>();
        var user = (UserEntity) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());
        var token = jwtService.generateToken(claims, user);
        var refreshToken = jwtService.generateRefreshToken(claims, user);

        tokenUtils.revokeAllUserTokens(user.getId());
        tokenUtils.saveUserToken(user.getId(), token);
        log.info("User signed in successfully: {}", email);
        return AccessToken.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void resetPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(userMapper.toUserEntity(user));
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(userMapper.toUserEntity(user));
    }

    @Override
    public void activateAccount(Code code, User user) throws MessagingException {
        var userEntity = userMapper.toUserEntity(user);
        var codeEntity = codeMapper.toCodeEntity(code);
        userEntity.setEnabled(true);
        userEntity.setEmailVerified(true);
        userRepository.save(userEntity);
        codeEntity.setStatus(CodeStatus.USED);
        codeRepository.save(codeEntity);
    }

    @Override
    public void sendActivationCode(User user) throws MessagingException {
        //        Generate a 6 digits code and save it to the database
        var code = Generate6digitsCode.generate();
        CodeEntity codeToSave = CodeEntity.builder()
                .code(code)
                .status(CodeStatus.PENDING)
                .userId(user.getId())
                .build();
        codes.save(codeToSave);
//        Send the code to the user
        emails.sendEmail(
                user.getEmail(),
                EmailType.ACCOUNT_CONFIRMATION,
                user.getFullName(),
                code
        );
    }
}
