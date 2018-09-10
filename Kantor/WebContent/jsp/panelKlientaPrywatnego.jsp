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
<p>Panel Klienta Prywatnego</p>
<p>Zalogowany: ${userZalogowany.imieKlienta } ${userZalogowany.nazwiskoKlienta }</p>
<jsp:include page="index.jsp"></jsp:include>
<br>
<a href="http://localhost:8080/Kantor/statystykaKP">Statystyka wykonanych transakcji</a>
<br><br>
<b>Stan posiadanych rachunków:</b><br/>
Rachunek PLN<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuPLN }<br/>
Dostępne środki: PLN${rachunkiKP.stanPLN }<br/>
<br><br>
<c:if test="${userZalogowany.usd eq true }">
Rachunek USD<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuUSD }<br/>
Dostępne środki: USD${rachunkiKP.stanUSD }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="sprzedajUSD" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajUSD }</b>
<br>
<b>USD	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="kupUSD" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupUSD }</b>
<br>
<b>${komunikat }</b>
</c:if>
<br><br>
<c:if test="${userZalogowany.eur eq true }">
Rachunek EUR<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuEUR }<br/>
Dostępne środki: EUR${rachunkiKP.stanEUR }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>EUR	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="sprzedajEUR" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajEUR }</b>
<br>
<b>EUR	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="kupEUR" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupEUR }</b>
<br>
<b>${komunikat }</b>
</c:if>
<br><br>
<c:if test="${userZalogowany.chf eq true }">
Rachunek CHF<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuCHF }<br/>
Dostępne środki: CHF${rachunkiKP.stanCHF }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
<br>
<b>CHF	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankBid }"  minFractionDigits="4" maxFractionDigits="4" /></b> 
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="sprzedajCHF" size="10" /><input type="submit" value="Sprzedaj" /></form>
<b>${komunikatSprzedajCHF }</b>
<br>
<b>CHF	<fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b>
<form action="http://localhost:8080/Kantor/transakcjaKP" method="POST">	Kwota: <input type="text" name="kupCHF" size="10" /><input type="submit" value="Kup" /></form>
<b>${komunikatKupCHF }</b>
<br>
<b>${komunikat }</b>
</c:if>
</body>
</html>