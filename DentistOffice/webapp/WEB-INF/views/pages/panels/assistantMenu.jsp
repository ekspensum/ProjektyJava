<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Menu asystenta</h3>

<h4>Zaplanuj wizytę pacjenta:</h4>
<p><a href="${pageContext.request.contextPath}/visit/assistant/searchPatient"><button class="button">Wybierz pacjenta</button></a></p>
<br>
<h5>Dodaj/edytuj pacjenta:</h5>
<p><a href="${pageContext.request.contextPath}/users/patient/assistant/register"><button class="button">Dodaj nowego pacjenta</button></a></p>
<p><a href="${pageContext.request.contextPath}/users/patient/assistant/search"><button class="button">Edycja danych pacjenta</button></a></p>
<h5>Edytuj swoje dane:</h5>
<p><a href="${pageContext.request.contextPath}/users/assistant/edit"><button class="button">Edycja swoich danych</button></a></p>
