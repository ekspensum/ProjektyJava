<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie rachunku</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<a href="http://localhost:8080/Kantor/wylogowanie"><button>Wyloguj</button></a>
<br/>
<p>Panel Administratora</p>
<p>Zalogowany: ${userZalogowany.imieAdministratora } ${userZalogowany.nazwiskoAdministratora }</p>
<br>
<a href="http://localhost:8080/Kantor/panelAdministratora">Powrót</a>
<br/>
<br/>
<b>Dodawanie/usuwanie rachunku</b>
<br/>
<br/>
Wyszukaj klienta firmowego:
<form name="wyszukajKF" action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<p>Login: <input name="loginKF" type="text" size="10" /> AND/OR Nazwa firmy: <input name="nazwa" type="text" size="12" /> AND/OR Regon: <input name="regon" type="text" size="9" /> AND/OR NIP: <input name="nip" type="text" size="10" />
<input type="submit" value="Wyszukaj" /></p>
</form>
<form name="wybierzKF" action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<table>
	<tr>
	<th>Login</th>
	<th>Nazwa firmy</th>
	<th>Regon</th>
	<th>NIP</th>
	<th>Czy aktywny USD</th>
	<th>Czy aktywny EUR</th>
	<th>Czy aktywny CHF</th>
	<th>Id Rachunku USD</th>
	<th>Id Rachunku EUR</th>
	<th>Id Rachunku CHF</th>
	<th>Wybierz klienta</th>
	</tr>
<c:forEach begin="0" items="${listaKF}" var="f">
<tr>
<td>${f.login }</td> <td>${f.nazwa }</td> <td>${f.regon }</td> <td>${f.nip}</td> <td>${f.usd}</td> <td>${f.eur}</td> <td>${f.chf}</td> <td>${f.idRachunekUSD }</td> <td>${f.idRachunekEUR }</td> <td>${f.idRachunekCHF }</td> <td><input type="checkbox" name="boxKF" value="${f.idUzytkownik }"/></td>
</tr>
</c:forEach>
</table><br>
<select name="waluta"><option value="">Wybierz walutę</option><option value="0">USD</option><option value="1">EUR</option><option value="2">CHF</option></select>
<br>
<select name="wyborOpcjiKF"><option value="2">Dodanie rachunku</option><option value="1">Aktywacja rachunku</option><option value="0">Deaktywacja rachunku</option> </select>
<br><br>
Wprowadz numer rchunku banowego: <input type="text" name="rachunekKF" />
<br><br>
<input type="submit" value="Wykonaj wybraną opcję">
</form>
<br/>
Wyszukaj klienta prywatnego:
<form name="wyszukajKP" action="http://localhost:8080/Kantor/dodajRachunek" method="POST">
<p>Login: <input name="loginKP" type="text" size="10" /> AND/OR Nazwisko: <input name="nazwisko" type="text" size="12" /> AND/OR Pesel: <input name="pesel" type="text" size="11" />
<input type="submit" value="Wyszukaj" /></p>
</form>
<form name="wybierzKP" action="http://localhost:8080/Kantor/dodajRachunek" method="POST" >
<table>
	<tr>
	<th>Login</th>
	<th>Imię</th>
	<th>Nazwisko</th>
	<th>PESEL</th>
	<th>Czy aktywny USD</th>
	<th>Czy aktywny EUR</th>
	<th>Czy aktywny CHF</th>
	<th>Id Rachunku USD</th>
	<th>Id Rachunku EUR</th>
	<th>Id Rachunku CHF</th>
	<th>Wybierz klienta</th>
	</tr>
<c:forEach begin="0" items="${listaKP}" var="p">
<tr>
<td>${p.login }</td> <td>${p.imie }</td> <td>${p.nazwisko }</td> <td>${p.pesel }</td> <td>${p.usd }</td>  <td>${p.eur }</td>  <td>${p.chf }</td> <td>${p.idRachunekUSD }</td> <td>${p.idRachunekEUR }</td> <td>${p.idRachunekCHF }</td> <td><input type="checkbox" name="boxKP" value="${p.idUzytkownik }"/></td> 
</tr>
</c:forEach>
</table><br>
<select name="waluta"><option value="">Wybierz walutę</option><option value="0">USD</option><option value="1">EUR</option><option value="2">CHF</option></select>
<br>
<select name="wyborOpcjiKP"><option value="2">Dodanie rachunku</option><option value="1">Aktywacja rachunku</option><option value="0">Deaktywacja rachunku</option> </select>
<br><br>
Wprowadz numer rchunku banowego: <input type="text" name="rachunekKP" />
<br><br> 
<input type="submit" value="Wykonaj wybraną opcję">
</form>
<br/>
<b>${komunikat }</b><br/>


</body>
</html>