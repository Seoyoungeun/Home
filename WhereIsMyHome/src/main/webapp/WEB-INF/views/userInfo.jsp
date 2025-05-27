<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<h2>유저 상세정보</h2>
<table border="1">
    <tr><th>UID</th><td>${user.uid}</td></tr>
    <tr><th>이메일</th><td>${user.email}</td></tr>
    <tr><th>아이디</th><td>${user.id}</td></tr>
    <tr><th>이름</th><td>${user.name}</td></tr>
    <tr><th>권한</th><td>${user.role}</td></tr>
</table>

<a href="${root}/user/userList">← 유저 목록으로</a>
