<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie klienta</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<form action="/ShopAppWeb/AddPrivateCustomer" method="POST" >
<p>Imię: <input type="text" name="firstName" /></p>
<p>Nazwisko: <input type="text" name="lastName" /></p>
<p>Pesel: <input type="text" name="pesel"></p>
<p>Kod pocztowy: <input type="text" name="zipCode" /></p>
<p>Miasto: <input type="text" name="city" /></p>
<p>Ulica: <input type="text" name="street" /></p>
<p>Nr domu: <input type="text" name="streetNo" /></p>
<p>Nr lokalu: <input type="text" name="unitNo" /></p>
<input type="submit" value="Dodaj">
</form>
</body>
</html>