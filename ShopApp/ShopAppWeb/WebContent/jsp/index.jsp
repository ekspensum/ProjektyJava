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
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
<c:if test="${SessionData.idRole == 3 }">
<p><a href="/ShopAppWeb/OperatorPanel"> Do panelu operatora</a></p>
</c:if>

<form action="/ShopAppWeb/LoginServlet" method="POST" name="loginForm" >
<p id="login">Login: <input type="text" name="login"/></p>
<p id="password">Hasło: <input type="password" name="password" /></p>
<p id="loginButtonId"><input  type="submit" name="loginButton" value="Zaloguj"  /></p>
<button type="submit" formaction="/ShopAppWeb/AddCustomer" id="registrationButtonId">Rejestracja</button>
</form>

<b id="message">${message }</b>

<form action="/ShopAppWeb/ProductByCategory" method="POST" id="ProductByCategoryPlain">
<p id="optionHeader">Wszystkie produkty według kategorii:</p>
<c:forEach items="${catList}" var="cat" begin="1" end="4">
<p>${cat.name } <button type="submit" name="${cat.id }" value="${cat.id}" form="ProductByCategoryPlain"><img src="data:image;base64,${cat.base64Image}"></button></p>
</c:forEach>
</form>

<form action="/ShopAppWeb/ProductByCategory" method="POST" id="ProductByCategoryFine">
<p id="optionHeader">Produkty wysokiej wydajności według kategorii:</p>
<c:forEach items="${catList}" var="cat" begin="5" end="8">
<p>${cat.name } <button type="submit" name="${cat.id }" value="${cat.id}" form="ProductByCategoryFine"><img src="data:image;base64,${cat.base64Image}"></button></p>
</c:forEach>
</form>

<c:if test="${SessionData != null && SessionData.idRole == 2}">
<form action="/ShopAppWeb/LoginServlet" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table>
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość koszyka: ${ total }</b></p>
<button type="submit" name="buttonDeleteRowBasket" value="${pl.id }" ${ total > 0 ? "" : "disabled" } >Usuń zazanaczone</button><br><br>
<button type="submit" formaction="/ShopAppWeb/Transaction" ${ total > 0 ? "" : "disabled" } >Dokonaj zakupu</button>
</form>
</c:if>

</body>
</html>