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
<p id="pageHeaderMain">Witamy w sklepie internetowym</p>
<c:if test="${SessionData != null }">
<p id="loginParagraph">Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
</c:if>
<a ${SessionData != null ? 'href="/ShopAppWeb/LogOutServlet"' : '' }><button id="buttonLogOut" class="button" ${SessionData != null ? "" : "disabled" }>Wyloguj</button></a>
<a ${SessionData.idRole == 3 ? "" : 'href="/ShopAppWeb/CustomerPanel"' }><button id="registrationButtonId" class="button" ${SessionData.idRole == 3 ? "disabled" : "" } >${SessionData != null ? "Panel klienta" : "Rejestracja" }</button></a>
<b id="message">${message }</b>

<c:if test="${SessionData.idRole == 3 }">
<a href="/ShopAppWeb/OperatorPanel" id="toOperatorPanel"><button class="button">Do panelu operatora</button></a>
</c:if>

<form action="/ShopAppWeb/LoginServlet" method="POST" name="loginForm" >
<table id="loginTable">
<tr><td>Login:</td><td align="right"><input type="text" name="login" size="23" /></td></tr>
<tr><td>Hasło:</td><td align="right"><input type="password" name="password" size="23" /></td></tr>
</table>
<p id="loginButtonId"><input  type="submit" name="loginButton" value="Zaloguj" class="button" /></p>
</form>


<form action="/ShopAppWeb/ProductByCategory" method="POST" id="ProductsMainPage">
<table style="border-collapse: collapse; border: 0px; text-align: center;">
<tr>
<c:forEach items="${catList}" var="cat" begin="1" end="8" varStatus="loop">
<c:if test="${loop.index == 1 }">
<tr><td id="optionHeader" colspan="4">Wszystkie produkty według kategorii:</td></tr>
<tr><td><br></td></tr>
</c:if>
<c:if test="${loop.index == 5 }">
<tr><td colspan="4"><br></td></tr>
<tr><td id="optionHeader" colspan="4">Produkty wysokiej wydajności według kategorii:</td></tr>
<tr><td><br></td></tr>
</c:if>
<td>
<table align="center" style="border-collapse: collapse; text-align: center;">
	<tr><td>${cat.name }</td></tr>
	<tr><td><button type="submit" name="${cat.id }" value="${cat.id}" form="ProductsMainPage"><img src="data:image;base64,${cat.base64Image}" height="150" ></button></td></tr>
</table>
</td>
${loop.index == 4 ? '</tr>' : ''}		
</c:forEach>
</table>
</form>

<c:if test="${SessionData != null && SessionData.idRole == 2}">
<form action="/ShopAppWeb/LoginServlet" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table id="tableBasket">
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość koszyka: ${ total }</b></p>
<button type="submit" name="buttonDeleteRowBasket" value="${pl.id }" ${ total > 0 ? "" : "disabled" } class="button" >Usuń zazanaczone</button><br><br>
<button type="submit" formaction="/ShopAppWeb/Transaction" ${ total > 0 ? "" : "disabled" } class="button" >Dokonaj zakupu</button>
</form>
</c:if>

<form action="/ShopAppWeb/LoginServlet" method="POST" id="searchFormMain">
<input type="text" name="searchProductInput" value="${param['searchProductInput'] }" />	<input type="submit" name="searchProductButton" value="Szukaj" class="button">
</form>

<form action="/ShopAppWeb/LoginServlet" method="POST" id="resultSearchFormMain">
<c:if test="${resultSearchProducts.size() > 0}">
	<p><b>Wyniki wyszukiwania:</b></p>
	<table id="tableResultSearchMain">
	<tr><th>Lp</th><th>Nazwa produktu</th><th>Dostępna ilość</th><th>Cana</th><th>Przejdź do szczegółów</th></tr>
	<c:forEach items="${resultSearchProducts}" var="res" varStatus="lp" begin="0">
	<tr><td>${lp.count }</td><td>${res.name }</td><td align="center">${res.unitsInStock }</td><td align="center">${res.price }</td><td align="center"><button type="submit" name="buttonToProductDetailsFromMain" value="${res.id }" formaction="/ShopAppWeb/ProductDetails" class="button">Szczegóły produktu</button></td></tr>
	</c:forEach>
	</table>
</c:if>
</form>

</body>
</html>