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
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<br>
<form action="/ShopAppWeb/ProductDetails" method="POST" id="ProductDetails">
<p>${pd.name } <img src="data:image;base64,${pd.base64Image}">	${pd.description }</p>
<p>Cena: ${pd.price } Dostępna ilość: ${pd.unitsInStock }</p>
<p>Dodaj do koszyka: <input type="number" name="quantity${pd.id }" min="1" max="${pl.unitsInStock }" value="1" style="width: 3em;" />	<button type="submit" name="buttonToBasketFromDetails" value="${pd.id }" form="ProductDetails">Dodaj do koszyka</button>
</form>

<c:if test="${SessionData != null}">
<form action="/ShopAppWeb/ProductDetails" method="POST" id="basket">
<b>Zawartość koszyka:</b>
<table>
<tr><th>Usuń</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0">
<tr><td><input type="checkbox" name="chbxDeleteRow" value="${br.productId }" /></td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<%
pl.shopapp.beans.SessionData sd = (pl.shopapp.beans.SessionData) session.getAttribute("SessionData");
double total = 0.0;
for(int i=0; i<sd.getBasketBeanLocal().getBasketData().size(); i++){
	total += sd.getBasketBeanLocal().getBasketData().get(i).getPrice() * sd.getBasketBeanLocal().getBasketData().get(i).getQuantity(); 
} 
%>
<p><b>Wartość koszyka: <%=total %></b></p>
<button type="submit" name="buttonDeleteRowBasket" value="${pd.id }" >Usuń zazanaczone</button>
</form>
</c:if>

</body>
</html>