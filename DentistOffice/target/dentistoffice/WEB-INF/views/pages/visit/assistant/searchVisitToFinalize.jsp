<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Finalizacja wizyty</h3>
<form name="selectVisitForm" method="POST" action="${pageContext.request.contextPath}/visit/assistant/visitToFinalize">
<table>
	<tr>
		<td><input type="date" name="dateFrom" value="${dateFrom }" /></td>
		<td><input type="date" name="dateTo" value="${dateTo }" readonly="readonly" /></td>
		<td><button type="submit" formmethod="POST" formaction="${pageContext.request.contextPath}/visit/assistant/searchVisitToFinalize" class="navigateButton" onclick="return validateInputFieldsDateSearchVisits()" >Wyszukaj ponownie</button></td>
	</tr>
</table>
<br>
<table border="1" class="search">
	<thead>
		<tr>
			<th>Lp.
			</th>
			<th colspan="2">Imię i nazwisko pcjenta
			</th>
			<th>PESEL pacjenta
			</th>
			<th colspan="2">Imię i nazwisko lekarza
			</th>
			<th colspan="2">Data i czas wizyty
			</th>
			<th>Zaznacz
			</th>
		</tr>
	</thead>
	<c:forEach items="${visitsToFinalize }" var="visit" varStatus="loop">
		<tbody>
			<tr>
				<td>${loop.count }
				</td>
				<td>${visit.patient.firstName }
				</td>
				<td>${visit.patient.lastName }
				</td>
				<td>${visit.patient.pesel }
				</td>
				<td>${visit.doctor.firstName }
				</td>
				<td>${visit.doctor.lastName }
				</td>
				<td>${visit.visitDateTime.toLocalDate() } 
				</td>
				<td>${visit.visitDateTime.toLocalTime() }
				</td>
				<th><input type="checkbox" name="visitId" value="${visit.id }"/>
				</th>
			</tr>
		</tbody>
	</c:forEach>
</table>
<h3 class="msg">${visitsToFinalize.size() == 0 ? 'Brak wyników wyszukiwania.' : '' }</h3>
<input type="submit" value="Wybierz wizytę" ${visitsToFinalize.size() == 0 ? 'disabled' : '' } onclick="return validateSelectChbxVisit()" class="navigateButton" />
</form>
<br><br>