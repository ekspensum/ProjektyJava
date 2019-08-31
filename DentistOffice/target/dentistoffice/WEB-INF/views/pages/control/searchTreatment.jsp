<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h4>Wyszukaj zabieg do edycji:</h4>
<form name="searchTreatmentForm" method="POST" action="${pageContext.request.contextPath}/control/searchResult">
	<input type="search" name="treatmentData" placeholder="Wpisz całość bądź fragment nazwy lub opisu - max 20 znaków" size="70"/>
	<input type="submit" value="Wyszukaj zabieg" onclick="return validateInputFieldTreatmentData()" class="navigateButton" />
</form>
