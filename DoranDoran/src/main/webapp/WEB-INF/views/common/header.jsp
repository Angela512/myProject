<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- header 시작 -->

<div id="main_login">
   <ul class="member_top">
      <c:if test="${!empty user_num && user_auth == 3}">
         <li><a
            href="${pageContext.request.contextPath}/member/memberList.do">회원관리</a>
         </li>
      </c:if>

      <c:if test="${!empty user_num && user_auth == 2}">
         <li><a
            href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
         </li>
      </c:if>

      <c:if test="${!empty user_num && !empty user_photo}">
         <li class="menu-profile"><img src="${pageContext.request.contextPath}/upload/${user_photo}" 
         width="25" height="25" class="my-photo"></li>
      </c:if>

      <c:if test="${!empty user_num && empty user_photo}">
         <li class="menu-profile"><img src="${pageContext.request.contextPath}/images/face.png" 
         width="25" height="25" class="my-photo"></li>
      </c:if>

      <c:if test="${!empty user_num}">
         <li class="menu-logout">[<span>${user_name}</span>] <a
            href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
         </li>
      </c:if>

      <c:if test="${empty user_num}">
         <li><a
            href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입 | </a>
         </li>
         <li><a
            href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
         </li>
      </c:if>
   </ul>
</div>
<div id="main_logo">
   <h1 class="align-center">
      <a href="${pageContext.request.contextPath}/main/main.do">도란도란</a>
   </h1>
</div>
<div id="main_nav">
   <ul>
      <li><a href="${pageContext.request.contextPath}/notice/list.do">공지사항</a>
      </li>

      <li><a href="${pageContext.request.contextPath}/food/list.do">맛집찾기</a>
      </li>

      <li><a href="${pageContext.request.contextPath}/trade/list.do">중고거래</a>
      </li>

      <li><a href="${pageContext.request.contextPath}/job/list.do">구인구직</a>
      </li>

      <li><a href="${pageContext.request.contextPath}/board/list.do">자유게시판</a>
      </li>


   </ul>


</div>
<!-- header 끝 -->




