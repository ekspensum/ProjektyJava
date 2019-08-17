<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Nasi lekarze</h3>
<table class="homePageDoctors" >
	<c:forEach items="${allDoctors }" var="doctor">
		<tr class="homePageDoctors">
			<td align="center" class="homePageDoctorsName">${doctor.firstName }	${doctor.lastName }</td>
			<td rowspan="2" align="center" class="homePageDoctorsDescription"><textarea readonly="readonly" class="homePageDoctorsDescription">${doctor.description }</textarea></td>
		</tr>
		<tr class="homePageDoctors">
			<td align="center" class="homePageDoctorsPhoto"><img src="data:image;base64,${doctor.base64Photo }" style="height: 300px; max-width: 300px;" /></td>
		</tr>
		<tr><td colspan="2"><br></td>
		</tr>
	</c:forEach>
</table>