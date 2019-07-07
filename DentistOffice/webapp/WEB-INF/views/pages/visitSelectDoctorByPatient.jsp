<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h1>Visit page - select doctor</h1>

<form method="POST" action="${pageContext.request.contextPath}/visitToReserveByPatient">
<select name="doctorId">
	<option value="1">Doctor 1</option>
	<option value="2">Doctor 2</option>
	<option value="3">Doctor 3</option>
</select>

<input type="submit" />
</form>