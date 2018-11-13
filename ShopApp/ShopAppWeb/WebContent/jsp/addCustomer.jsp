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
<form action="/ShopAppWeb/AddCustomer" method="POST" name="loginForm" >
<p id="loginAddCustomer">Login: <input type="text" name="login" /></p>
<p id="passwordAddCustomer">Hasło: <input type="password" name="password" /></p>
<p id="firstName">Imię: <input type="text" name="firstName" /></p>
<p id="lastName">Nazwisko: <input type="text" name="lastName" /></p>
<p id="pesel">Pesel: <input type="text" name="pesel" /></p>
<p id="country">Kraj: <input type="text" name="country" /></p>
<p id="zipCode">Kod pocztowy: <input type="text" name="zipCode" /></p>
<p id="city">Miasto: <input type="text" name="city" /></p>
<p id="street">Ulica: <input type="text" name="street" /></p>
<p id="streetNo">Nr domu: <input type="text" name="streetNo" /></p>
<p id="unitNo">Nr lokalu: <input type="text" name="unitNo" /></p>
<p id="email">E-mail: <input type="email" name="email" /></p>
<p id="isCompany">Czy firma?: <input type="checkbox" name="isCompany" value="yes" /></p>
<p id="compnyName">Nazwa firmy: <input type="text" name="compnyName" /></p>
<p id="taxNo">NIP: <input type="text" name="taxNo" /></p>
<p id="regon">REGON: <input type="text" name="regon" /></p>
<p id="buttonAddCustomer"><input type="submit" value="Dodaj" /></p>
</form>
</body>
</html>