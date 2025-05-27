package com.ssafy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
	    if (userDetails != null) {
	        model.addAttribute("username", userDetails.getUsername());
	    }
	    return "index"; // index.jsp는 인증 없어도 볼 수 있게 구성
	}
}