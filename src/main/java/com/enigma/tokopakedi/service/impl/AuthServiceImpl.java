package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.constant.Erole;
import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Role;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;
import com.enigma.tokopakedi.repository.UserCredentialRepository;
import com.enigma.tokopakedi.security.JwtUtils;
import com.enigma.tokopakedi.service.AuthService;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.RoleService;
import com.enigma.tokopakedi.utils.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final ValidationUtils validationUtils;

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdmin(){
        Optional<UserCredential> optionalUserCredential = userCredentialRepository
                .findByEmail("superadmin@gmail.com");

        if (optionalUserCredential.isPresent())return;

        Role roleSuperAdmin = roleService.getOrSave(Erole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(Erole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(Erole.ROLE_CUSTOMER);

        //password pake enviroment
        String hashPassword = passwordEncoder.encode("password");

        UserCredential userCredential = UserCredential.builder()
                .email("superadmin@gmail.com")
                .password(hashPassword)
                .roles(List.of(roleCustomer, roleAdmin, roleSuperAdmin))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(AuthRequest request) {
        validationUtils.validate(request);
        Role roleCustomer = roleService.getOrSave(Erole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .email(request.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        Customer customer = Customer.builder()
                .userCredential(userCredential)
                .build();
        customerService.create(customer);

        return toUserResponse(userCredential);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse registerAdmin(AuthRequest request) {
        validationUtils.validate(request);
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
    @Transactional(rollbackFor = Exception.class)
    public String login(AuthRequest request) {
        validationUtils.validate(request);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserCredential userCredential = (UserCredential) authenticate.getPrincipal();

        return jwtUtils.generateToken(userCredential);
    }

    private UserResponse toUserResponse(UserCredential userCredential) {
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();
    }
}
