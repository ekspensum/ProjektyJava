<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel asystenta</h3>

<p>Wybierz pacjenta do edycji danych:</p>
<form method="POST" action="${pageContext.request.contextPath}/users/patient/assistant/selectToEdit">
<select name="patientId">
	<c:forEach items="${allPatientsList }" var="patient">
		<option value="${patient.id }">${patient.firstName } ${patient.lastName }</option>
	</c:forEach>
</select>

	<input type="submit" value="Edytuj dane pacjenta">
</form>
