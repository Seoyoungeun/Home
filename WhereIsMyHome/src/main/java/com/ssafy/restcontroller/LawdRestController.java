package com.ssafy.restcontroller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.model.service.LawdService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/lawds")
@RequiredArgsConstructor
@Slf4j
public class LawdRestController implements RestControllerHelper{
	private final RestTemplate restTemplate;
	private final LawdService lService;
	
	@Value("${api.key_data}")
	private String dataKey;
	
	@GetMapping()
	private ResponseEntity<?> updateLawdTable(){
		String serviceKey = dataKey;  // Decoding된 키 넣어야 함
		
		try {
			// step.1 : total Count 가져오기
			String url = String.format(
	            "https://api.odcloud.kr/api/15123287/v1/uddi:a3a4ea9d-2e34-4e32-a092-ef8feb8d71f1?" +
	            "page=1&perPage=1&serviceKey=%s",
	            serviceKey
	        );
			
			Map<String, Object> result = restTemplate.getForObject(new URI(url.toString()), Map.class);
			Integer totalCount = (Integer) result.get("totalCount");
			
			// step.2 : 모든 lawd_cd 정보 가져와서 중복되지 않게 set으로 임시보관
			Set<String> lawdCd5Set = new HashSet<>();
			
	        String perPage = "1000";
	        int totalPages = (int) Math.ceil((double) totalCount / Integer.parseInt(perPage));
	        
	        for(int i=1; i<=totalPages; ++i) {
	        	url = String.format(
	    	            "https://api.odcloud.kr/api/15123287/v1/uddi:a3a4ea9d-2e34-4e32-a092-ef8feb8d71f1?" +
	    	            "page=%s&perPage=%s&serviceKey=%s",
	    	            Integer.toString(i), perPage, serviceKey
	    	        );
	        	result = restTemplate.getForObject(new URI(url.toString()), Map.class);
	        	
	        	// 1. data 필드를 꺼내고 List로 캐스팅
		        List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("data");
	        	
		        // 2. currentCount 만큼 반복
	        	for(int j=0; j<(Integer)result.get("currentCount"); ++j) {
	        		Map<String, Object> item = dataList.get(j);
	        		
	        		Object codeObj = item.get("법정동코드");
		            if (codeObj == null) continue;
		            
		            String code = codeObj.toString();
		            if (code.length() >= 5) {
		                String lawdCd5 = code.substring(0, 5);
		                lawdCd5Set.add(lawdCd5);
		            }
	        	}
	        }
	        	     
	        
	        // step.3 : 데이터 베이스에 저장
	        // transaction 처리해서 문제 생기면 롤백하게 만들어야 한다 
	        lService.refreshLawdCd5(lawdCd5Set);
	        	   
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	    	return handleFail(e);
	    }
	}
	
	
	
}
