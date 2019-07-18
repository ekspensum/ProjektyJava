<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Menu pracownika</h3>
<security:authorize access="hasRole('ROLE_ADMIN')">
	<p><a href="${pageContext.request.contextPath}/panels/adminPanel"><button class="button">Panel administratora</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_DOCTOR')">
	<p><a href="${pageContext.request.contextPath}/panels/doctorPanel"><button class="button">Panel lekarza</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_ASSISTANT')">
	<p><a href="${pageContext.request.contextPath}/panels/assistantPanel"><button class="button">Panel asystenta</button></a></p>
</security:authorize>
