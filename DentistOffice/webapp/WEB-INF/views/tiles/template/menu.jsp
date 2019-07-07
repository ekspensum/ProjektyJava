<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<nav>
	<a href="${pageContext.request.contextPath}/"><button class="button">Strona główna</button></a>
	<a href="${pageContext.request.contextPath}/doctorRegister"><button class="button">Add new doctor</button></a>
	<a href="${pageContext.request.contextPath}/doctorEdit"><button class="button">Edit dta doctor</button></a>
	<a href="${pageContext.request.contextPath}/visitSelectDoctorByPatient"><button class="button">Visit</button></a>
	<a href="${pageContext.request.contextPath}/contact"><button class="button">Kontakt z nami</button></a>
</nav>


<%-- <security:authorize access="isAuthenticated()"> --%>
<%-- <h4>Zalogowany jako: <security:authentication property="principal.username" /></h4> --%>
<%-- </security:authorize> --%>
<!-- <br><br> -->
<%-- <a href='<c:url value="login" />'><button>Zaloguj</button></a> --%>
<!-- <br><br> -->
<%-- <a href='<c:url value="logout" />'><button>Wyloguj</button></a> --%>
<!-- <br><br> -->
<!-- <p>Domyślny dostęp do rejestracji</p> -->
<!-- <p>Login: owner</p> -->
<!-- <p>Hasło: owner</p> -->