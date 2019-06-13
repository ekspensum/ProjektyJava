<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<h3>Strona logowania</h3>
<form name="loginForm" action="login" method="POST">
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
			<td><input value="Zaloguj" type="submit" name="submit" onclick="validateLoginForm()" /></td>
		</tr>
	</table>
</form>
<h3>${msg }</h3>
<h3>${error }</h3>