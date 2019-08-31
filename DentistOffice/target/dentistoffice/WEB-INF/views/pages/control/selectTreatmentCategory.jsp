<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h4>Wybierz kategorię zabiegu do edycji:</h4>
<form method="POST" action="${pageContext.request.contextPath}/control/selectTreatmentCategory">
<select name="categoryId">
	<c:forEach items="${treatmentCategoriesList }" var="category">
		<option value="${category.id }">${category.categoryName }</option>
	</c:forEach>
</select>

	<input type="submit" value="Edytuj kategorię" class="navigateButton" />
</form>
