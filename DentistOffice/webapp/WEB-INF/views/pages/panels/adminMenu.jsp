<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Admin menu</h3>

	<p><a href="${pageContext.request.contextPath}/users/doctor/admin/register"><button class="sideMenuButton">Dodaj nowego lekarza</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/doctor/admin/selectToEdit"><button class="sideMenuButton">Edycja lekarza</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/assistant/admin/register"><button class="sideMenuButton">Dodaj nowego asystenta</button></a></p>
	<p><a href="${pageContext.request.contextPath}/users/assistant/admin/selectToEdit"><button class="sideMenuButton">Edycja asystenta</button></a></p>
	<p><a href="${pageContext.request.contextPath}/control/addTreatment"><button class="sideMenuButton">Dodaj nowy zabieg</button></a></p>
	<p><a href="${pageContext.request.contextPath}/control/searchTreatment"><button class="sideMenuButton">Edytuj zabieg</button></a></p>
	<p><a href="${pageContext.request.contextPath}/control/addTreatmentCategory"><button class="sideMenuButton">Nowa kategoria zabiegu</button></a></p>
	<p><a href="${pageContext.request.contextPath}/control/selectTreatmentCategory"><button class="sideMenuButton">Edytuj kategorię zabiegu</button></a></p>
	<br>
	<p><a href="${pageContext.request.contextPath}/control/indexing"><button class="sideMenuButton" style="font-size: 10px;">Aktualizuj indeksy bazy danych</button></a></p>
	<p><a href="${pageContext.request.contextPath}/control/adjusting"><button class="sideMenuButton" style="font-size: 10px;">Dopasuj generatory kluczy głównych w bazie danych</button></a></p>
	<br>
	<p><a href="${pageContext.request.contextPath}/users/admin/edit"><button class="sideMenuButton">Edytuj swoje dane</button></a></p>