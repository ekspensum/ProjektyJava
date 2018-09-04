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
<br/>
<b>Panel Klienta Firmowego</b>
<br/>
Zalogowany: ${userZalogowany.nazwaFirmy }
<jsp:include page="index.jsp"></jsp:include>
<br>
<b>Stan posiadanych rachunków:</b><br/>
Rachunek PLN<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuPLN }<br/>
Dostępne środki: PLN${rachunkiKF.stanPLN }<br/>
<br/>
<c:if test="${userZalogowany.usd eq true }">
Rachunek USD<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuUSD }<br/>
Dostępne środki: USD${rachunkiKF.stanUSD }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="http://localhost:8080/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="sprzedajUSD" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajUSD }</b>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="http://localhost:8080/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="kupUSD" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupUSD }</b>
<br>
<b>${komunikat }</b>
</c:if>
</body>
</html>