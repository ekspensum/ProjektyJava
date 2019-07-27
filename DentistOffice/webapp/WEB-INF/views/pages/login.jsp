<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Strona logowania</h3>
<form name="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
	<table>
		<tr>
			<td>Użytkownik:</td>
			<td><input name="username" type="text" /></td>
		</tr>
		<tr>
			<td>Hasło:</td>
			<td><input name="password" type="password" /></td>
		</tr>
		<tr>
			<td></td><td align="right"><input type="submit" value="Zaloguj" onclick="return validateLoginForm()" class="navigateButton" style="width: 100px;" /></td>
		</tr>
	</table>
</form>
<h3>${msg }</h3>
<h3>${error }</h3>