<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <style>
        /* 스타일은 그대로 유지 */
    </style>
</head>
<body>
    <div class="container">
        <!-- 헤더에 userLogin.jsp 포함 -->
        <!--<jsp:include page="/include/userLogin.jsp" />-->
        
        <!-- 여기에 메인 컨텐츠 추가 -->
       <h2>Welcome</h2>
        <!-- 더 많은 콘텐츠가 이곳에 추가될 수 있습니다 -->
    </div>
</body>
</html>
