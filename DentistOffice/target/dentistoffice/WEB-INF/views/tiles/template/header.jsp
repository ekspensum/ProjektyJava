<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<div>
	<h1>${head }</h1>
	<p id="clock"></p>
</div>
<security:authorize access="isAuthenticated()">
	<h4 id="loggedUser">Zalogowany jako: <security:authentication property="principal.username" /></h4>
</security:authorize>
<security:authorize access="!isAuthenticated()">
	<a href='<c:url value="/login" />'><button id="login">Logowanie</button></a>
</security:authorize>
<security:authorize access="isAuthenticated()">
	<a href='<c:url value="/logout" />'><button id="logout">Wyloguj</button></a>
</security:authorize>
