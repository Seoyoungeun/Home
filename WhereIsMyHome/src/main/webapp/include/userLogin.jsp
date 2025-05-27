<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<c:choose>
  <c:when test="${loginInfo == null}">
    <!-- 로그인 상태가 아니면 로그인 버튼을 보여줍니다 -->
    <a href="${root}/user/login">로그인</a>
  </c:when>

  <c:otherwise>
    <!-- 로그인 상태일 때 사용자 정보 표시 -->
    <div class="info">
      ${loginInfo.name}님 (${loginInfo.email}), 로그인하였습니다.<br/>
      <a href="${root}/user/logout">로그아웃</a>
      <c:choose>
        <c:when test="${loginInfo.role == 'ADMIN'}">
          <!-- 관리자일 경우 회원 리스트로 이동 -->
          <a href="${root}/user/userList">회원 리스트</a>
        </c:when>
        <c:otherwise>
          <!-- 일반 사용자일 경우 회원 정보 수정 -->
          <a href="${root}/user/edit">회원정보수정</a>
        </c:otherwise>
      </c:choose>
    </div>
  </c:otherwise>
</c:choose>
