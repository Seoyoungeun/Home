package com.ssafy.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.model.dao.PostDao;
import com.ssafy.model.dto.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostDao pDao;
	
	
	@Override
	public int registPost(Post post) {
		return pDao.insert(post);
	}


	@Override
	public int updatePost(Post post) {
		return pDao.update(post);
	}


	@Override
	public int deletePost(int pid) {
		return pDao.delete(pid);
	}


	@Override
	public Post selectPost(int pid) {
		return pDao.select(pid);
	}


	@Override
	public List<Post> selectByCategory(String category) {
	    return pDao.selectByCategory(category);
	}


	@Override
	public List<Post> searchByKeyword(String keyword) {
		return pDao.searchByKeyword(keyword);
	}

	@Override
	public List<Post> findByUserId(int uid) {
	    return pDao.findByUserId(uid);
	}
	
	@Override
	public void incrementViewCount(int pid) {
	    pDao.incrementViewCount(pid);
	}
	
	@Override
	public List<Post> selectByCategoryWithPaging(String category, int size, int offset) {
	    return pDao.selectByCategoryWithPaging(category, size, offset);
	}

	@Override
	public int countByCategory(String category) {
	    return pDao.countByCategory(category);
	}
	
    @Override
    public List<Post> search(String type, String keyword, int limit, int offset) {
        return pDao.search(type, keyword, limit, offset);
    }
}
