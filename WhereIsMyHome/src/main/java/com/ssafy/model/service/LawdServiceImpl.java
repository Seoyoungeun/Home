package com.ssafy.model.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.dao.LawdDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LawdServiceImpl implements LawdService{
	private final LawdDao lDao;

	@Override
	public List<String> getAll() {
		return lDao.getAll();
	}

	@Override
	@Transactional
	public int refreshLawdCd5(Set<String> lawd_cd5set) {
		lDao.deleteAll();
		return lDao.insertAll(lawd_cd5set);
	}

}
