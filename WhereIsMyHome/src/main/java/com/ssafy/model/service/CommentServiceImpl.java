package com.ssafy.model.service;

import com.ssafy.model.dto.Comment;
import com.ssafy.model.jpa.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void insertComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.findByPostIdOrderByCreatedAsc(postId);
    }

    @Override
    public void deleteComment(int cid) {
        commentRepository.deleteById(cid);
    }
    
    @Override
    public Comment getCommentById(int cid) {
        return commentRepository.findById(cid).orElse(null);
    }
}
