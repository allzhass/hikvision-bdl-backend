package kz.bdl.config;

import kz.bdl.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Static resources
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // User and role management - ADMIN only
                .requestMatchers("/user-view/**", "/users/**", "/role-view/**", "/roles/**").hasRole("ADMIN")
                
                // SentViolationViewController - OPERATOR, MANAGER, ADMIN
                .requestMatchers("/sent-violations-view/**", "/sent-violations/**").hasAnyRole("OPERATOR", "MANAGER", "ADMIN")
                
                // All other view controllers - MANAGER, ADMIN (excluding user/role management)
                .requestMatchers("/vshep-data-view/**", "/vshep-data/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/certificates/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/region-view/**", "/regions/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/location-view/**", "/locations/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/apk-view/**", "/apk/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/camera-view/**", "/cameras/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/violation-view/**", "/violations/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/autos-view/**", "/autos/**").hasAnyRole("MANAGER", "ADMIN")
                
                // Login page
                .requestMatchers("/login").permitAll()
                
                // Default success page - redirect based on role
                .requestMatchers("/").authenticated()
                
                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/error")
            )
            .userDetailsService(userDetailsService);
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 