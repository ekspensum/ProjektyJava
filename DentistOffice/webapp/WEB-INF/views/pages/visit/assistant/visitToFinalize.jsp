<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel asystenta - finalizacja wizyty pacjenta:</h3>
<table border="1">
	<tr><td rowspan="12"><img src="data:image;base64,${visit.patient.base64Photo }" width="300px" /></td></tr>
	<tr><td>Imię:</td><td>${visit.patient.firstName }</td>
	<tr><td>Nazwisko:</td><td>${visit.patient.lastName }</td></tr>
	<tr><td>Pesel:</td><td>${visit.patient.pesel }</td></tr>
	<tr><td>Kraj:</td><td>${visit.patient.country }</td></tr>
	<tr><td>Kod pocztowy:</td><td>${visit.patient.zipCode }</td></tr>
	<tr><td>Miasto:</td><td>${visit.patient.city }</td></tr>
	<tr><td>Ulica:</td><td>${visit.patient.street }</td></tr>
	<tr><td>Nr domu:</td><td>${visit.patient.streetNo }</td></tr>
	<tr><td>Nr lokalu</td><td>${visit.patient.unitNo }</td></tr>
	<tr><td>Telefon:</td><td>${visit.patient.phone }</td></tr>
	<tr><td>Email:</td><td>${visit.patient.email }</td></tr>
</table>
<br>
<table border="1">
<c:forEach items="${visit.treatments }" var="treatment" varStatus="loop">
	<tr><td>${loop.count}</td><td>${treatment.name}</td><td>${treatment.description}</td></tr>
</c:forEach>
</table>