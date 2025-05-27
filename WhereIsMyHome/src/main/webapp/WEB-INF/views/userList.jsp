<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록 (관리자 전용)</title>
</head>
<body>

<h2>회원 목록</h2>

<table border="1">
    <thead>
        <tr>
            <th>UID</th>
            <th>이메일</th>
            <th>ID</th>
            <th>이름</th>
            <th>권한</th>
            <th>상태</th>
            <th>관리</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.uid}</td>
            <td>${user.email}</td>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.role}</td>
            <td>
                <c:choose>
                    <c:when test="${user.role eq 'BANNED'}">
                        <span style="color:red;">차단됨</span>
                    </c:when>
                    <c:otherwise>
                        정상
                    </c:otherwise>
                </c:choose>
            </td>
			<td>
			    <c:if test="${user.role ne 'ADMIN'}">
			        <c:choose>
			            <c:when test="${user.role eq 'BANNED'}">
			                <!-- 차단 해제 버튼 -->
			                <form action="${root}/user/admin/unban" method="post" style="display:inline;">
			                    <input type="hidden" name="uid" value="${user.uid}" />
			                    <button type="submit">차단 해제</button>
			                </form>
			            </c:when>
			            <c:otherwise>
			                <!-- 차단 버튼 -->
			                <form action="${root}/user/admin/ban" method="post" style="display:inline;">
			                    <input type="hidden" name="uid" value="${user.uid}" />
			                    <button type="submit">차단</button>
			                </form>
			            </c:otherwise>
			        </c:choose>
			    </c:if>
			</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<a href="${root}/">홈으로</a>

</body>
</html>
