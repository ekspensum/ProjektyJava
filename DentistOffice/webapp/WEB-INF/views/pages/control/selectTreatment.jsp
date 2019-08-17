<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h4>Wybierz zabieg do edycji:</h4>
<form name="selectTreatmentForm" id="selectTreatmentForm" >
<table border="1" class="search">
	<thead>
		<tr>
			<th>Lp.</th>
			<th>Nazwa zabiegu</th>
			<th>Opis zabiegu</th>
			<th>Cena</th>
			<th>Zaznacz</th>
		</tr>
	</thead>
	<c:forEach items="${searchedTreatmentList }" var="treatment" varStatus="loop">
		<tbody>
			<tr>
				<td>${loop.count }</td>
				<td>${treatment.name }</td>
				<td style="text-align: left;">${treatment.description }</td>
				<td>${treatment.price }</td>
				<td><input type="checkbox" name="treatmentId" value="${treatment.id }"/></td>
			</tr>
		</tbody>
	</c:forEach>
</table>
<h3 class="msg">${searchedTreatmentList.size() == 0 ? 'Brak wyników wyszukiwania.' : '' }</h3>
<button form="selectTreatmentForm" type="submit" formmethod="POST" formaction="${pageContext.request.contextPath}/control/selectedTreatmentToEdit" ${searchedTreatmentList.size() == 0 ? 'disabled' : '' } onclick="return validateSelectChbxTreatment()" class="navigateButton">Edytuj zabieg</button>
</form>
