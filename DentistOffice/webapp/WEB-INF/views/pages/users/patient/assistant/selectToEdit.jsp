<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel asystenta</h3>

<p>Wybierz pacjenta do edycji danych:</p>
<form name="selectPatientForm" method="POST" action="${pageContext.request.contextPath}/users/patient/assistant/selectToEdit">
<table border="1" class="search">
	<thead>
		<tr>
			<th>Lp.
			</th>
			<th>Imię
			</th>
			<th>Nazwisko
			</th>
			<th>PESEL
			</th>
			<th>Miasto
			</th>
			<th>Ulica
			</th>
			<th>Telefon
			</th>
			<th>Zaznacz
			</th>
		</tr>
	</thead>
	<c:forEach items="${searchedPatientList }" var="patient" varStatus="loop">
		<tbody>
			<tr>
				<td>${loop.count }
				</td>
				<td>${patient.firstName }
				</td>
				<td>${patient.lastName }
				</td>
				<td>${patient.pesel }
				</td>
				<td>${patient.city }
				</td>
				<td>${patient.street }
				</td>
				<td>${patient.phone }
				</td>
				<th><input type="checkbox" name="patientId" value="${patient.id }"/>
				</th>
			</tr>
		</tbody>
	</c:forEach>
</table>
<br>
	<input type="submit" value="Wybierz pacjenta" onclick="return validateSelectChbxPatient()" />
</form>
