package com.ssafy.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.dto.AptDeal;
import com.ssafy.model.dto.AptInfo;

@Mapper
public interface AptDao {
	List<AptInfo> getAptsOnMap(String swLat, String swLng, String neLat, String neLng, String keyword);
	AptInfo getAptInfo(String apt_seq);
	List<AptDeal> getAptDealList(String apt_seq, int page, int per_page);
}
