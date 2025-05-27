<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
</head>
<body>

<h2>비밀번호 찾기</h2>

<form action="${root}/user/findpw" method="post">
    <label for="email">이메일:</label>
    <input type="email" id="email" name="email" required />
    <button type="submit">찾기</button>
</form>

<c:if test="${not empty message}">
    <p style="color: red;">${message}</p>
</c:if>

</body>
</html>
