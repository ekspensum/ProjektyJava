<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel asystenta - finalizacja wizyty pacjenta:</h3>
<table border="1" style="width:50%">
	<tr><td rowspan="12" align="center"><img src="data:image;base64,${visit.patient.base64Photo }" width="300px" /></td></tr>
	<tr><td>Imię:</td><td>${visit.patient.firstName }</td>
	<tr><td>Nazwisko:</td><td>${visit.patient.lastName }</td></tr>
	<tr><td>Pesel:</td><td>${visit.patient.pesel }</td></tr>
	<tr><td>Kraj:</td><td>${visit.patient.country }</td></tr>
	<tr><td>Kod pocztowy:</td><td>${visit.patient.zipCode }</td></tr>
	<tr><td>Miasto:</td><td>${visit.patient.city }</td></tr>
	<tr><td>Ulica:</td><td>${visit.patient.street }</td></tr>
	<tr><td>Nr domu:</td><td>${visit.patient.streetNo }</td></tr>
	<tr><td>Nr lokalu</td><td>${visit.patient.unitNo }</td></tr>
	<tr><td>Telefon:</td><td>${visit.patient.phone }</td></tr>
	<tr><td>Email:</td><td>${visit.patient.email }</td></tr>
</table>
<br>
<table border="1" style="width:50%">
	<tr><td>Planowa data i czas wizyty:</td><td>${visit.visitDateTime.toLocalDate() }</td><td>Godz.:</td><td>${visit.visitDateTime.toLocalTime() }</td></tr>
	<tr><td>Lekarz:</td><td colspan="3">${visit.doctor.firstName } ${visit.doctor.lastName }</td>
	<tr><td>Obecny staus wizyty:</td><td colspan="3">${visit.status.description }</td></tr>	
</table>
<br>
<form  method="POST" action="${pageContext.request.contextPath}/visit/assistant/finalizedVisit">
	<table border="1" style="width:50%">
		<c:forEach items="${visit.treatments}" var="treat" varStatus="loop">
			<tr>
			<td>${loop.count }.</td>
			<td>
				<select name="treatment${loop.count }">
					<c:forEach items="${dentalTreatmentsList }" var="dtl">
						<option value="${dtl.id }" ${dtl.id == treat.id ? 'selected' : ''} >${dtl.name } ${dtl.price }</option>
					</c:forEach>
				</select>
			</td>
			<td align="center"><textarea name="treatmentCommentVisit${loop.count }"  rows="3" cols="60"></textarea></td>
			</tr>
		</c:forEach>
		<tr><td colspan="3"><br></td></tr>
		<tr><td colspan="3" align="right"><input type="submit" value="Finalizacja wizyty" class="navigateButton" ></td></tr>
	</table>
	<br>
</form>
