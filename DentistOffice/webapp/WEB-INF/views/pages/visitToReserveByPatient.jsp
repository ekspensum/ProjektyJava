<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h1>Visit page - select visit parameters</h1>

<form method="POST" action="${pageContext.request.contextPath}/visitReservationByPatient">
<select name="treatment">
	<option value="empty">Wybierz zabieg</option>
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment">
	<option value="empty">Wybierz zabieg</option>
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment">
	<option value="empty">Wybierz zabieg</option>
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<p>Lekarz stomatolog: ${doctor.firstName} ${doctor.lastName}</p>
<c:forEach items="${workingWeekFreeTimeMap}" var="map" varStatus="vs1">
	<b>${map.key } ${map.key.dayOfWeek.value == 1 ? 'Poniedziałek' : map.key.dayOfWeek.value == 2 ? 'Wtorek' : map.key.dayOfWeek.value == 3 ? 'Środa' : map.key.dayOfWeek.value == 4 ? 'Czwartek' : map.key.dayOfWeek.value == 5 ? 'Piątek' : map.key.dayOfWeek.value == 6 ? 'Sobota' : ''}</b>
	<p>
	<c:forEach items="${map.value }" var="time" varStatus="vs2">	
			<input type="text" name="${fn:toLowerCase(map.key)}Time" readonly="readonly" value="${time.key }" />
			<input id="in${vs1.count}${vs2.count }" type="hidden" name="${fn:toLowerCase(map.key)}TimeBool" value="${time.value }" />
			<input id="${vs1.count}${vs2.count}" type="checkbox" onclick="setValueOnInputFromChbx(this.id)" />&emsp;&emsp;
			${vs2.count % 6 == 0 ? '<br>' : '' }
	</c:forEach>
	</p>
</c:forEach>


<input type="submit" value="Zaplanuj wizytę" />
</form>