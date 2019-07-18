<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h3>Rezerwacja wizyty - wybór pacjenta</h3>

<form method="POST" action="${pageContext.request.contextPath}/visit/assistant/selectPatient">
<select name="patientId">

	<option value="1">Patient 1</option>
	<option value="2">Patient 2</option>
	<option value="3">Patient 3</option>
	<option value="4">Patient 4</option>
</select>

<input type="submit" value="Dalej"/>
</form>