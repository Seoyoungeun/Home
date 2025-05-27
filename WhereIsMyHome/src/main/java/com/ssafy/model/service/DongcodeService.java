package com.ssafy.model.service;

import java.util.List;

public interface DongcodeService {
	List<String> getSido();
	List<String> getGugun(String sido);
	List<String> getUmd(String sido, String gugun);
}
