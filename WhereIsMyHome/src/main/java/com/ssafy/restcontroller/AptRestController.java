package com.ssafy.restcontroller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.model.dto.AptDeal;
import com.ssafy.model.dto.AptInfo;
import com.ssafy.model.dto.ListResponse;
import com.ssafy.model.dto.Post;
import com.ssafy.model.service.AptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/apts")
@RequiredArgsConstructor
@Slf4j
public class AptRestController implements RestControllerHelper{
	private final AptService aService;
	
	@GetMapping
	private ResponseEntity<?> getAptsOnMap(@RequestParam String swLat, String swLng, String neLat, String neLng, String keyword){
		try {
			List<AptInfo> aptInfos = aService.getAptsOnMap(swLat, swLng, neLat, neLng, keyword);
			ListResponse<AptInfo> response = new ListResponse(aptInfos);
			
			return handleSuccess(response, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/{apt_seq}")
	private ResponseEntity<?> getAptInfo(@PathVariable String apt_seq){
		try {
			AptInfo aptInfo = aService.getAptInfo(apt_seq);
			return handleSuccess(aptInfo, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/{apt_seq}/deals")
	private ResponseEntity<?> getAptDealList(
			@PathVariable String apt_seq,
			@RequestParam int page,
			@RequestParam int per_page){
		try {			
			List<AptDeal> aptDealInfos = aService.getAptDealList(apt_seq, page, per_page);
			ListResponse<AptDeal> response = new ListResponse(aptDealInfos);
			return handleSuccess(response, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
}
