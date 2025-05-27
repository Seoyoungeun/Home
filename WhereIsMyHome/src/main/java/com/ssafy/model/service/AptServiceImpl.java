package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dao.AptDao;
import com.ssafy.model.dto.AptDeal;
import com.ssafy.model.dto.AptInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService{
	private final AptDao aDao;
	
	@Override
	public List<AptInfo> getAptsOnMap(String swLat, String swLng, String neLat, String neLng, String keyword) {
		return aDao.getAptsOnMap(swLat, swLng, neLat, neLng, keyword);
	}

	@Override
	public AptInfo getAptInfo(String apt_seq) {
		return aDao.getAptInfo(apt_seq);
	}

	@Override
	public List<AptDeal> getAptDealList(String apt_seq, int page, int per_page) {
		return aDao.getAptDealList(apt_seq, page-1, per_page);
	}
	

}
