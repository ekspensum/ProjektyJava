<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h1>Visit page - select visit parameters</h1>

<form method="POST" action="${pageContext.request.contextPath}/visitReservationByPatient">
<select name="treatment">
	<option value="0">treatment 1</option>
	<option value="1">treatment 2</option>
	<option value="2">treatment 3</option>
</select>
<select name="treatment">
	<option value="0">treatment 1</option>
	<option value="1">treatment 2</option>
	<option value="2">treatment 3</option>
</select>
<select name="treatment">
	<option value="0">treatment 1</option>
	<option value="1">treatment 2</option>
	<option value="2">treatment 3</option>
</select>
<br><br>
<c:forEach items="${datesList}" var="dl">
 ${dl } <input type="checkbox" name="date" value="${dl }" />	${dl.dayOfWeek }
 
 Time <input type="checkbox" name="time" value="18:00" /><br>
 
</c:forEach>




<input type="submit" />
</form>