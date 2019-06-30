<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<nav>
	<p><a href="${pageContext.request.contextPath}/"><button class="button">Strona główna</button></a></p>
	<p><a href="${pageContext.request.contextPath}/taskForm"><button class="button">Dodaj nowe zadanie</button></a></p>
	<p><a href="${pageContext.request.contextPath}/register"><button class="button">Rejestruj użytkownika</button></a></p>
	<p><a href="${pageContext.request.contextPath}/contact"><button class="button">Kontakt z nami</button></a></p>
</nav>


<security:authorize access="isAuthenticated()">
<h4>Zalogowany jako: <security:authentication property="principal.username" /></h4>
</security:authorize>
<br><br>
<a href='<c:url value="login" />'><button>Zaloguj</button></a>
<br><br>
<a href='<c:url value="logout" />'><button>Wyloguj</button></a>
<br><br>
<p>Domyślny dostęp do rejestracji</p>
<p>Login: owner</p>
<p>Hasło: owner</p>