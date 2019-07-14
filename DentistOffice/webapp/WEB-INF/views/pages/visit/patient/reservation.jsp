<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h1>Visit page - select visit parameters</h1>

<form method="POST" action="${pageContext.request.contextPath}/visit/patient/reservation">
<select name="treatment">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>


<input type="submit" />
</form>