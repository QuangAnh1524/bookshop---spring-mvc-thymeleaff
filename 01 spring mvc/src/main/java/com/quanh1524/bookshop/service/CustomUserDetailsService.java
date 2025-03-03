package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> user = userRepository.findByEmail(email);
        User user1 = user.stream().findFirst().orElseThrow(() -> new UsernameNotFoundException("User name not found with email"));
        System.out.println("User: " + email + ", Role: " + user1.getRole().getName()); // Debug
        return org.springframework.security.core.userdetails.User
                .withUsername(user1.getEmail()).password(user1.getPassword())
                .roles(user1.getRole().getName())
                .build();
    }
}
