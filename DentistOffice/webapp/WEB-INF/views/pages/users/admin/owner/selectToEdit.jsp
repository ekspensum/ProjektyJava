<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h4>Wybierz administratora do edycji danych:</h4>
<form method="POST" action="${pageContext.request.contextPath}/users/admin/owner/selectToEdit">
<select name="adminId">
	<c:forEach items="${allAdminsList }" var="admin">
		<option value="${admin.id }">${admin.firstName } ${admin.lastName }</option>
	</c:forEach>
</select>

	<input type="submit" value="Edytuj administratora" class="navigateButton" ${allAdminsList.size() == 0 ? 'disabled' : '' } />
</form>
