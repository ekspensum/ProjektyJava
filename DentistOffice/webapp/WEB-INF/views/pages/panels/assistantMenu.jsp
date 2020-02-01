<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Menu asystenta</h3>
<h4>Wizyty pacjentów:</h4>
<p><a href="${pageContext.request.contextPath}/visit/assistant/searchPatient"><button class="sideMenuButton">Rezerwacja wizyty</button></a></p>
<p><a href="${pageContext.request.contextPath}/visit/assistant/searchVisitToFinalize"><button class="sideMenuButton">Wizyty do finalizacji</button></a></p>
<p><a href="${pageContext.request.contextPath}/visit/assistant/searchVisitToRemove"><button class="sideMenuButton">Odwołaj wizytę</button></a></p>
<h4>Dodaj/edytuj/pokaż pacjenta:</h4>
<p><a href="${pageContext.request.contextPath}/users/patient/assistant/register"><button class="sideMenuButton">Dodaj nowego pacjenta</button></a></p>
<p><a href="${pageContext.request.contextPath}/users/patient/assistant/searchPatient"><button class="sideMenuButton">Informacje o pacjencie</button></a></p>
<h4>Edytuj swoje dane:</h4>
<p><a href="${pageContext.request.contextPath}/users/assistant/edit"><button class="sideMenuButton">Edycja swoich danych</button></a></p>
