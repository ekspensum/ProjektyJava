<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<h3>Rezerwacja wizyty - wybór terminu i rodzaju zabiegów.</h3>

<form method="POST" action="${pageContext.request.contextPath}/visit/assistant/reservation">
<select name="treatment1">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment2">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment3">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }">${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<h4>Pacjent: ${patient.firstName} ${patient.lastName}</h4>
<h4>Lekarz stomatolog: ${doctor.firstName} ${doctor.lastName}</h4>
<c:forEach items="${workingWeekFreeTimeMap}" var="map" varStatus="vs1">
	<b>${map.key } ${map.key.dayOfWeek.value == 1 ? 'Poniedziałek' : map.key.dayOfWeek.value == 2 ? 'Wtorek' : map.key.dayOfWeek.value == 3 ? 'Środa' : map.key.dayOfWeek.value == 4 ? 'Czwartek' : map.key.dayOfWeek.value == 5 ? 'Piątek' : map.key.dayOfWeek.value == 6 ? 'Sobota' : ''}</b>
	<c:if test="${map.value.size() == 0 }">
		Brak wolnych terminów na ten dzień.
	</c:if>
	<p>
	<c:forEach items="${map.value }" var="time" varStatus="vs2">	
			<input type="text" name="time" readonly="readonly" value="${time.key }" class="inputTime" />
			<input id="${vs1.count}${vs2.count}" type="checkbox" onclick="setValueOnInputFromChbx(this.id)" name="dateTime" value="${map.key};${time.key}" />&emsp;&emsp;
			${vs2.count % 8 == 0 ? '<br>' : '' }
	</c:forEach>
	</p>
</c:forEach>


<input type="submit" value="Zaplanuj wizytę" />
</form>