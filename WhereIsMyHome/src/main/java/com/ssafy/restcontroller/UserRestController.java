package com.ssafy.restcontroller;

import com.ssafy.jwt.JWTUtil;
import com.ssafy.model.dto.Post;
import com.ssafy.model.dto.User;
import com.ssafy.model.service.PostService;
import com.ssafy.model.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController implements RestControllerHelper {

    private final UserService userService;
    private final PostService postService;
    private final JWTUtil jwtUtil;
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        System.out.println("로그인 요청 받음: " + user.getEmail());
        try {
            User loginInfo = userService.findByIdAndPassword(user);

            if (loginInfo != null && !"BANNED".equals(loginInfo.getRole())) {

                // ✅ JWT 생성
                String token = jwtUtil.createJwt(
                    loginInfo.getEmail(),
                    loginInfo.getRole(),
                    loginInfo.getUid(),
                    60 * 60 * 1000L  // 1시간
                );

                // ✅ 응답 헤더에도 추가
                response.setHeader("Authorization", "Bearer " + token);

                // ✅ 응답 바디에도 토큰 포함
                Map<String, Object> result = new HashMap<>();
                result.put("token", token);
                result.put("user", loginInfo);

                return ResponseEntity.ok(result);

            } else if (loginInfo != null) {
                return handleFail(new Exception("접근이 제한된 계정입니다."), HttpStatus.FORBIDDEN);
            } else {
                return handleFail(new Exception("아이디 또는 비밀번호 오류"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 회원가입
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody User user) {
        try {
            if (userService.isEmailExists(user.getEmail())) {
                return handleFail(new Exception("이미 가입된 이메일입니다."), HttpStatus.BAD_REQUEST);
            }

            user.setRole("USER"); // 기본 역할
            User result = userService.insert(user);
            return handleSuccess(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleFail(e);
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/my")
    public ResponseEntity<?> getMyPosts(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Post> posts = postService.findByUserId(user.getUid());
        return ResponseEntity.ok(Map.of("data", posts));
    }

    // 회원 정보 조회
    @GetMapping("/{uid}")
    public ResponseEntity<?> getUser(@PathVariable int uid) {
        try {
            User user = userService.findByUid(uid);
            if (user != null) return handleSuccess(user);
            else return handleFail(new Exception("존재하지 않는 사용자입니다."), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            userService.update(user);
            User updatedUser = userService.findByUid(user.getUid());
            return handleSuccess(updatedUser);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 비밀번호 찾기
    @PostMapping("/findpw")
    public ResponseEntity<?> findPassword(@RequestParam String email) {
        try {
            boolean exists = userService.isEmailExists(email);
            if (exists) {
                // 실제로는 이메일 발송 로직 필요
                return handleSuccess("임시 비밀번호가 전송되었습니다.");
            } else {
                return handleFail(new Exception("등록되지 않은 이메일입니다."), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 관리자 - 회원 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        try {
            List<User> users = userService.findAll();
            return handleSuccess(users);
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 관리자 - 회원 밴
    @PostMapping("/ban")
    public ResponseEntity<?> banUser(@RequestParam int uid) {
        try {
            userService.banUser(uid);
            return handleSuccess("사용자가 차단되었습니다.");
        } catch (Exception e) {
            return handleFail(e);
        }
    }

    // 관리자 - 밴 해제
    @PostMapping("/unban")
    public ResponseEntity<?> unbanUser(@RequestParam int uid) {
        try {
            userService.unbanUser(uid);
            return handleSuccess("사용자 차단이 해제되었습니다.");
        } catch (Exception e) {
            return handleFail(e);
        }
    }
}
