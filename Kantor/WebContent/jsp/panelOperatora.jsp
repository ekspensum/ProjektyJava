<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel Operatora</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<a href="/Kantor/wylogowanie"><button>Wyloguj</button></a>
<br/>
<p>Panel Operatora</p>
<p>Zalogowany: ${userZalogowany.imieOperatora } ${userZalogowany.nazwiskoOperatora }</p>
<br>
<p>Aktualne kursy walut:</p>
<br>
<b>USD kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/>
<b>USD sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/>
<br>
<b>EUR kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/>
<b>EUR sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/>
<br>
<b>CHF kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/>
<b>CHF sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/>
<br>
<br>
<b>Wprowadzenie nowych ustawień:</b>
<br>
<br>
<form method="POST" name="daneUSD" action="/Kantor/panelOperatora">
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
	<th>Imię Operatora</th>
	<th>Nazwisko Operatora</th>
	</tr>

<c:forEach items="${listaDaneDolar}" var="ldd" begin="0">
<tr>
<td>${ldd.znak}</td>	<td>${ldd.idDolar}</td>  <td>${ldd.bid }</td> <td>${ldd.ask }</td> <td>${ldd.dataDodania }</td> <td>${ldd.imieOperatora }</td>	<td>${ldd.nazwiskoOperatora }</td>	
</tr>
</c:forEach>
</table>
<br>
<form method="POST" name="daneEUR" action="/Kantor/panelOperatora">
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
	<th>Imię Operatora</th>
	<th>Nazwisko Operatora</th>
	</tr>

<c:forEach items="${listaDaneEuro}" var="lde" begin="0">
<tr>
<td>${lde.znak}</td> <td>${lde.idEuro}</td>  <td>${lde.bid }</td> <td>${lde.ask }</td> <td>${lde.dataDodania }</td> <td>${lde.imieOperatora }</td>	<td>${lde.nazwiskoOperatora }</td>
</tr>
</c:forEach>
</table>
<br>
<form method="POST" name="daneCHF" action="/Kantor/panelOperatora">
<b>CHF		Bid: <input type="text" name="frankBid" /> Ask: <input type="text" name="frankAsk" />	<input type="submit" value="Dodaj" /></b>
</form>
<br>
<table>
	<tr>
	<th>Znak</th>
	<th>id</th>
	<th>Bid</th>
	<th>Ask</th>
	<th>Data</th>
	<th>Imię Operatora</th>
	<th>Nazwisko Operatora</th>
	</tr>

<c:forEach items="${listaDaneFrank}" var="ldf" begin="0">
<tr>
<td>${ldf.znak}</td> <td>${ldf.idFrank}</td>  <td>${ldf.bid }</td> <td>${ldf.ask }</td> <td>${ldf.dataDodania }</td> <td>${ldf.imieOperatora }</td>	<td>${ldf.nazwiskoOperatora }</td>
</tr>
</c:forEach>
</table>
<br>
<br>
<b>${komunikat }</b>
</body>
</html>