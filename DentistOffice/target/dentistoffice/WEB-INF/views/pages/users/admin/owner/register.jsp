<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Rejestracja administratora</h3>
	<form:form method="POST"
		action="${pageContext.request.contextPath}/users/admin/owner/register"
		modelAttribute="admin" enctype="multipart/form-data">
		<table>
			<tbody>
				<tr>
					<td>Login:</td>
					<td><form:input path="user.username" id="username" /></td>
					<td class="msgError" ><form:errors path="user.username" />${distinctLoginError }</td>
				</tr>
				<tr>
					<td>Hasło:</td>
					<td><form:input path="user.passwordField" id="password"
							type="password" /></td>
					<td><form:errors path="user.passwordField" class="msgError" /></td>
				</tr>
				<tr>
					<td>Aktywny:</td>
					<td><form:checkbox path="user.enabled" checked="true" /></td>
					<td><form:errors path="user.enabled" class="msgError" /></td>
				</tr>
				<tr>
					<td>Rola 1:</td>
					<td><form:select path="user.roles[0].id"  multiple="false" >
						<form:option selected="true" value="5">Administrator</form:option>
					</form:select></td>
				</tr>
				<tr>
					<td>Rola 2:</td>
					<td><form:select path="user.roles[1].id" items="${rolesList}" itemValue="id" itemLabel="roleName" multiple="false" /></td>
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
					<td>PESEL:</td>
					<td><form:input path="pesel" id="pesel" /></td>
					<td><form:errors path="pesel" class="msgError" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><form:input path="email" id="email" /></td>
					<td><form:errors path="email" class="msgError" /></td>
				</tr>
				<tr>
					<td>Telefon:</td>
					<td><form:input path="phone" id="phone" /></td>
					<td><form:errors path="phone" class="msgError" /></td>
				</tr>
				<tr>
					<td>Zdjęcie:</td>
					<td><form:input type="file" name="photo" accept="image/*" path="photo" /></td>
					<td><form:errors path="photo" class="msgError" /></td>
				</tr>
				<tr>
					<td></td><td><input type="submit" value="Zarejestruj" class="navigateButton" /></td>
				</tr>
			</tbody>
		</table>
</form:form>
