<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel lekarza</h3>

<p>Wyszukaj pacjenta do przeglądu danych:</p>
<form name="searchDataPatientForm" method="POST" action="${pageContext.request.contextPath}/users/doctor/searchResult">
	<input type="search" name="patientData" placeholder="Wpisz nazwisko lub pesel lub nr telefonu lub nazwę ulicy - max 20 znaków" size="70"/>
	<input type="submit" value="Wyszukaj pacjenta" onclick="return validateInputFieldPatientData()">
</form>
