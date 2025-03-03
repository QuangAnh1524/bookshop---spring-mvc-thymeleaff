package com.quanh1524.bookshop.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {
    public static class UserInfo {
        private final String email;
        private final boolean isAdmin;
        private final boolean isLoggedIn;

        public UserInfo(String email, boolean isAdmin, boolean isLoggedIn) {
            this.email = email;
            this.isAdmin = isAdmin;
            this.isLoggedIn = isLoggedIn;
        }

        public String getEmail() {
            return email;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public boolean isLoggedIn() {
            return isLoggedIn;
        }
    }

    public UserInfo getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().toUpperCase().contains("ADMIN"));
            System.out.println("Email: " + email + ", isAdmin: " + isAdmin);
            return new UserInfo(email, isAdmin, true);
        }
        return new UserInfo(null, false, false);
    }
}
