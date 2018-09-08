<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transakcja</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/panelKlientaFirmowego">Powrót</a>
<p>Zalogowany: ${userZalogowany.nazwaFirmy }</p>
<br>
<b>Stan posiadanych rachunków:</b>
<p>Rachunek PLN</p>
<p>Nr rachunku: ${rachunkiKF.nrRachunkuPLN }</p>
<p>Dostępne środki: PLN${rachunkiKF.stanPLN }</p>
<br>
<c:if test="${userZalogowany.usd eq true }">
<p>Rachunek USD</p>
<p>Nr rachunku: ${rachunkiKF.nrRachunkuUSD }</p>
<p>Dostępne środki: USD${rachunkiKF.stanUSD }</p>
</c:if>
<br>
<c:if test="${userZalogowany.eur eq true }">
<p>Rachunek EUR</p>
<p>Nr rachunku: ${rachunkiKF.nrRachunkuEUR }</p>
<p>Dostępne środki: EUR${rachunkiKF.stanEUR }</p>
</c:if>
<br>
<c:if test="${userZalogowany.chf eq true }">
<p>Rachunek CHF</p>
<p>Nr rachunku: ${rachunkiKF.nrRachunkuCHF }</p>
<p>Dostępne środki: CHF${rachunkiKF.stanCHF }</p>
</c:if>
<br>
<br>
<form method="POST" action="http://localhost:8080/Kantor/panelKlientaFirmowego" >
<b>${transakcjaKF.rodzaj }:</b>	<input type="text" name="kwota" value="${transakcjaKF.kwota }" disabled="disabled"/> <b>${transakcjaKF.znak } po cenie: </b><input type="text" name="cena" value="${transakcjaKF.cena }" disabled="disabled" />
	<input type="submit" value="ZATWIERDŹ" />
</form>
<br>

</body>
</html>