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
<p>Zalogowany: ${userZalogowany.imieKlienta } ${userZalogowany.nazwiskoKlienta }</p>
<br>
<b>Stan posiadanych rachunków:</b>
<p>Rachunek PLN</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuPLN }</p>
<p>Dostępne środki: PLN${rachunkiKP.stanPLN }</p>
<br>
<p>Rachunek USD</p>
<p>Nr rachunku: ${rachunkiKP.nrRachunkuUSD }</p>
<p>Dostępne środki: USD${rachunkiKP.stanUSD }</p>
<br>
<br>
<form method="POST" action="http://localhost:8080/Kantor/panelKlientaPrywatnego" >
<b>${transakcjaKP.rodzaj }:</b>	<input type="text" name="kwota" value="${transakcjaKP.kwota }" disabled="disabled"/> <b>${transakcjaKP.znak } po cenie: </b><input type="text" name="cena" value="${transakcjaKP.cena }" disabled="disabled" />
	<input type="submit" value="ZATWIERDŹ" />
</form>
<br>

</body>
</html>