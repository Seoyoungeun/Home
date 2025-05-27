package com.ssafy.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ssafy.jwt.JWTFilter;
import com.ssafy.jwt.JWTUtil;
import com.ssafy.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/v1/login");

        // ✅ CORS 설정
        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://3.149.246.123","http://3.149.246.123:8080","http://3.149.246.123:80"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                configuration.setExposedHeaders(List.of("Authorization"));
                return configuration;
            }
        }));

        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> form.disable());
        http.httpBasic(basic -> basic.disable());

        //  권한 설정
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html").permitAll()
            .requestMatchers("/api/user/login", "/api/user/regist", "/api/user/findpw").permitAll()
            .requestMatchers("/api/v1/login", "/", "/password", "/api/v1/members/join").permitAll()
            .requestMatchers("/api/v1/apts/**", "/api/v1/dongcodes/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/posts", "/api/v1/posts/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v1/posts/**").hasAnyRole("USER", "ADMIN") // ✅ PATCH 허용 추가
            .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasAnyRole("USER", "ADMIN") // ✅ DELETE 허용 추가
            .requestMatchers("/api/user/me", "/api/user/my").authenticated()
            .anyRequest().permitAll()
        );

        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
