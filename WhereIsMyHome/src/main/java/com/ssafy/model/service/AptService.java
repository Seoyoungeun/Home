package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.AptDeal;
import com.ssafy.model.dto.AptInfo;

public interface AptService {
	List<AptInfo> getAptsOnMap(String swLat, String swLng, String neLat, String neLng, String keyword);
	AptInfo getAptInfo(String apt_seq);
	List<AptDeal> getAptDealList(String apt_seq, int page, int per_page);
}
