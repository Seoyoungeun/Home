package com.ssafy.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DongcodeDao {
	List<String> getSido();
	List<String> getGugun(String sido);
	List<String> getUmd(String sido, String gugun);
}
