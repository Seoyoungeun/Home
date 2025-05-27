// UserRepository.java
package com.ssafy.model.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.model.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
