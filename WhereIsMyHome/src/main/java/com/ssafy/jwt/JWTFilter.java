package com.ssafy.jwt;

import java.io.IOException;

import com.ssafy.model.dto.User;
import com.ssafy.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer로 시작하지 않으면 필터 통과
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // Bearer 다음 공백 이후부터 추출

        try {
            // 토큰 만료 여부 검사
            if (jwtUtil.isExpired(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT expired");
                return;
            }

            // 토큰에서 사용자 정보 추출
            String email = jwtUtil.getEmail(token);
            String role = jwtUtil.getRole(token);
            int uid = jwtUtil.getUid(token);
            
            // 사용자 정보를 User 객체로 생성 (password는 null로 둠)
            User user = new User();
            user.setEmail(email);
            user.setRole(role);
            user.setUid(uid);
            
            System.out.println("인증된 사용자: " + email + ", 권한: " + role + ", UID: " + uid);
            
            // SecurityContext에 인증 객체 저장
            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
            return;
        }

        filterChain.doFilter(request, response);
        
    }
}
