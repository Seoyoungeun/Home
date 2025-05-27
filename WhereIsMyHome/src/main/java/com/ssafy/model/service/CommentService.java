package com.ssafy.model.service;

import com.ssafy.model.dto.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(Comment comment);
    List<Comment> getCommentsByPostId(int postId);
    void deleteComment(int cid);
    Comment getCommentById(int cid);
}
