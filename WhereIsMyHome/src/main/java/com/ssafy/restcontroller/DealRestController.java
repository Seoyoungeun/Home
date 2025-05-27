package com.ssafy.restcontroller;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
@Slf4j
public class DealRestController implements RestControllerHelper {
	private final RestTemplate restTemplate;
	@Value("${api.key_data}")
	private String dataKey;
	
	@GetMapping("/apts/sale")
	private ResponseEntity<?> getAptSale(){
		try {
	        String serviceKey = dataKey;  // Decoding된 키 넣어야 함
	        String lawdCd = "11110";             // 서울 종로구
	        String dealYmd = "202403";           // 2024년 3월

	        String url = String.format(
	            "https://apis.data.go.kr/1613000/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade?" +
	            "LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s",
	            lawdCd, dealYmd, serviceKey
	        );

	        Map<String, Object> result = restTemplate.getForObject(new URI(url.toString()), Map.class);
            return handleSuccess(result);
	    } catch (Exception e) {
	    	return handleFail(e);
	    }
	}
	
	@GetMapping("/apts/rent")
	private ResponseEntity<?> getAptRent(){
		try {
	        String serviceKey = dataKey;  // Decoding된 키 넣어야 함
	        String lawdCd = "11110";             // 서울 종로구
	        String dealYmd = "202403";           // 2024년 3월

	        String url = String.format(
	            "https://apis.data.go.kr/1613000/RTMSDataSvcAptRent/getRTMSDataSvcAptRent?" +
	            "LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s",
	            lawdCd, dealYmd, serviceKey
	        );

	        Map<String, Object> result = restTemplate.getForObject(new URI(url.toString()), Map.class);
            return handleSuccess(result);
	    } catch (Exception e) {
	    	return handleFail(e);
	    }
	}
	
	@GetMapping("/houses/sale")
	private ResponseEntity<?> getHouseSale(){
		try {
	        String serviceKey = dataKey;  // Decoding된 키 넣어야 함
	        String lawdCd = "11110";             // 서울 종로구
	        String dealYmd = "202403";           // 2024년 3월

	        String url = String.format(
	            "https://apis.data.go.kr/1613000/RTMSDataSvcRHTrade/getRTMSDataSvcRHTrade?" +
	            "LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s",
	            lawdCd, dealYmd, serviceKey
	        );

	        Map<String, Object> result = restTemplate.getForObject(new URI(url.toString()), Map.class);
            return handleSuccess(result);
	    } catch (Exception e) {
	    	return handleFail(e);
	    }
	}
	
	@GetMapping("/houses/rent")
	private ResponseEntity<?> getHouseRent(){
		try {
	        String serviceKey = dataKey;  // Decoding된 키 넣어야 함
	        String lawdCd = "11110";             // 서울 종로구
	        String dealYmd = "202403";           // 2024년 3월

	        String url = String.format(
	            "https://apis.data.go.kr/1613000/RTMSDataSvcRHRent/getRTMSDataSvcRHRent?" +
	            "LAWD_CD=%s&DEAL_YMD=%s&serviceKey=%s",
	            lawdCd, dealYmd, serviceKey
	        );

	        Map<String, Object> result = restTemplate.getForObject(new URI(url.toString()), Map.class);
            return handleSuccess(result);
	    } catch (Exception e) {
	    	return handleFail(e);
	    }
	}
}
