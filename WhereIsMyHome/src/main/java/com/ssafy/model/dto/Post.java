package com.ssafy.model.dto;

import lombok.Data;

@Data
public class Post {
	private int pid; // 게시글 고유 번호
	private String user_id; // 유저 고유 uid
	private String user_name; // 유저 이름
	private String title;
	private String text;
	private String category;
	private String created;
	private String updated;
	private int view_count;
	private int comment_count;
}
