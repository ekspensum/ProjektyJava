<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Wolne terminy wizyt</h3>
<h4>Wybierz lekarza aby przeglądać jego wolne terminy wizyt:</h4>
<form id="homePageAgendaSelectDoctor">
	<table>
		<tr>
			<c:forEach items="${allDoctors }" var="doctor" varStatus="loop">
			<td>
				<table class="homePageAgenda">
					<tr class="">
						<td align="center" >${doctor.firstName }	${doctor.lastName }</td>
					</tr>
					<tr class="">
						<td align="center" rowspan="2" >
							<button type="submit" form="homePageAgendaSelectDoctor" formmethod="POST" formaction="${pageContext.request.contextPath}/agenda" name="doctorId" value="${doctor.id }" class="homePageAgenda">
								<img src="data:image;base64,${doctor.base64Photo }" class="homePageAgenda" />
							</button>
						</td>
					</tr>
					<tr><td><br></td></tr>
				</table>
			</td>
			<td width="20px"><br></td>
			${loop.count % 4 == 0 ? '<tr><td><br></td></tr></tr>' : '' }
			</c:forEach>
	</table>
	<c:if test="${workingWeekFreeTimeMap.size() > 0 }">
		<p><b>Wybrany lekarz: ${doctor.firstName }	${doctor.lastName }</b></p>
		<button type="submit" name="weekResultDriver" formmethod="POST" formaction="${pageContext.request.contextPath}/agenda" value="stepLeft" ${disableLeftArrow eq 'YES' ? 'disabled' : '' } class="navigateButton" >Poprzednie 7 dni</button>
		<button type="submit" name="weekResultDriver" formmethod="POST" formaction="${pageContext.request.contextPath}/agenda" value="stepRight" ${disableRightArrow eq 'YES' ? 'disabled' : '' } class="navigateButton" >Następne 7 dni</button>
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
					</td>
					${vs2.count % 8 == 0 ? '</tr>' : '' }
				</c:forEach>
			</c:forEach>
		</table>	
	</c:if>
</form>
<br>

