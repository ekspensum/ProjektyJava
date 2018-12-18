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
<a ${SessionData != null ? 'href="http://localhost:8080/ShopAppWeb/LogOutServlet"' : '' }><button id="buttonLogOut" ${SessionData != null ? "" : "disabled" }>Wyloguj</button></a>
<a ${SessionData.idRole == 3 ? "" : 'href="http://localhost:8080/ShopAppWeb/CustomerPanel"' }><button id="registrationButtonId" ${SessionData.idRole == 3 ? "disabled" : "" } >${SessionData != null ? "Panel klienta" : "Rejestracja" }</button></a>
<b id="message">${message }</b>

<c:if test="${SessionData.idRole == 3 }">
<p><a href="/ShopAppWeb/OperatorPanel"> Do panelu operatora</a></p>
</c:if>

<form action="/ShopAppWeb/LoginServlet" method="POST" name="loginForm" >
<p id="login">Login: <input type="text" name="login"/></p>
<p id="password">Hasło: <input type="password" name="password" /></p>
<p id="loginButtonId"><input  type="submit" name="loginButton" value="Zaloguj"  /></p>
</form>

<form action="/ShopAppWeb/ProductByCategory" method="POST" id="ProductByCategoryPlain">
<p id="optionHeader">Wszystkie produkty według kategorii:</p>
<c:forEach items="${catList}" var="cat" begin="1" end="4">
<p>${cat.name } <button type="submit" name="${cat.id }" value="${cat.id}" form="ProductByCategoryPlain"><img src="data:image;base64,${cat.base64Image}" height="50%" width="150"></button></p>
</c:forEach>
</form>

<form action="/ShopAppWeb/ProductByCategory" method="POST" id="ProductByCategoryFine">
<p id="optionHeader">Produkty wysokiej wydajności według kategorii:</p>
<c:forEach items="${catList}" var="cat" begin="5" end="8">
<p>${cat.name } <button type="submit" name="${cat.id }" value="${cat.id}" form="ProductByCategoryFine"><img src="data:image;base64,${cat.base64Image}" height="50%" width="150"></button></p>
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

<form action="/ShopAppWeb/LoginServlet" method="POST" id="searchFormMain">
<input type="text" name="searchProductInput" value="${param['searchProductInput'] }" />	<input type="submit" name="searchProductButton" value="Szukaj">
</form>

<form action="/ShopAppWeb/LoginServlet" method="POST" id="resultSearchFormMain">
<p><b>Wyniki wyszukiwania:</b></p>
<table>
<tr><th>Lp</th><th>Nazwa produktu</th><th>Dostępna ilość</th><th>Cana</th><th>Przejdz do szczegółów</th></tr>
<c:forEach items="${resultSearchProducts}" var="res" varStatus="lp" begin="0">
<tr><td>${lp.count }</td><td>${res.name }</td><td>${res.unitsInStock }</td><td>${res.price }</td><td><button type="submit" name="buttonToProductDetailsFromMain" value="${res.id }" formaction="/ShopAppWeb/ProductDetails">Szczegóły produktu</button></td></tr>
</c:forEach>
</table>
</form>

</body>
</html>