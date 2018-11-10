<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p> 
<form action="/ShopAppWeb/Test" method="POST" >
<p>Login: <input type="text" name="txtUserName" /></p>
<p>Hasło: <input type="password" name="txtPassword" /></p>
<input type="submit" value="OK">
</form>
</body>
</html>