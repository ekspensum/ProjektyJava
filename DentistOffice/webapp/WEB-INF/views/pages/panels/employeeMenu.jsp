<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Menu pracownika</h3>
<security:authorize access="hasRole('ROLE_ADMIN')">
	<p><a href="${pageContext.request.contextPath}/panels/adminPanel"><button class="sideMenuButton">Panel administratora</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_DOCTOR')">
	<p><a href="${pageContext.request.contextPath}/panels/doctorPanel"><button class="sideMenuButton">Panel lekarza</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_ASSISTANT')">
	<p><a href="${pageContext.request.contextPath}/panels/assistantPanel"><button class="sideMenuButton">Panel asystenta</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_OWNER')">
	<p><a href="${pageContext.request.contextPath}/users/admin/owner/register"><button class="sideMenuButton">Dodaj administratora</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/admin/owner/selectToEdit"><button class="sideMenuButton">Edytuj administratora</button></a></p>
</security:authorize>