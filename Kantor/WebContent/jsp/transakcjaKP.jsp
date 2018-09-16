<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transakcja</title>
<script type="text/javascript" src="skrypt.js"></script>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body bgcolor="gray" onload="odliczKP();">
<a href="http://localhost:8080/Kantor/panelKlientaPrywatnego">Powrót</a>
<p>Zalogowany: ${userZalogowany.imieKlienta } ${userZalogowany.nazwiskoKlienta }</p>
<br>
<b>Stan posiadanych rachunków:</b>
<p>Rachunek PLN</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuPLN }</p>
<p>Dostępne środki: PLN${rachunkiKP.stanPLN }</p>
<br>
<c:if test="${userZalogowany.usd eq true }">
<p>Rachunek USD</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuUSD }</p>
<p>Dostępne środki: USD${rachunkiKP.stanUSD }</p>
</c:if>
<br>
<c:if test="${userZalogowany.eur eq true }">
<p>Rachunek EUR</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuEUR }</p>
<p>Dostępne środki: EUR${rachunkiKP.stanEUR }</p>
</c:if>
<br>
<c:if test="${userZalogowany.chf eq true }">
<p>Rachunek CHF</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuCHF }</p>
<p>Dostępne środki: CHF${rachunkiKP.stanCHF }</p>
</c:if>
<br>
<b>Pozostały czas do pojęcia decyzji: </b><b id="zegarKP"></b><b> sekund.</b>
<br><br>
<form method="POST" action="http://localhost:8080/Kantor/panelKlientaPrywatnego" >
<b>${transakcjaKP.rodzaj }:</b>	<input type="text" name="kwota" value="${transakcjaKP.kwota }" disabled="disabled"/> <b>${transakcjaKP.znak } po cenie: </b><input type="text" name="cena" value="${transakcjaKP.cena }" disabled="disabled" />
	<input type="submit" value="ZATWIERDŹ" />
</form>
<br>

</body>
</html>