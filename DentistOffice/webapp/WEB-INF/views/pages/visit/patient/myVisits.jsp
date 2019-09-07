<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Moje wizyty</h3>
<form method="POST" action="${pageContext.request.contextPath}/visit/patient/myVisits" id="myVisitsForm">
	<select name="statusId">
		<c:forEach items="${visitStatusList }" var="status">
			<option value="${status.id }" ${defaultVisitStatus.id == null ? param['statusId'] == status.id ? 'selected' : '' : defaultVisitStatus.id == status.id ? 'selected' : '' } >${status.description }</option>
		</c:forEach>
	</select>
	<input type="submit" value="Pokaż" class="navigateButton" />
	<button type="submit" formmethod="POST" form="myVisitsForm" formaction="${pageContext.request.contextPath}/visit/patient/myVisitsPdf" class="navigateButton">Drukuj do PDF</button>
</form>
<br>
<p><b>Staus poniższych wizyt: ${defaultVisitStatus.id != null ? defaultVisitStatus.description : actualVisitStatus.description} </b></p>
<table border="1" style="width:70%">
	<thead>
		<tr><th>Lp.</th><th>Data i godz.</th><th colspan="2">Dane lekarza</th><th>Nazwa zabiegu</th><th>Cena</th><th></th></tr>
	</thead>
	<c:forEach items="${visitsByPatientAndStatus }" var="visit"  varStatus="loop">
			<tbody>
				<tr><td>${loop.count}</td><td>${visit.visitDateTime}</td><td>${visit.doctor.firstName }</td><td>${visit.doctor.lastName }</td><td></td><td></td>
				<td align="center">
				<c:if test="${visit.status.id == 1 }"><button form="myVisitsForm" type="submit" name="visitId" value="${visit.id }" formmethod="POST" formaction="${pageContext.request.contextPath}/visit/patient/removeVisit"  onclick="return confirmRemoveVisit()">Odwołaj wizytę</button></c:if>
				</td>
				</tr>
					<c:forEach items="${visit.visitTreatmentComment }" var="treat" varStatus="loop2">
						<c:if test="${treat.treatment.id != 1 }">
							<tr><td>${loop.count}.${loop2.count}</td><td></td><td></td><td></td><td>${treat.treatment.name}</td><td>${treat.treatment.price}</td></tr>
						</c:if>
					</c:forEach>			
			</tbody>
	</c:forEach>
</table>
<p class="msg">${visitsByPatientAndStatus.size() == 0 ? 'Brak wizyt o wybranym statusie!' : '' }</p>