<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h4>Dodaj nową kategorię zabiegów</h4>
<form:form method="POST" modelAttribute="treatmentCategory" action="${pageContext.request.contextPath}/control/addTreatmentCategory">
<table>
	<tr>
		<td>Nazwa kategorii</td>
		<td><form:input path="categoryName" size="40" /></td>
		<td><form:errors path="categoryName" class="msgError"/></td>
	</tr>
	<tr><td colspan="2"><br></td></tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Dodaj nową kategorię" class="navigateButton" ></td>
	</tr>
</table>
</form:form>