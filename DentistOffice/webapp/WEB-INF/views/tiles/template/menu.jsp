<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<nav class="menu">
	<a href="${pageContext.request.contextPath}/"><button class="menuButton">Strona główna</button></a>
	<a href="${pageContext.request.contextPath}/doctors"><button class="menuButton">Zespół lekarzy</button></a>
	<a href="${pageContext.request.contextPath}/services"><button class="menuButton">Usługi dentystyczne</button></a>
	<a href="${pageContext.request.contextPath}/agenda"><button class="menuButton">Terminarz wizyt</button></a>
	<a href="${pageContext.request.contextPath}/panels/patientPanel"><button class="menuButton">Strefa pacjenta</button></a>
	<a href="${pageContext.request.contextPath}/contact"><button class="menuButton">Kontakt z nami</button></a>
	<security:authorize access="hasAnyRole('ROLE_DOCTOR', 'ROLE_ASSISTANT', 'ROLE_ADMIN')">
		<a href="${pageContext.request.contextPath}/panels/employeePanel"><button class="menuButton">Strefa pracownika</button></a>	
	</security:authorize>
</nav>
