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
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p> 
<form action="/ShopAppWeb/AddCustomer" method="POST" >
<p>Login: <input type="text" name="login" /></p>
<p>Hasło: <input type="password" name="password" /></p>
<p>Imię: <input type="text" name="firstName" /></p>
<p>Nazwisko: <input type="text" name="lastName" /></p>
<p>Pesel: <input type="text" name="pesel" /></p>
<p>Kraj: <input type="text" name="country" /></p>
<p>Kod pocztowy: <input type="text" name="zipCode" /></p>
<p>Miasto: <input type="text" name="city" /></p>
<p>Ulica: <input type="text" name="street" /></p>
<p>Nr domu: <input type="text" name="streetNo" /></p>
<p>Nr lokalu: <input type="text" name="unitNo" /></p>
<p>E-mail: <input type="email" name="email" /></p>
<p>Czy firma?: <input type="checkbox" name="isCompany" value="yes" /></p>
<p>Nazwa firmy: <input type="text" name="compnyName" /></p>
<p>NIP: <input type="text" name="taxNo" /></p>
<p>REGON: <input type="text" name="regon" /></p>
<input type="submit" value="Dodaj">
</form>
</body>
</html>