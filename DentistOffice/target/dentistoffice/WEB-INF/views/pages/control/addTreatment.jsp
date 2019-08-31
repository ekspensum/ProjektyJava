<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h4>Dodaj nowy zabieg</h4>
<form:form method="POST" modelAttribute="dentalTreatment" action="${pageContext.request.contextPath}/control/addTreatment">
<table>
	<tr>
		<td>Nazwa zabiegu</td>
		<td><form:input path="name" style="min-width: 400px;" /></td>
		<td><form:errors path="name" class="msgError"/></td>
	</tr>
	<tr>
		<td>Opis zabiegu</td>
		<td><form:textarea path="description" class="textareaDescription"/></td>
		<td><form:errors path="description" class="msgError"/></td>
	</tr>
	<tr>
		<td>Cena zabiegu</td>
		<td><form:input path="price"/></td>
		<td><form:errors path="price" class="msgError"/></td>
	</tr>
	<tr>
		<td>Kategoria 1</td>
		<td><form:select path="treatmentCategory[0].id" multiple="false" items="${treatmentCategoriesList}" itemValue="id" itemLabel="categoryName" /></td>
	</tr>
	<tr>
		<td>Kategoria 2</td>
		<td><form:select path="treatmentCategory[1].id" multiple="false" items="${treatmentCategoriesList}" itemValue="id" itemLabel="categoryName" /></td>
		<td class="msgError">${categoryError}</td>
	</tr>
	<tr><td colspan="2"><br></td></tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Dodaj nowy zabieg" class="navigateButton" ></td>
	</tr>
</table>
</form:form>