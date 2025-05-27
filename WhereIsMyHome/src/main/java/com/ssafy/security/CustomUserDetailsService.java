package com.ssafy.security;

import com.ssafy.model.dto.User;
import com.ssafy.model.jpa.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            throw new UsernameNotFoundException("사용자 없음: " + email);
        } else {
            System.out.println("사용자 조회 결과: " + user);
            System.out.println("UID: " + user.getUid());
        }
        return new CustomUserDetails(user);
    }
}