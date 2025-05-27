package com.ssafy.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.dto.Fav;

@Mapper
public interface FavDao {
	int addFavorite(Fav fav);
	int delFavorite(String uid, String apt_seq);
	boolean isFavorite(String uid, String apt_seq);
	List<String> favList(String uid);
}
