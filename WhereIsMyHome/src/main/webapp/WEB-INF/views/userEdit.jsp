<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

        <!-- 회원 정보 수정 폼 -->
<h3>회원 정보 수정</h3>
<form action="${root}/user/edit" method="post">
	<label for="userEmail">이메일</label>
	<input type="email" id="userEmail" name="email" value="${user.email}" readonly> <!-- 이메일은 수정 불가 -->
	<label for="userId">아이디</label>
    <input type="text" id="userId" name="uid" value="${user.id}" readonly>
	<label for="userName">이름</label>
	<input type="text" id="userName" name="name" value="${user.name}" required>
	<label for="password">비밀번호</label>
	<input type="password" id="password" name="password" placeholder="새 비밀번호" required>

	<button type="submit">수정하기</button>
</form>