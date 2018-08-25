<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kantor walutowy</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<p>Panel Klienta Firmowego</p>
<p>Zalogowany: ${userZalogowany.nazwaFirmy }</p>
<br>
<b>Stan posiadanych rachunków:</b>
<p>Rachunek PLN</p>
<p>Nr rachunku: ${rachunki.nrRachunkuPLN }</p>
<p>Dostępne środki: PLN${rachunki.stanPLN }</p>
<br>
<p>Rachunek USD</p>
<p>Nr rachunku: ${rachunki.nrRachunkuUSD }</p>
<p>Dostępne środki: USD${rachunki.stanUSD }</p>

<br>
<b>Wykonaj transakcję:</b>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="http://localhost:8080/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="sprzedajUSD" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="http://localhost:8080/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="kupUSD" size="10" /><input type="submit" value="Kup" /></form>
<br>
</body>
</html>