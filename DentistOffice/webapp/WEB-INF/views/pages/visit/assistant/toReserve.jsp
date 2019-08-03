<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Rezerwacja wizyty - wybór terminu i rodzaju zabiegów.</h3>
<h4>Pacjent: ${patient.firstName} ${patient.lastName}</h4>
<h4>Lekarz stomatolog: ${doctor.firstName} ${doctor.lastName}</h4>

<form name="selectVisitDateForm" method="POST" action="${pageContext.request.contextPath}/visit/assistant/reservation">

<button type="submit" name="weekResultDriver" formmethod="POST" formaction="${pageContext.request.contextPath}/visit/assistant/toReserve" value="stepLeft" ${disableLeftArrow eq 'YES' ? 'disabled' : '' } class="navigateButton" >Poprzednie 7 dni</button>
<button type="submit" name="weekResultDriver" formmethod="POST" formaction="${pageContext.request.contextPath}/visit/assistant/toReserve" value="stepRight" ${disableRightArrow eq 'YES' ? 'disabled' : '' } class="navigateButton" >Następne 7 dni</button>
<br><br>
<select name="treatment1">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }" ${param['treatment1'] == treat.id ? 'selected' : ''} >${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment2">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }" ${param['treatment2'] == treat.id ? 'selected' : ''} >${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br>
<select name="treatment3">
	<c:forEach items="${treatments}" var="treat">
		<option value="${treat.id }" ${param['treatment3'] == treat.id ? 'selected' : ''} >${treat.name } ${treat.price }</option>
	</c:forEach>
</select>
<br><br>
<table border="1" class="workingTime">
	<c:forEach items="${workingWeekFreeTimeMap}" var="map" varStatus="vs1">
		<tr><th colspan="8">${map.key } ${dayOfWeekPolish[map.key.dayOfWeek.value] }</th></tr>
		<c:if test="${map.value.size() == 0 }">
			<tr><td colspan="8" style="text-align: left;">
				Brak wolnych terminów na ten dzień.
			</td></tr>
		</c:if>
		<tr>
		<c:forEach items="${map.value }" var="time" varStatus="vs2">
			<td>	
				<input type="text" name="time" readonly="readonly" value="${time.key }" class="inputTime" />
				<input type="checkbox" name="dateTime" value="${map.key};${time.key}" />
			</td>
			${vs2.count % 8 == 0 ? '</tr>' : '' }
		</c:forEach>
	</c:forEach>
</table>
<br>
<table>
	<tr>
		<td align="right" width="820px"><input type="submit" value="Zaplanuj wizytę" onclick="return validateSelectChbxVisitDate()" class="navigateButton" /></td>
	</tr>
</table>
<br>
</form>
