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
<a href="http://localhost:8080/Kantor/wylogowanie"><button>Wyloguj</button></a>
<br/>
<br/>
<b>Statystyka Klienta Firmowego</b>
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
</c:if>
<br><br>
<c:if test="${userZalogowany.eur eq true }">
Rachunek EUR<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuEUR }<br/>
Dostępne środki: EUR${rachunkiKF.stanEUR }<br/>
</c:if>
<br><br>
<c:if test="${userZalogowany.chf eq true }">
Rachunek CHF<br/>
Nr rachunku: ${rachunkiKF.nrRachunkuCHF }<br/>
Dostępne środki: CHF${rachunkiKF.stanCHF }<br/>
</c:if>
<br><br>
<a href="http://localhost:8080/Kantor/panelKlientaFirmowego">Powrót</a>
<br><br>
<b>Wyszukaj operacje według daty i waluty:</b>
<form name="okres" action="http://localhost:8080/Kantor/statystykaKF" method="POST">
<p>Wybierz walute: <select name="opcje">
	<option value="wszystkie">Wszystkie waluty</option><option value="USD">Tylko USD</option><option value="EUR">Tylko EUR</option><option value="CHF">Tylko CHF</option>
</select></p>
<p>Data od: <input type="date" name="dataOd" /> data do: <input type="date" name="dataDo" /></p>
<input type="submit" value="Wybierz">
</form>
<br><br>
<table>
	<tr>
	<th>Nr rachunku PLN</th>
	<th>Tytuł operacji</th>
	<th>WN PLN</th>
	<th>MA PLN</th>
	<th>Data operacji</th>
	<th>Nr rachunku USD</th>
	<th>WN USD</th>
	<th>MA USD</th>
	<th>Kurs USD</th>
	<th>Nr rachunku EUR</th>
	<th>WN EUR</th>
	<th>MA EUR</th>
	<th>Kurs EUR</th>
	<th>Nr rachunku CHF</th>
	<th>WN CHF</th>
	<th>MA CHF</th>
	<th>Kurs CHF</th>
	</tr>

<c:forEach items="${stat}" var="s" begin="0">
<tr>
<td>${s.nrRachunkuPLN}</td>	<td>${s.tytulOperacji}</td>  <td>${s.kwotaWN_PLN }</td> <td>${s.kwotaMA_PLN }</td> <td>${s.dataOperacji }</td> <td>${s.nrRachunkuUSD }</td>	<td>${s.kwotaWN_USD }</td> <td>${s.kwotaMA_USD }</td><td>${s.kursUSD }</td><td>${s.nrRachunkuEUR }</td>	<td>${s.kwotaWN_EUR }</td> <td>${s.kwotaMA_EUR }</td><td>${s.kursEUR }</td><td>${s.nrRachunkuCHF }</td>	<td>${s.kwotaWN_CHF }</td> <td>${s.kwotaMA_CHF }</td><td>${s.kursCHF }</td>	
</tr>
</c:forEach>
</table>
</body>
</html>