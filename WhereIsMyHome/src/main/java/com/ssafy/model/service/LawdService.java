package com.ssafy.model.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface LawdService {
	List<String> getAll();
	int refreshLawdCd5(Set<String> lawd_cd5set);
}
