<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Admin menu</h3>

	<p><a href="${pageContext.request.contextPath}/users/doctor/admin/register"><button class="sideMenuButton">Dodaj nowego lekarza</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/doctor/admin/selectToEdit"><button class="sideMenuButton">Edycja lekarza</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/assistant/admin/register"><button class="sideMenuButton">Dodaj nowego asystenta</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/assistant/admin/selectToEdit"><button class="sideMenuButton">Edycja asystenta</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/admin/indexing"><button class="sideMenuButton">Aktualizuj indeksy bazy d.</button></a></p>