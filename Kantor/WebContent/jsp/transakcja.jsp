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
<p>Zalogowany: ${userZalogowany.imieKlienta } ${userZalogowany.nazwiskoKlienta } ${userZalogowany.nazwaFirmy }</p>
<form method="POST" action="http://localhost:8080/Kantor/panelKlientaFirmowego" >
<b>${transakcja.rodzaj }:</b>	<input type="text" name="kwota" value="${transakcja.kwota }" disabled="disabled"/> <b>${transakcja.znak } po cenie: </b><input type="text" name="cena" value="${transakcja.cena }" disabled="disabled" />
	<input type="submit" value="ZATWIERDŹ" />
</form>
<br>

</body>
</html>