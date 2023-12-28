package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constant.Erole;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.security.JwtUtils;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;

    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCredential = userCredentialRepository
                .findByEmail("superadmin@gmail.com");

        if (optionalUserCredential.isPresent())return;

        Role roleSuperAdmin = roleService.getOrSave(Erole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(Erole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(Erole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode("password");

        UserCredential userCredential = UserCredential.builder()
                .email("superadmin@gmail.com")
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin, roleSuperAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);
    }
    @Override
    public UserResponse register(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(Erole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(Erole.ROLE_ADMIN);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        return toUserResponse(userCredential);
    }

    @Override
    public UserResponse registerAdmin(AuthRequest request) {
        Role roleCustomer = roleService.getOrSave(Erole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(Erole.ROLE_ADMIN);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        return toUserResponse(userCredential);
    }

    @Override
    public String login(AuthRequest request) {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByEmail(request.getEmail());
        if (optionalUserCredential.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        return jwtUtils.generateToken(optionalUserCredential.get());
    }

    private UserResponse toUserResponse(UserCredential userCredential) {
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();
    }
}
