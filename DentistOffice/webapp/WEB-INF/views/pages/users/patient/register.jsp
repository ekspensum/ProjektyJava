<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Rejestracja pacjenta</h3>
	<form:form method="POST"
		action="${pageContext.request.contextPath}/users/patient/register"
		modelAttribute="patient" enctype="multipart/form-data">
		<table>
			<tbody>
				<tr>
					<td>Login:</td>
					<td><form:input path="user.username" id="username" /></td>
					<td class="msgError" ><form:errors path="user.username" />${distinctLoginError }</td>
				</tr>
				<tr>
					<td>Hasło:</td>
					<td><form:input path="user.passwordField" id="password"	type="password" /></td>
					<td><form:errors path="user.passwordField" class="msgError" /></td>
				</tr>
				<tr>
					<td>Powtórz hasło:</td>
					<td><form>
						<input type="password" name="password2" id="password2" value="${param.password2 }">
					</form></td>
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
					<td>Kraj:</td>
					<td><form:input path="country" id="country" /></td>
					<td><form:errors path="country" class="msgError" /></td>
				</tr>
				<tr>
					<td>Kod pocztowy:</td>
					<td><form:input path="zipCode" id="zipCode" /></td>
					<td><form:errors path="zipCode" class="msgError" /></td>
				</tr>
				<tr>
					<td>Miasto:</td>
					<td><form:input path="city" id="city" /></td>
					<td><form:errors path="city" class="msgError" /></td>
				</tr>
				<tr>
					<td>Ulica:</td>
					<td><form:input path="street" id="street" /></td>
					<td><form:errors path="street" class="msgError" /></td>
				</tr>
				<tr>
					<td>Nr domu:</td>
					<td><form:input path="streetNo" id="streetNo" /></td>
					<td><form:errors path="streetNo" class="msgError" /></td>
				</tr>
				<tr>
					<td>Nr lokalu:</td>
					<td><form:input path="unitNo" id="unitNo" /></td>
					<td><form:errors path="unitNo" class="msgError" /></td>
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
					<td></td><td><input type="submit" value="Zarejestruj" class="navigateButton" onclick="return checkCorrectPassword()" /></td>
				</tr>
			</tbody>
		</table>
</form:form>
