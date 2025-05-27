package com.ssafy.restcontroller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.AptInfo;
import com.ssafy.model.dto.ListResponse;
import com.ssafy.model.service.AptService;
import com.ssafy.model.service.DongcodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/dongcodes")
@RequiredArgsConstructor
@Slf4j
public class DongcodeRestController implements RestControllerHelper{
	private final DongcodeService dService;
	
	@GetMapping()
	private ResponseEntity<?> getSido(){
		try {
			List<String> sidos = dService.getSido();
			ListResponse<String> response = new ListResponse(sidos);
			
			return handleSuccess(response, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/{sido}")
	private ResponseEntity<?> getGugun(@PathVariable String sido){
		try {
			List<String> guguns = dService.getGugun(sido);
			ListResponse<String> response = new ListResponse(guguns);
			
			return handleSuccess(response, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/{sido}/{gugun}")
	private ResponseEntity<?> getUmd(@PathVariable String sido, @PathVariable String gugun){
		try {
			List<String> umds = dService.getUmd(sido, gugun);
			ListResponse<String> response = new ListResponse(umds);
			
			return handleSuccess(response, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
}
