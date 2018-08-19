<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel Operatora</title>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<jsp:include page="index.jsp"></jsp:include>
<b>Wprowadzenie nowych ustawień:</b>
<br>
<br>
<form method="POST" name="daneUSD" action="http://localhost:8080/Kantor/panelOperatora">
<b>USD		Bid: <input type="text" name="dolarBid" /> Ask: <input type="text" name="dolarAsk" />	<input type="submit" value="Dodaj" /></b>
</form>

<br>
<table>
	<tr>
	<th>Znak</th>
	<th>id</th>
	<th>Bid</th>
	<th>Ask</th>
	<th>Data</th>
	<th>Operator</th>
	</tr>

<c:forEach items="${listaDaneDolar}" var="ldd" begin="0">
<tr>
<td>${ldd.znak}</td>	<td>${ldd.idDolar}</td>  <td>${ldd.bid }</td> <td>${ldd.ask }</td> <td>${ldd.dataDodania }</td> <td>${ldd.idOperator }</td>	
</tr>
</c:forEach>
</table>
<br>
<form method="POST" name="daneEUR" action="http://localhost:8080/Kantor/panelOperatora">
<b>EUR		Bid: <input type="text" name="euroBid" /> Ask: <input type="text" name="euroAsk" />	<input type="submit" value="Dodaj" /></b>
</form>
<br>
<table>
	<tr>
	<th>Znak</th>
	<th>id</th>
	<th>Bid</th>
	<th>Ask</th>
	<th>Data</th>
	<th>Operator</th>
	</tr>

<c:forEach items="${listaDaneEuro}" var="lde" begin="0">
<tr>
<td>${lde.znak}</td> <td>${lde.idEuro}</td>  <td>${lde.bid }</td> <td>${lde.ask }</td> <td>${lde.dataDodania }</td> <td>${lde.idOperator }</td>	
</tr>
</c:forEach>
</table>
<br>
<br>
<b>${komunikat }</b>
</body>
</html>