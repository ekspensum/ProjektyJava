<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel administratora</h3>

<p>Wybierz asystenta do edycji danych:</p>
<form method="POST" action="${pageContext.request.contextPath}/users/assistant/admin/selectToEdit">
<select name="assistantId">
	<c:forEach items="${allAssistantsList }" var="assistants">
		<option value="${assistants.id }">${assistants.firstName } ${assistants.lastName }</option>
	</c:forEach>
</select>

	<input type="submit" value="Edytuj dane asystenta">
</form>
