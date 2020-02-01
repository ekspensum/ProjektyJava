<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Panel administratora - wybór lekarza do edycji danych.</h3>

<p>Wybierz lekarza do edycji danych:</p>
<form method="POST" action="${pageContext.request.contextPath}/users/doctor/admin/selectToEdit">
<select name="doctorId">
	<c:forEach items="${allDoctorsList }" var="doctors">
		<option value="${doctors.id }">${doctors.firstName } ${doctors.lastName }</option>
	</c:forEach>
</select>

	<input type="submit" value="Edytuj dane lekarza" class="navigateButton" />
</form>
