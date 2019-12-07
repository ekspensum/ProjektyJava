<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<div>
	<h1><s:message code="header.name" /></h1>
	<p id="clock"></p>
</div>
<div>
	<a href="https://dentistofficemobile.herokuapp.com/"><button id="linkMobile"><s:message code="button.mobile" /></button></a>
</div>
<security:authorize access="isAuthenticated()">
	<h4 id="loggedUser"><s:message code="header.logged" /><security:authentication property="principal.username" /></h4>
</security:authorize>
<security:authorize access="!isAuthenticated()">
	<a href='<c:url value="/login" />'><button id="login"><s:message code="button.login" /></button></a>
</security:authorize>
<security:authorize access="isAuthenticated()">
	<a href='<c:url value="/logout" />'><button id="logout"><s:message code="button.logout" /></button></a>
</security:authorize>
