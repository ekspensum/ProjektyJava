<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie klienta</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<c:if test="${SessionData != null }">
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
</c:if>
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<c:if test="${SessionData.idRole != 2 }">
<form action="/ShopAppWeb/AddCustomer" method="POST" name="loginForm" >
<p id="loginAddCustomer">Login: <input type="text" name="login" value="${RequestAttribute.login }"/></p>
<p id="passwordAddCustomer">Hasło: <input type="password" name="password" value="${RequestAttribute.password }" /></p>
<p id="firstName">Imię: <input type="text" name="firstName" value="${RequestAttribute.firstName }" /></p>
<p id="lastName">Nazwisko: <input type="text" name="lastName" value="${RequestAttribute.lastName }"/></p>
<p id="pesel">Pesel: <input type="text" name="pesel" value="${RequestAttribute.pesel }"/></p>
<p id="country">Kraj: <input type="text" name="country" value="${RequestAttribute.country }"/></p>
<p id="zipCode">Kod pocztowy: <input type="text" name="zipCode" value="${RequestAttribute.zipCode }"/></p>
<p id="city">Miasto: <input type="text" name="city" value="${RequestAttribute.city }"/></p>
<p id="street">Ulica: <input type="text" name="street" value="${RequestAttribute.street }"/></p>
<p id="streetNo">Nr domu: <input type="text" name="streetNo" value="${RequestAttribute.streetNo }"/></p>
<p id="unitNo">Nr lokalu: <input type="text" name="unitNo" value="${RequestAttribute.unitNo }"/></p>
<p id="email">E-mail: <input type="email" name="email" value="${RequestAttribute.email }"/></p>
<p id="isCompany">Czy firma?: <input type="checkbox" name="isCompany" value="yes" ${RequestAttribute.isCompany }/></p>
<p id="compnyName">Nazwa firmy: <input type="text" name="compnyName" value="${RequestAttribute.compnyName }"/></p>
<p id="taxNo">NIP: <input type="text" name="taxNo" value="${RequestAttribute.taxNo }"/></p>
<p id="regon">REGON: <input type="text" name="regon" value="${RequestAttribute.regon }"/></p>
<p id="buttonAddCustomer"><input type="submit" value="Dodaj" name="buttonAddCustomer"/></p>
</form>
</c:if>
<c:if test="${SessionData != null }">
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
<b id="message">${message }</b>
</body>
</html>