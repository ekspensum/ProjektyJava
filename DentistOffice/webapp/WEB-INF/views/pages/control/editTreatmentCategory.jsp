<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h4>Edycja kategorii zabiegów</h4>
<form:form method="POST" modelAttribute="treatmentCategory" action="${pageContext.request.contextPath}/control/editTreatmentCategory">
<table>
	<tr>
		<td>Nazwa kategorii</td>
		<td><form:input path="categoryName" size="40" /></td>
		<td><form:errors path="categoryName" class="msgError"/></td>
	</tr>
	<tr><td colspan="2"><br></td></tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Zapisz zmiany" class="navigateButton" ></td>
	</tr>
</table>
</form:form>