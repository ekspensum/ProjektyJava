<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Home</h3>

<form method="POST" action="${pageContext.request.contextPath}/visit">
<select name="doctor">
	<option value="1">Doctor 1</option>
	<option value="2">Doctor 2</option>
	<option value="3">Doctor 3</option>
</select>

<select name="weekDay">
	<option value="MONDAY">MONDAY</option>
	<option value="TUESDAY">TUESDAY</option>
	<option value="WEDNESDAY">WEDNESDAY</option>
	<option value="THURSDAY">THURSDAY</option>
	<option value="FRIDAY">FRIDAY</option>
</select>

	<button name="time" value="09:30">09:30</button>
	<button name="time" value="14:00">14:00</button>
	<button name="time" value="17:30">17:30</button>
	<button name="time" value="18:30">18:30</button>
</form>