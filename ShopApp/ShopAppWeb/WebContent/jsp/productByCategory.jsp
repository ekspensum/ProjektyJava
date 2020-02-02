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
<p id="loginParagraph">Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button class="button" >Wyloguj</button></a>
</c:if>
<a href="/ShopAppWeb/LoginServlet" id="toMainPage"><button class="button">Do strony głównej</button></a>
<form action="/ShopAppWeb/ProductDetails" method="POST" id="ProductByCategory">
<p id="optionHeader">Kategoria: ${curentCategory.name }</p>
<table>
	<c:forEach items="${productList }" var="pl" begin="0">
	<tr><td rowspan="5">
		<button type="submit" name="buttonToProductDetailsFromCategory" value="${pl.id}" form="ProductByCategory">
		<img src="data:image;base64,${pl.base64Image}"  height="150" width="230">
		</button>
	</td></tr>
	<tr><td>${pl.name }</td></tr>
	<tr><td>Cena: ${pl.price }</td></tr>
	<tr><td>Dostępna ilość: ${pl.unitsInStock }</td></tr>
	<tr><td>
		<input type="hidden" name="${curentCategory.id }" value="${curentCategory.id }">
		<input type="number" name="quantity${pl.id }" max="${pl.unitsInStock }" min="0" value="1" style="width: 3em;" ${ SessionData.idRole == 3 || pl.unitsInStock == 0 ? "disabled" : ""} >	
		<button type="submit" class="button" name="buttonToBasketFromCategory" value="${pl.id }" 
			formaction="/ShopAppWeb/ProductByCategory" ${ SessionData.idRole == 3 || pl.unitsInStock == 0  ? "disabled" : ""} >Dodaj do koszyka</button>
	</td></tr>
	<tr><td><br></td></tr>
</c:forEach>
</table>
</form>

<c:if test="${SessionData != null && SessionData.idRole == 2}">
<form action="/ShopAppWeb/ProductByCategory" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table id="tableBasket">
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość koszyka: ${ total }</b></p>
<input type="hidden" name="${curentCategory.id }" value="${curentCategory.id }">
<button type="submit" class="button" name="buttonDeleteRowBasket" value="${pl.id }" ${ total > 0 ? "" : "disabled" } >Usuń zazanaczone</button><br><br>
<button type="submit" class="button" formaction="/ShopAppWeb/Transaction" ${ total > 0 ? "" : "disabled" } >Dokonaj zakupu</button>
</form>
</c:if>

</body>
</html>