<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kantor walutowy</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<a href="/Kantor/wylogowanie"><button>Wyloguj</button></a>
<br/>
<br/>
<b>Panel Klienta Firmowego</b>
<br/>
Zalogowany: ${userZalogowany.nazwaFirmy }
<jsp:include page="index.jsp"></jsp:include>
<br>
<a href="/Kantor/statystykaKF">Statystyka wykonanych transakcji</a>
<br><br>
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
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="sprzedajUSD" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajUSD }</b>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="kupUSD" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupUSD }</b>
<br>
<b>${komunikat }</b>
</c:if>
<br><br>
<c:if test="${userZalogowany.eur eq true }">
Rachunek EUR<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuEUR }<br/>
Dostępne środki: EUR${rachunkiKF.stanEUR }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>EUR	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="sprzedajEUR" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajEUR }</b>
<br>
<b>EUR	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="kupEUR" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupEUR }</b>
<br>
<b>${komunikat }</b>
</c:if>
<br><br>
<c:if test="${userZalogowany.chf eq true }">
Rachunek CHF<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuCHF }<br/>
Dostępne środki: CHF${rachunkiKF.stanCHF }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>CHF	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="sprzedajCHF" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajCHF }</b>
<br>
<b>CHF	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="/Kantor/transakcjaKF" method="POST">	Kwota: <input type="text" name="kupCHF" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupCHF }</b>
<br>
<b>${komunikat }</b>
</c:if>
</body>
</html>