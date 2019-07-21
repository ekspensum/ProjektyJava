<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Doctor menu</h3>

<p><a href="${pageContext.request.contextPath}/users/doctor/searchPatient"><button class="button">Wyszukaj pacjenta</button></a></p>
<br>
<p><a href="${pageContext.request.contextPath}/users/doctor/edit"><button class="button">Edycja swoich danych</button></a></p>