package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadByUserId(String userId);
    UserDetails loadUserByUsername(String email);
}
