<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3><s:message code="menu.header.patient" /></h3>

<security:authorize access="!isAuthenticated()">
	<p><a href="${pageContext.request.contextPath}/users/patient/register"><button class="sideMenuButton"><s:message code="button.register" /></button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_PATIENT')" >
	<p><a href="${pageContext.request.contextPath}/visit/patient/selectDoctor"><button class="sideMenuButton"><s:message code="button.planVisit" /></button></a></p>
	<p><a href="${pageContext.request.contextPath}/visit/patient/myVisits"><button class="sideMenuButton"><s:message code="button.myVisits" /></button></a></p>
	<br>
	<p><a href="${pageContext.request.contextPath}/users/patient/edit"><button class="sideMenuButton"><s:message code="button.editMyData" /></button></a></p>
</security:authorize>
