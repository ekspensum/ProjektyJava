<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" >
<title>Potwierdzenie transacji</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<p id="pageHeader">Potwierdzenie transacji</p>
<b id="messageTransaction">${message }</b>
<c:if test="${SessionData != null }">
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</c:if>
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<br><br><br>

<form action="/ShopAppWeb/Transaction" method="POST">
<b>Zawartość koszyka:</b>
<table>
<tr><th>L.p.</th><th>Nazwa produktu</th><th>Ilość</th><th>Cana</th><th>Wartość</th></tr>
<c:forEach items="${SessionData.basketBeanLocal.basketData}" var="br" begin="0" varStatus="loop">
<tr><td>${loop.count }</td><td>${br.productName }</td>	<td>${br.quantity }</td>	<td>${br.price }</td><td>${br.price * br.quantity}</td></tr>
</c:forEach>
</table>
<p><b>Wartość zamówienia: ${ total }</b></p>
<p>Koszty przesyłki kurierem DHL wliczone są do wartości zamówienia</p>
<button type="submit" name="buttonBuyNow" value="buyNow" ${ total > 0 ? "" : "disabled" } >Kup teraz</button>
</form>

</body>
</html>