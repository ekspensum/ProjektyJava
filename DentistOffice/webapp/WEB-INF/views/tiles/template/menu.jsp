<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<nav class="menu">
	<a href="${pageContext.request.contextPath}/"><button class="menuButton"><s:message code="button.mainPage" /></button></a>
	<a href="${pageContext.request.contextPath}/doctors"><button class="menuButton"><s:message code="button.team" /></button></a>
	<a href="${pageContext.request.contextPath}/services"><button class="menuButton"><s:message code="button.treatments" /></button></a>
	<a href="${pageContext.request.contextPath}/agenda"><button class="menuButton"><s:message code="button.agenda" /></button></a>
	<a href="${pageContext.request.contextPath}/panels/patientPanel"><button class="menuButton"><s:message code="button.patientZone" /></button></a>
	<a href="${pageContext.request.contextPath}/contact"><button class="menuButton"><s:message code="button.contactUs" /></button></a>
	<security:authorize access="hasAnyRole('ROLE_DOCTOR', 'ROLE_ASSISTANT', 'ROLE_ADMIN', 'ROLE_OWNER')">
		<a href="${pageContext.request.contextPath}/panels/employeePanel"><button class="menuButton"><s:message code="button.employeeZone" /></button></a>	
	</security:authorize>
</nav>
