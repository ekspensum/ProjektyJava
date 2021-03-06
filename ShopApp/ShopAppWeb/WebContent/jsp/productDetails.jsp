<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" >
<title>Szczegóły produktu</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<p id="pageHeader">Szczegóły produktu</p>
<b id="message">${message }</b>
<c:if test="${SessionData != null }">
<p id="loginParagraph">Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button class="button" >Wyloguj</button></a>
</c:if>
<a href="/ShopAppWeb/LoginServlet" id="toMainPage"><button class="button">Do strony głównej</button></a>
<br>
<form action="/ShopAppWeb/ProductDetails" method="POST" id="ProductDetails">
<p>${pd.name }</p>
<p><img src="data:image;base64,${pd.base64Image}" height="350"></img></p>
<p style="width: 50%;">${pd.description }</p>
<p>Cena: ${pd.price }</p>
<p>Dostępna ilość: ${pd.unitsInStock }</p>
<p>Dodaj do koszyka: <input type="number" name="quantity${pd.id }" max="${pd.unitsInStock }" min="0" value="1" style="width: 3em;" ${ SessionData.idRole == 3 || pd.unitsInStock == 0 ? "disabled" : ""} />	<button type="submit" class="button" name="buttonToBasketFromDetails" value="${pd.id }" form="ProductDetails" ${ SessionData.idRole == 3 || pd.unitsInStock == 0  ? "disabled" : ""} >Dodaj do koszyka</button>
</form>

<c:if test="${SessionData != null && SessionData.idRole == 2}">
<form action="/ShopAppWeb/ProductDetails" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table id="tableBasket">
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość koszyka: ${ total }</b></p>
<button type="submit" class="button" name="buttonDeleteRowBasket" value="${pd.id }" ${ total > 0 ? "" : "disabled" } >Usuń zazanaczone</button><br><br>
<button type="submit" class="button" formaction="/ShopAppWeb/Transaction" ${ total > 0 ? "" : "disabled" } >Dokonaj zakupu</button>
</form>
</c:if>

</body>
</html>