package com.ssafy.restcontroller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.AptInfo;
import com.ssafy.model.dto.ListResponse;
import com.ssafy.model.service.AptService;
import com.ssafy.model.service.FavService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/favs")
@RequiredArgsConstructor
@Slf4j
public class FavRestContoller implements RestControllerHelper{
	private final FavService fService;
	
	@GetMapping
	private ResponseEntity<?> isFavorite(@RequestParam String uid, @RequestParam String apt_seq){
		try {
			boolean result = fService.isFavorite(uid, apt_seq);
			
			return handleSuccess(result, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/list")
	private ResponseEntity<?> favList(@RequestParam String uid){
		try {
			List<String> result = fService.favList(uid);
			
			return handleSuccess(result, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@PostMapping
	private ResponseEntity<?> addFavorite(@RequestParam String uid, @RequestParam String apt_seq){
		try {
			int result = fService.addFavorite(uid, apt_seq);
			
			return handleSuccess(result, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@DeleteMapping
	private ResponseEntity<?> delFavorite(@RequestParam String uid, @RequestParam String apt_seq){
		try {
			int result = fService.delFavorite(uid, apt_seq);
			
			return handleSuccess(result);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
}
