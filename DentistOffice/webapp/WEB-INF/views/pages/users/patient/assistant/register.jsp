<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3><s:message code="register.header.patient.assistant" /></h3>
	<form:form method="POST"
		action="${pageContext.request.contextPath}/users/patient/assistant/register"
		modelAttribute="patient" enctype="multipart/form-data">
		<table>
			<tbody>
				<tr>
					<td><s:message code="register.user.login" /></td>
					<td><form:input path="user.username" id="username" /></td>
					<td class="msgError" ><form:errors path="user.username" />${distinctLoginError }</td>
				</tr>
				<tr>
					<td><s:message code="register.user.password" /></td>
					<td><form:input path="user.passwordField" id="password"	type="password" /></td>
					<td><form:errors path="user.passwordField" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.password.repet" /></td>
					<td><form>
						<input type="password" name="password2" id="password2" value="${param.password2 }">
					</form></td>
				</tr>
				<tr>
					<td><s:message code="register.user.firstName" /></td>
					<td><form:input path="firstName" id="firstName" /></td>
					<td><form:errors path="firstName" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.lastName" /></td>
					<td><form:input path="lastName" id="lastName" /></td>
					<td><form:errors path="lastName" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.pesel" /></td>
					<td><form:input path="pesel" id="pesel" /></td>
					<td><form:errors path="pesel" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.country" /></td>
					<td><form:input path="country" id="country" /></td>
					<td><form:errors path="country" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.zipCode" /></td>
					<td><form:input path="zipCode" id="zipCode" /></td>
					<td><form:errors path="zipCode" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.city" /></td>
					<td><form:input path="city" id="city" /></td>
					<td><form:errors path="city" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.street" /></td>
					<td><form:input path="street" id="street" /></td>
					<td><form:errors path="street" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.streetNo" /></td>
					<td><form:input path="streetNo" id="streetNo" /></td>
					<td><form:errors path="streetNo" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.unitNo" /></td>
					<td><form:input path="unitNo" id="unitNo" /></td>
					<td><form:errors path="unitNo" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.email" /></td>
					<td><form:input path="email" id="email" /></td>
					<td><form:errors path="email" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.phone" /></td>
					<td><form:input path="phone" id="phone" /></td>
					<td><form:errors path="phone" class="msgError" /></td>
				</tr>
				<tr>
					<td><s:message code="register.user.photo" /></td>
					<td><form:input type="file" name="photo" accept="image/*" path="photo" /></td>
					<td><form:errors path="photo" class="msgError" /></td>
				</tr>
				<tr>
					<td></td><td><input type="submit" value="<s:message code="button.register" />" onclick="return checkCorrectPassword()" class="navigateButton" /></td>
				</tr>
			</tbody>
		</table>
</form:form>
