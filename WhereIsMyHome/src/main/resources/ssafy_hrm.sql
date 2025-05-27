-- 데이터베이스 초기화 
USE ssafyhome;

-- users 테이블 생성 (id 제거됨)
CREATE TABLE `users` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `role` VARCHAR(20) DEFAULT 'USER',

  PRIMARY KEY (`uid`)
);

-- users 테이블에 시군구추가.
ALTER TABLE users
ADD sido VARCHAR(50),
ADD gugun VARCHAR(50),
ADD dong VARCHAR(50);

-- 기본 관리자 계정 추가
INSERT INTO users (name, email, password, role)
VALUES ('관리자', 'admin@ssafy.com', '1234', 'ADMIN');

INSERT INTO users (name, email, password, role)
VALUES ('유저', 'user@ssafy.com', '1234', 'USER');

-- posts 테이블 생성
CREATE TABLE `posts` (
  `pid` INT NOT NULL AUTO_INCREMENT,         -- 게시글 ID (PK)
  `user_id` INT NOT NULL,                    -- 작성자 (users.uid 참조)
  `title` VARCHAR(200) NOT NULL,             -- 제목
  `text` TEXT NOT NULL,                      -- 본문
  `category` ENUM('notice', 'qna', 'free') NOT NULL, -- 카테고리
  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,      -- 작성 시각
  `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시각

  PRIMARY KEY (`pid`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`uid`) ON DELETE CASCADE
);

CREATE TABLE `comments` (
   cid INT AUTO_INCREMENT PRIMARY KEY,
  post_id INT NOT NULL,
  user_id INT NOT NULL,
  content TEXT NOT NULL,
  category ENUM('notice', 'qna', 'free') NOT NULL,
  created DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES posts(pid) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE
);

CREATE TABLE favorites (
  id INT AUTO_INCREMENT PRIMARY KEY,           -- 고유 ID
  uid INT NOT NULL,                        -- 사용자 ID (외래키)
  apt_seq VARCHAR(20) NOT NULL,                   -- 아파트 고유 ID

  -- 중복 등록 방지
  CONSTRAINT uq_user_apt UNIQUE (uid, apt_seq),

  -- 외래키 제약
  CONSTRAINT fk_fav_user FOREIGN KEY (uid)
    REFERENCES users(uid)
    ON DELETE CASCADE
);

-- 인덱스 추가
CREATE INDEX idx_fav_user ON favorites(uid);
CREATE INDEX idx_fav_apt ON favorites(apt_seq);
-- 결과 확인용
SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM comments;