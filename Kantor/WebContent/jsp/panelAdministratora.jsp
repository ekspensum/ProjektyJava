<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel Administratora</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<p>Panel Administratora</p>
<p>Zalogowany: ${userZalogowany.imieAdministratora } ${userZalogowany.nazwiskoAdministratora }</p>
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
<p>Dodaj użytkownika</p>
<form action="http://localhost:8080/Kantor/panelAdministratora" method="POST">
<select name="opcje">
<option value="wybor">Wybierz opcję</option>
<option value="admin">Nowy administrator</option>
<option value="klientFirmowy">Nowy klient firmowy</option>
<option value="klientPrywatny">Nowy klient prywatny</option>
<option value="operator">Nowy operator</option>
</select>
<input type="submit" value="Wybierz" />
<br/>
<br/>
<a href="http://localhost:8080/Kantor/dodajRachunek">Dodaj/usuń rachunek walutowy klienta</a>
</form>
</body>
</html>