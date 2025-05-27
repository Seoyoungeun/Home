package com.ssafy.restcontroller;

import com.ssafy.model.dto.Comment;
import com.ssafy.model.dto.User;
import com.ssafy.model.service.CommentService;
import com.ssafy.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentRestController implements RestControllerHelper {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        // JWT 인증 기반 유저 조회
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return handleFail(new RuntimeException("로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User loginUser = userDetails.getUser();
        System.out.println("로그인 유저 UID: " + loginUser.getUid());  // 확인
        // QnA: 관리자만 가능
        if ("qna".equals(comment.getCategory()) && !"ADMIN".equals(loginUser.getRole())) {
            return handleFail(new RuntimeException("QnA 게시판은 관리자만 댓글을 작성할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        // 공지사항: 댓글 불가
        if ("notice".equals(comment.getCategory())) {
            return handleFail(new RuntimeException("공지사항에는 댓글을 작성할 수 없습니다."), HttpStatus.FORBIDDEN);
        }

        try {
            comment.setUserId(loginUser.getUid());
            commentService.insertComment(comment);
            return handleSuccess(comment);
        } catch (DataAccessException e) {
            return handleFail(e);
        }
    }

    // 게시글 ID로 댓글 리스트 조회
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable("postId") int postId) {
        try {
            List<Comment> list = commentService.getCommentsByPostId(postId);
            return handleSuccess(list);
        } catch (DataAccessException e) {
            return handleFail(e);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{cid}")
    public ResponseEntity<?> deleteComment(@PathVariable("cid") int cid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return handleFail(new RuntimeException("로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User loginUser = userDetails.getUser();

        Comment comment = commentService.getCommentById(cid);
        if (comment == null) {
            return handleFail(new RuntimeException("댓글을 찾을 수 없습니다."), HttpStatus.NOT_FOUND);
        }

        // 작성자 또는 관리자만 삭제 가능
        if (comment.getUserId() != loginUser.getUid() && !"ADMIN".equals(loginUser.getRole())) {
            return handleFail(new RuntimeException("삭제 권한이 없습니다."), HttpStatus.FORBIDDEN);
        }

        try {
            commentService.deleteComment(cid);
            return handleSuccess("댓글 삭제 완료");
        } catch (DataAccessException e) {
            return handleFail(e);
        }
    }
}
