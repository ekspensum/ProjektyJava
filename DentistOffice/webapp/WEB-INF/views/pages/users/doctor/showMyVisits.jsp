<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel lekarza - przegląd zaplanowanych wizyt (najbliższe 30 dni)</h3>
<form method="POST" action="${pageContext.request.contextPath}/users/doctor/showMyVisits">
	<table>
		<tr>
			<td><input type="date" name="dateFrom" value="${dateFrom }" /></td>
			<td><input type="submit" value="Pokaż" class="navigateButton" /></td>
		</tr>
	</table>
</form>
<br>
<table border="1" style="width:50%">
	<thead>
		<tr><th>Lp.</th><th colspan="2">Data i godz. wizyty</th><th colspan="2">Dane pacjenta</th><th>Nazwa zabiegu</th></tr>
	</thead>
	<c:forEach items="${visitsToShow }" var="visit"  varStatus="loop">
			<tbody>
				<tr><td>${loop.count}</td><td>${visit.visitDateTime.toLocalDate()}</td><td>${visit.visitDateTime.toLocalTime()}</td><td>${visit.patient.firstName }</td><td>${visit.patient.lastName }</td><td></td></tr>
					<c:forEach items="${visit.treatments }" var="treat">
						<c:if test="${treat.id != 1 }">
							<tr><td></td><td></td><td></td><td></td><td></td><td>${treat.name}</td></tr>
						</c:if>
					</c:forEach>			
			</tbody>
	</c:forEach>
</table>
<br>
<p class="msg">${visitsToShow.size() == 0 ? 'Brak wizyt w wybranym czasie!' : '' }</p>