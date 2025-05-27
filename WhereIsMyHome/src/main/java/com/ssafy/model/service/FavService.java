package com.ssafy.model.service;

import java.util.List;

public interface FavService {
	boolean isFavorite(String uid, String apt_seq);
	int addFavorite(String uid, String apt_seq);
	int delFavorite(String uid, String apt_seq);
	List<String> favList(String uid);
}
