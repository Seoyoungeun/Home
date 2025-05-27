package com.ssafy.model.service;

import com.ssafy.model.dto.User;
import com.ssafy.model.jpa.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // BCryptPasswordEncoder 주입
    
    @Override
    public User findByIdAndPassword(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return dbUser;
        }
        return null;
    }


    @Override
    public User insert(User user) {
        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // BCrypt로 비밀번호 암호화
        return userRepository.save(user);
    }
    
    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    @Override
    public void update(User user) {
        User existing = userRepository.findById(user.getUid()).orElse(null);
        if (existing != null) {
            // 비밀번호가 전달되었을 경우 새 비밀번호로 교체
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                // 입력된 평문 비밀번호로 저장
                existing.setPassword(user.getPassword());
            }
            // 나머지 필드 업데이트
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            // 기존 role은 유지
            userRepository.save(existing);
        }
    }
    @Override
    public boolean isAdmin(User user) {
        return "ADMIN".equals(user.getRole());
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public void banUser(int uid) {
        Optional<User> optionalUser = userRepository.findById(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole("BANNED");
            userRepository.save(user);
        }
    }
    
    @Override
    public void unbanUser(int uid) {
        Optional<User> optionalUser = userRepository.findById(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole("USER"); // 또는 원래 role 저장했다면 복구
            userRepository.save(user);
        }
    }
    
    @Override
    public User findByUid(int uid) {
        return userRepository.findById(uid).orElse(null);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
