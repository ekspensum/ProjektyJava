<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" >
<title>Produkty wg kategorii</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<p id="pageHeader">Produkty według kategorii</p>
<b id="message">${message }</b>
<c:if test="${SessionData != null }">
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<br>
<p id="optionHeader">Kategoria: ${curentCategory.name }</p>
<form action="/ShopAppWeb/ProductDetails" method="POST" id="ProductByCategory">
<c:forEach items="${productList }" var="pl" begin="0">
<p> <button type="submit" name="buttonToProductDetailsFromCategory" value="${pl.id}" form="ProductByCategory"><img src="data:image;base64,${pl.base64Image}"  height="150" width="230"></button></p>
<p>${pl.name } Cena: ${pl.price } Dostępna ilość: ${pl.unitsInStock }</p>
<input type="hidden" name="${curentCategory.id }" value="${curentCategory.id }">
<input type="number" name="quantity${pl.id }" max="${pl.unitsInStock }" value="1" style="width: 3em;" ${ SessionData.idRole == 3 || pl.unitsInStock == 0 ? "disabled" : ""} >	<button type="submit" name="buttonToBasketFromCategory" value="${pl.id }" formaction="/ShopAppWeb/ProductByCategory" ${ SessionData.idRole == 3 || pl.unitsInStock == 0  ? "disabled" : ""} >Dodaj do koszyka</button>
</c:forEach>
</form>

<c:if test="${SessionData != null && SessionData.idRole == 2}">
<form action="/ShopAppWeb/ProductByCategory" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table>
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość koszyka: ${ total }</b></p>
<input type="hidden" name="${curentCategory.id }" value="${curentCategory.id }">
<button type="submit" name="buttonDeleteRowBasket" value="${pl.id }" ${ total > 0 ? "" : "disabled" } >Usuń zazanaczone</button><br><br>
<button type="submit" formaction="/ShopAppWeb/Transaction" ${ total > 0 ? "" : "disabled" } >Dokonaj zakupu</button>
</form>
</c:if>

</body>
</html>