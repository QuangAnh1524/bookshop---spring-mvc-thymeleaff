package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findByEmail(email);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()).password(user.getPassword())
                .roles(user.getRole().getName())
                .build();
    }
}
