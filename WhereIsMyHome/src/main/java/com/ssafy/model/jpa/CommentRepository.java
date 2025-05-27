package com.ssafy.model.jpa;

import com.ssafy.model.dto.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(int postId);  // 게시글 ID로 댓글 조회
    List<Comment> findByPostIdOrderByCreatedAsc(int postId);
}
