<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<nav class="menu">
	<a href="${pageContext.request.contextPath}/"><button class="button">Strona główna</button></a>
	<a href="${pageContext.request.contextPath}/doctors"><button class="button">Zespół lekarzy</button></a>
	<a href="${pageContext.request.contextPath}/services"><button class="button">Usługi dentystyczne</button></a>
	<a href="${pageContext.request.contextPath}/agenda"><button class="button">Terminarz wizyt</button></a>
	<a href="${pageContext.request.contextPath}/panels/patientPanel"><button class="button">Strefa pacjenta</button></a>
	<a href="${pageContext.request.contextPath}/contact"><button class="button">Kontakt z nami</button></a>
	<security:authorize access="hasAnyRole('ROLE_DOCTOR', 'ROLE_ASSISTANT', 'ROLE_ADMIN')">
		<a href="${pageContext.request.contextPath}/panels/employeePanel"><button class="button">Strefa pracownika</button></a>	
	</security:authorize>
</nav>
