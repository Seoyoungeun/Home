package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.Post;

public interface PostService {
	int registPost(Post post);
	int updatePost(Post post);
	int deletePost(int pid);
	Post selectPost(int pid);
	List<Post> selectByCategory(String category);
	List<Post> searchByKeyword(String keyword);
	List<Post> findByUserId(int uid);
	void incrementViewCount(int pid);
	List<Post> selectByCategoryWithPaging(String category, int size, int offset);
	int countByCategory(String category);
	List<Post> search(String type, String keyword, int limit, int offset);
}
