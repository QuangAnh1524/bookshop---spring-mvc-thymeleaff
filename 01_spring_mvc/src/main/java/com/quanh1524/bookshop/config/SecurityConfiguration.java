package com.quanh1524.bookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //cau hinh quyen truy cap
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register","/logout", "/css/**", "/js/**", "/images/**", "/clientLib/**").permitAll()
                .requestMatchers("/admin/**").hasRole("Admin")
                .anyRequest().authenticated()
        );
        //cau hinh form login
        httpSecurity.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll());
        //cau hinh log out
        httpSecurity.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll());
        //remember me
        httpSecurity.rememberMe(rememberMe -> rememberMe
                .key("quang-anh-1524-key")
                .tokenValiditySeconds(7*24*60*60)
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService)
                .authenticationSuccessHandler((request, response, authentication) -> {
                    System.out.println("Remember Me authentication successful for: " + authentication.getName());
                    response.sendRedirect("/");
                }));

        httpSecurity.userDetailsService(userDetailsService);
        return httpSecurity.build();
    }
}

