package com.ssafy.controller;

import com.ssafy.model.dto.User;
import com.ssafy.model.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        session.invalidate(); // 기존 세션 초기화
        return "Loginpage";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User loginInfo = userService.findByIdAndPassword(user);
        if (loginInfo != null && !"BANNED".equals(loginInfo.getRole())) {
            session.setAttribute("loginInfo", loginInfo);
            return "redirect:/";
        } else if (loginInfo != null && "BANNED".equals(loginInfo.getRole())) {
            model.addAttribute("message", "접근이 제한된 계정입니다.");
        } else {
            model.addAttribute("message", "아이디 또는 비밀번호가 틀렸습니다.");
        }
        return "Loginpage";
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 비밀번호 찾기 폼
    @GetMapping("/findpw")
    public String findPWForm() {
        return "findPW";
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findpw")
    public String findPW(@RequestParam("email") String email, Model model) {
        boolean foundUser = userService.isEmailExists(email);
        if (foundUser) {
            model.addAttribute("message", "해당 이메일로 임시 비밀번호를 전송했습니다.");
        } else {
            model.addAttribute("message", "해당 이메일로 가입된 계정이 없습니다.");
        }
        return "findPW";
    }

    // 회원가입 폼
    @GetMapping("/regist")
    public String registForm() {
        return "join";
    }

    // 회원가입 처리
    @PostMapping("/regist")
    public String regist(@ModelAttribute User user, Model model) {
        if (userService.isEmailExists(user.getEmail())) {
            model.addAttribute("message", "이미 가입된 이메일입니다.");
            return "join";
        }

        user.setRole("USER"); // 기본값 설정
        userService.insert(user);
        return "redirect:/user/login";
    }

    // 회원 정보 수정 폼
    @GetMapping("/edit")
    public String editForm(HttpSession session, Model model) {
        User loginInfo = (User) session.getAttribute("loginInfo");
        if (loginInfo == null) return "redirect:/user/login";

        model.addAttribute("user", loginInfo);
        return "userEdit";
    }

    // 회원 정보 수정 처리
    @PostMapping("/edit")
    public String edit(@ModelAttribute User user, HttpSession session) {
        User loginInfo = (User) session.getAttribute("loginInfo");
        if (loginInfo == null) return "redirect:/user/login";

        user.setUid(loginInfo.getUid()); // uid를 보존
        user.setRole(loginInfo.getRole()); // 역할 보존
        userService.update(user);
        session.setAttribute("loginInfo", userService.findByUid(user.getUid())); // 세션 정보 갱신
        return "redirect:/";
    }

    // 관리자: 회원 목록 보기
    @GetMapping("/userList")
    public String userList(HttpSession session, Model model) {
        User loginInfo = (User) session.getAttribute("loginInfo");
        if (loginInfo == null || !"ADMIN".equals(loginInfo.getRole())) {
            return "redirect:/";
        }

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList"; // userList.jsp
    }
    
    @PostMapping("/admin/ban")
    public String banUser(@RequestParam("uid") int uid, HttpSession session) {
        User loginInfo = (User) session.getAttribute("loginInfo");
        if (loginInfo == null || !"ADMIN".equals(loginInfo.getRole())) {
            return "redirect:/";
        }
        userService.banUser(uid);
        return "redirect:/user/userList";
    }
    
    @PostMapping("/admin/unban")
    public String unbanUser(@RequestParam("uid") int uid, HttpSession session) {
        User loginInfo = (User) session.getAttribute("loginInfo");
        if (loginInfo == null || !"ADMIN".equals(loginInfo.getRole())) {
            return "redirect:/";
        }
        userService.unbanUser(uid);
        return "redirect:/user/userList";
    }
}
