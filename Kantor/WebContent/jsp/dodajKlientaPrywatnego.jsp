<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie klienta prywatnego</title>
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
<b>Dodawanie klienta prywatnego</b>
<br/>
<form action="http://localhost:8080/Kantor/dodajKlientaPrywatnego" method="POST">
<p>Login: <input name="login" type="text" value="${user.login }" /></p>
<b>${login }</b>
<p>Hasło: <input name="haslo" type="password" value="${user.haslo }"></p>
<b>${haslo }</b>
<p>Powtórz hasło: <input name="haslo2" type="password" value="${haslo2wartosc }" /></p>
<b>${haslo2 }</b>
<p>Imię: <input name="imie" type="text" value="${prywatny.imie}"/></p>
<b>${imie }</b>
<p>Nazwisko: <input name="nazwisko" type="text" value="${prywatny.nazwisko }" /></p>
<b>${nazwisko }</b>
<p>Pesel: <input name="pesel" type="text" value="${prywatny.pesel }" /></p>
<b>${pesel }</b>
<p>Kod pocztowy: <input name="kod" type="text" value="${prywatny.kod }" /></p>
<b>${kod }</b>
<p>Miasto: <input name="miasto" type="text" value="${prywatny.miasto }" /></p>
<b>${miasto }</b>
<p>Ulica: <input name="ulica" type="text" value="${prywatny.ulica }" /></p>
<b>${ulica }</b>
<p>Nr domu: <input name="nrDomu" type="text" value="${prywatny.nrDomu }" /></p>
<b>${nrDomu }</b>
<p>Nr lokalu: <input name="nrLokalu" type="text" value="${prywatny.nrLokalu }" /></p>
<b>${nrLokalu }</b>
<p>Nr telefonu: <input name="telefon" type="text" value="${prywatny.telefon }" /></p>
<b>${telefon }</b>
<p>Nr rachunku PLN: <input name="pln" type="text" value="${pln.nrRachunku }" /></p>
<b>${nrRachunku }</b>
<p><input type="submit" value="Dodaj" /></p>
<b>${komunikat }</b><br/>
<c:if test="${wynik.dodano eq false }">
<b>Imie: ${wynik.imie }, Nazwisko:	${wynik.nazwisko }, Login:	${wynik.login }, PESEL:	${wynik.pesel }, Nr rachunku bakowego:	${wynik.nrRachunku }</b>
</c:if>	
</form>
</body>
</html>