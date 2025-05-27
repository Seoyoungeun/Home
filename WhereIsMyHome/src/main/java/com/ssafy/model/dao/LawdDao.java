package com.ssafy.model.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LawdDao {
	List<String> getAll();
	int deleteAll();
	int insertAll(@Param("lawdList") Set<String> lawd_cd5set);
}
