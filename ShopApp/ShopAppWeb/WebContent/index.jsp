<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sklep internetowy</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" media="all" />
</head>
<body>
<p id="pageHeader">Witamy w sklepie internetowym</p>
<c:if test="${SessionData != null }">
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
</c:if>
<a href="/ShopAppWeb/AddCustomer" id="registrationButtonId" class="buttonClass">Rejestracja</a><br/>
<form action="/ShopAppWeb/LoginServlet" method="POST" name="loginForm" >
<p id="login">Login: <input type="text" name="login"/></p>
<p id="password">Hasło: <input type="password" name="password" /></p>
<p id="loginButtonId"><input  type="submit" name="loginButton" value="Zaloguj"  /></p>
</form>
<b id="message">${message }</b>
</body>
</html>