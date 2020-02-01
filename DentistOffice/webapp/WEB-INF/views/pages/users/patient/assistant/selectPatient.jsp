<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel asystenta</h3>
<h4>Wybierz pacjenta do edycji danych:</h4>
<form name="selectPatientForm" id="selectPatientForm" >
<table border="1" class="search">
	<thead>
		<tr>
			<th>Lp.</th>
			<th>Imię</th>
			<th>Nazwisko</th>
			<th>PESEL</th>
			<th>Miasto</th>
			<th>Ulica</th>
			<th>Telefon</th>
			<th>Zaznacz</th>
		</tr>
	</thead>
	<c:forEach items="${searchedPatientList }" var="patient" varStatus="loop">
		<tbody>
			<tr>
				<td>${loop.count }</td>
				<td>${patient.firstName }</td>
				<td>${patient.lastName }</td>
				<td>${patient.pesel }</td>
				<td>${patient.city }</td>
				<td>${patient.street }</td>
				<td>${patient.phone }</td>
				<td><input type="checkbox" name="patientId" value="${patient.id }"/></td>
			</tr>
		</tbody>
	</c:forEach>
</table>
<h3 class="msg">${searchedPatientList.size() == 0 ? 'Brak wyników wyszukiwania.' : '' }</h3>
<table style="width: 70%;">
<tr>
	<td align="left"><button form="selectPatientForm" type="submit" formmethod="POST" formaction="${pageContext.request.contextPath}/users/patient/assistant/selectedPatientToEdit" ${searchedPatientList.size() == 0 ? 'disabled' : '' } onclick="return validateSelectChbxPatient()" class="navigateButton">Edytuj dane pacjenta</button></td>
	<td align="right"><button form="selectPatientForm" type="submit" formmethod="POST" formaction="${pageContext.request.contextPath}/users/patient/assistant/selectedPatientToShow" ${searchedPatientList.size() == 0 ? 'disabled' : '' } onclick="return validateSelectChbxPatient()" class="navigateButton">Pokaż wizyty pacjenta</button></td>
</tr>
</table>
</form>
