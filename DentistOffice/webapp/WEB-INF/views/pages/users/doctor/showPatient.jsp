<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel lekarza - przegląd danych pacjenta</h3>
<table border="1" style="width:50%">
	<tr><td>Dane pacjenta:</td><td>${patient.firstName }</td><td colspan="2">${patient.lastName }</td>
	<td rowspan="5"><img src="data:image;base64,${patient.base64Photo }" width="300px" /></td></tr>
	<tr><td>Pesel:</td><td colspan="3">${patient.pesel }</td></tr>
	<tr><td>Adres zamieszkania:</td><td>${patient.country }</td><td>${patient.zipCode }</td><td>${patient.city }</td></tr>
	<tr><td>Ulica:</td><td>${patient.street }</td><td>${patient.streetNo }</td><td>${patient.unitNo }</td></tr>
	<tr><td>Telefon:</td><td>${patient.phone }</td><td>Email:</td><td>${patient.email }</td></tr>
</table>
<br>
<form method="POST" action="${pageContext.request.contextPath}/users/doctor/showPatient">
	<select name="statusId">
		<c:forEach items="${visitStatusList }" var="status">
			<option value="${status.id }" ${defaultVisitStatus.id == null ? param['statusId'] == status.id ? 'selected' : '' : defaultVisitStatus.id == status.id ? 'selected' : '' } >${status.description }</option>
		</c:forEach>
	</select>
	<input type="submit" value="Pokaż" class="navigateButton" />
</form>
<br>
<p><b>Staus poniższych wizyt: ${defaultVisitStatus.id != null ? defaultVisitStatus.description : actualVisitStatus.description} </b></p>
<table border="1" style="width:50%">
	<thead>
		<tr><th>Lp.</th><th>Data i godz.</th><th colspan="2">Dane lekarza</th><th>Nazwa zabiegu</th><th>Opis zabiegu</th></tr>
	</thead>
	<c:forEach items="${visitsByPatientAndStatus }" var="visit"  varStatus="loop">
			<tbody>
				<tr><td>${loop.count}</td><td>${visit.visitDateTime}</td><td>${visit.doctor.firstName }</td><td>${visit.doctor.lastName }</td><td></td><td></td></tr>
					<c:forEach items="${visit.visitTreatmentComment }" var="treat" varStatus="loop2">
						<c:if test="${treat.treatment.id != 1 }">
							<tr><td>${loop.count}.${loop2.count}</td><td></td><td></td><td></td><td>${treat.treatment.name}</td><td>${treat.comment}</td></tr>
						</c:if>
					</c:forEach>			
			</tbody>
	</c:forEach>
</table>
<p class="msg">${visitsByPatientAndStatus.size() == 0 ? 'Brak wizyt o wybranym statusie!' : '' }</p>