<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Usługi dentystyczne oraz cennik</h3>

<form method="POST" action="${pageContext.request.contextPath}/services">
	<select name="categoryId">
		<c:forEach items="${treatmentCategoriesList }" var="category">
			<option value="${category.id }" ${param['categoryId'] == category.id ? 'selected' : '' }>${category.categoryName }</option>
		</c:forEach>
	</select>
	<input type="submit" value="Wyszukaj zabiegi" class="navigateButton" />
</form>
<c:if test="${selectedTreatmentCategory.dentalTreatment.size() > 0 && param['categoryId'] != 1 }">
<h4>${selectedTreatmentCategory.categoryName }:</h4>
	<table class="search">
		<tr><th>Lp.</th><th>Nazwa zabiegu</th><th>Cena</th><th>Opis zabiegu</th></tr>
		<c:forEach items="${selectedTreatmentCategory.dentalTreatment }" var="treatment" varStatus="loop">
			<tr>
				<td>${loop.count }.</td><td>${treatment.name }</td><td>od	${treatment.price }</td><td style="width: 70%;"><textarea readonly="readonly" class="homePageTreatmentDescription">${treatment.description }</textarea></td></tr>
		
		</c:forEach>
	</table>
</c:if>
<br>