<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Menu pacjenta</h3>

<security:authorize access="!isAuthenticated()">
	<p><a href="${pageContext.request.contextPath}/users/patient/register"><button class="button">Zarejestruj się</button></a></p>
</security:authorize>
<security:authorize access="hasRole('ROLE_PATIENT')" >
	<p><a href="${pageContext.request.contextPath}/users/patient/edit"><button class="button">Edytuj swoje dane</button></a></p>
	<p><a href="${pageContext.request.contextPath}/visit/patient/selectDoctor"><button class="button">Umów wizytę</button></a></p>
</security:authorize>
