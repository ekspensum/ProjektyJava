<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Wszystkie zadania:</h3>
<form method="POST" id="sortForm">
	<table class="tableHomePage">
		<thead>
			<tr>
				<th>Id</th>
				<th><button type="submit" name="sortBy" value="subjectAscending" form="sortForm"><img alt="" src='<c:url value="/static/image/iconfinder_BT_sort_az_905641.png" />'></button>
				Subject
				<button type="submit" name="sortBy" value="subjectDescending" form="sortForm"><img alt="" src='<c:url value="/static/image/iconfinder_BT_sort_za_905640.png" />'></button></th>
				<th>Date start</th>
				<th>Date end</th>
				<th><button type="submit" name="sortBy" value="taskNoAscending"	form="sortForm"><img alt="" src='<c:url value="/static/image/iconfinder_BT_sort_09_905643.png" />'></button>
				Task No
				<button type="submit" name="sortBy" value="taskNoDescending" form="sortForm"><img alt="" src='<c:url value="/static/image/iconfinder_BT_sort_90_905642.png" />'></button></th>
				<th>Image</th>
			</tr>
		</thead>
		<c:forEach items="${taskList }" var="t">
			<tbody>
				<tr>
					<td>${t.id }</td>
					<td>${t.subject }</td>
					<td>${t.dateTimeStart }</td>
					<td>${t.dateTimeEnd }</td>
					<td>${t.taskNo }</td>
					<td><img src="data:image;base64,${t.base64Image }"
						height="100px" width="150px" /></td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
</form>
