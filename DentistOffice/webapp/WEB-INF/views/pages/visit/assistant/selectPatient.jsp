<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h1>Visit page - select patient</h1>

<form method="POST" action="${pageContext.request.contextPath}/visit/assistant/selectDoctor">
<select name="patientId">

	<option value="1">Patient 1</option>
	<option value="2">Patient 2</option>
	<option value="3">Patient 3</option>
	<option value="4">Patient 4</option>
</select>

<input type="submit" />
</form>