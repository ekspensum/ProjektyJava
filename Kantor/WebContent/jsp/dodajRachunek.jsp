<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie rachunku</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<p>Panel Administratora</p>
<p>Zalogowany: ${userZalogowany.imieAdministratora } ${userZalogowany.nazwiskoAdministratora }</p>
<br>
<a href="http://localhost:8080/Kantor/panelAdministratora">Powrót</a>
<br/>
<br/>
<b>Dodawanie rachunku</b>
<br/>
<br/>
Wyszukaj klienta firmowego:
<form action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<p>Login: <input name="loginKF" type="text" size="10" /> AND/OR Nazwa firmy: <input name="nazwa" type="text" size="12" /> AND/OR Regon: <input name="regon" type="text" size="9" /> AND/OR NIP: <input name="nip" type="text" size="10" />
<input type="submit" value="Wyszukaj" /></p>
</form>
<form action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<c:forEach begin="0" items="${listaKF}" var="f">
<p>${f.login } ${f.nazwa } ${f.regon } ${f.nip} <input type="checkbox" name="boxKF" value="${f.idUzytkownik }"/> <select name="walutaKF"><option value="">Wybierz</option><option value="usd">USD</option><option value="eur">EUR</option><option value="chf">CHF</option></select></p>
</c:forEach>
<input type="submit" value="Wybierz zaznaczone">
</form>
<br/>
Wyszukaj klienta prywatnego:
<form action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<p>Login: <input name="loginKP" type="text" size="10" /> AND/OR Nazwisko: <input name="nazwisko" type="text" size="12" /> AND/OR Pesel: <input name="pesel" type="text" size="11" /> 
<input type="submit" value="Wyszukaj" /></p>
</form>
<form action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<c:forEach begin="0" items="${listaKP}" var="p">
<p>${p.login } ${p.imie } ${p.nazwisko } ${p.pesel } <input type="checkbox" name="boxKP" value="${p.idUzytkownik }"/> <select name="walutaKP"><option value="">Wybierz</option><option value="usd">USD</option><option value="eur">EUR</option><option value="chf">CHF</option></select></p>
</c:forEach>
<input type="submit" value="Wybierz zaznaczone">
</form>
<br/>
<b>${komunikat }</b><br/>


</body>
</html>