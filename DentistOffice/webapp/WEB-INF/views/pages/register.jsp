<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Rejestruj nowego użytkownika</h3>
<form:form method="POST"
	action="${pageContext.request.contextPath}/register"
	modelAttribute="user">
	<table>
		<tbody>
			<tr>
				<td>Login:</td>
				<td><form:input path="username" id="username" /></td>
				<td><form:errors path="username" class="msgError" /></td>
			</tr>
			<tr>
				<td>Hasło:</td>
				<td><form:input path="passwordField" id="password" type="password" /></td>
				<td><form:errors path="passwordField" class="msgError" /></td>
			</tr>
			<tr>
				<td>Imię:</td>
				<td><form:input path="firstName" id="firstName" /></td>
				<td><form:errors path="firstName" class="msgError" /></td>
			</tr>
			<tr>
				<td>Nazwisko:</td>
				<td><form:input path="lastName" id="lastName" /></td>
				<td><form:errors path="lastName" class="msgError" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><form:input path="email" id="email" /></td>
				<td><form:errors path="email" class="msgError" /></td>
			</tr>
			<tr>
				<td>Aktywny:</td>
				<td><form:checkbox path="enabled" checked="true" /></td>
				<td><form:errors path="enabled" class="msgError" /></td>
			</tr>
			<tr>
				<td>Rola 1:</td>
				<td><select name="firstSelect" >
					<c:forEach items="${rolesList}" var="rl">
						<option value="${rl.id }" ${param['firstSelect'] == rl.id ? 'selected' : '' } >${rl.roleName }</option>
					</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>Rola 2:</td>
				<td><select name="secondSelect" >
					<c:forEach items="${rolesList}" var="rl">
						<option value="${rl.id }" ${param['secondSelect'] == rl.id ? 'selected' : '' } >${rl.roleName }</option>
					</c:forEach>
				</select></td>
				<td class="msgError">${msg }</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><input type="submit" value="Zarejestruj" /></td>
			</tr>
		</tbody>
	</table>
</form:form>
	<c:if test="${alert == 'YES' }">
		<script>
			userAdded();
		</script>
	</c:if>
	<c:if test="${alert == 'NO' }">
		<script>alert("Did not added new user!");</script>
	</c:if>
