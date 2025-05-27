package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dao.AptDao;
import com.ssafy.model.dao.DongcodeDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DongcodeServiceImpl implements DongcodeService {
	private final DongcodeDao dDao;
	
	@Override
	public List<String> getSido() {
		return dDao.getSido();
	}

	@Override
	public List<String> getGugun(String sido) {
		return dDao.getGugun(sido);
	}

	@Override
	public List<String> getUmd(String sido, String gugun) {
		return dDao.getUmd(sido, gugun);
	}

}
