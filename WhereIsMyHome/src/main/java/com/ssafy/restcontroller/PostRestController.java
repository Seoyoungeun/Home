package com.ssafy.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.dto.Post;
import com.ssafy.model.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostRestController implements RestControllerHelper{
	private final PostService pService;
	
	@PostMapping
	private ResponseEntity<?> registPost(@RequestBody Post post){
		try {
			pService.registPost(post);
			return handleSuccess(post, HttpStatus.CREATED);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@PatchMapping
	private ResponseEntity<?> updatePost(@RequestBody Post post){
		try {
			pService.updatePost(post);
			return handleSuccess(post, HttpStatus.NO_CONTENT);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@DeleteMapping("/{pid}")
	private ResponseEntity<?> deletePost(@PathVariable("pid") int pid){
		try {
			pService.deletePost(pid);
			return handleSuccess(pid, HttpStatus.NO_CONTENT);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/{pid}")
	private ResponseEntity<?> selectPost(@PathVariable("pid")  int pid){
		try {
			pService.incrementViewCount(pid); //조회수 증가
			Post p = pService.selectPost(pid); //게시글 불러오기
			return handleSuccess(p);
		}catch (DataAccessException e) {
			return handleFail(e);
		}
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<?> getPostsByCategoryWithPaging(
	    @PathVariable("category") String category,
	    @RequestParam(name = "page", defaultValue = "1") int page,
	    @RequestParam(name = "size", defaultValue = "10") int size){
	    try {
	        int offset = (page - 1) * size;
	        List<Post> list = pService.selectByCategoryWithPaging(category, size, offset);
	        return handleSuccess(list);
	    } catch (DataAccessException e) {
	        return handleFail(e);
	    }
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchPosts(
	    @RequestParam(name = "type") String type,
	    @RequestParam(name = "keyword") String keyword,
	    @RequestParam(name = "page", defaultValue = "1") int page,
	    @RequestParam(name = "size", defaultValue = "10") int size
	) {
	    try {
	        int offset = (page - 1) * size;
	        List<Post> result = pService.search(type, keyword, size, offset);
	        return handleSuccess(result);
	    } catch (Exception e) {
	        return handleFail(e);
	    }
	}
}
