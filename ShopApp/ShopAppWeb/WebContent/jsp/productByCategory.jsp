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
</c:if>
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<br>
<p id="optionHeader">Kategoria: ${curentCategory.name }</p>
<form action="/ShopAppWeb/ProductDetails" method="POST" id="ProductByCategory">
<c:forEach items="${productList }" var="pl" begin="0">
<p> <button type="submit" name="buttonToProductDetails" value="${pl.id}" form="ProductByCategory"><img src="data:image;base64,${pl.base64Image}"></button></p>
<p>${pl.name } Cena: ${pl.price } Dostępna ilość: ${pl.unitsInStock }</p>
<input type="hidden" name="${curentCategory.id }" value="${curentCategory.id }">
<input type="number" name="quantity${pl.id }" min="1" max="${pl.unitsInStock }" value="1" style="width: 3em;">	<button type="submit" name="buttonToBasketFromCategory" value="${pl.id }" formaction="/ShopAppWeb/ProductByCategory">Dodaj do koszyka</button>
</c:forEach>
</form>

<c:if test="${SessionData != null}">
<form action="/ShopAppWeb/ProductDetails" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
	<p><input type="submit" name="buttonDeleteRow" value="Usuń">	<input type="checkbox" name="chbxDeleteRow" value="${br.productId }">	${br.productName }	Ilość: ${br.quantity }	cena: ${br.price }</p>
</c:forEach>
</form>
</c:if>
<c:if test="${SessionData != null }">
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
</body>
</html>