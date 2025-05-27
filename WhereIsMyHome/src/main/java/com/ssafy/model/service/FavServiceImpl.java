package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dao.FavDao;
import com.ssafy.model.dto.Fav;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavServiceImpl implements FavService{
	private final FavDao fDao;
	
	@Override
	public int addFavorite(String uid, String apt_seq) {
		Fav fav = new Fav();
		fav.setUid(Integer.parseInt(uid));
		fav.setApt_seq(apt_seq);
		return fDao.addFavorite(fav);
	}

	@Override
	public int delFavorite(String uid, String apt_seq) {
		return fDao.delFavorite(uid, apt_seq);
	}

	@Override
	public boolean isFavorite(String uid, String apt_seq) {
		return fDao.isFavorite(uid,apt_seq);
	}

	@Override
	public List<String> favList(String uid) {
		return fDao.favList(uid);
	}

}
