package com.goggle.voco.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException;
}
