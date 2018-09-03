<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie operatora</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<p>Panel Administratora</p>
<p>Zalogowany: ${userZalogowany.imieAdministratora } ${userZalogowany.nazwiskoAdministratora }</p>
<br>
<a href="http://localhost:8080/Kantor/panelAdministratora">Powrót</a>
<br/>
<form action="http://localhost:8080/Kantor/dodajOperatora" method="POST">
<p>Login: <input name="login" type="text" value="${user.login }" /></p>
<b>${login }</b>
<p>Hasło: <input name="haslo" type="password" value="${user.haslo }"></p>
<b>${haslo }</b>
<p>Powtórz hasło: <input name="haslo2" type="password" value="${haslo2wartosc }" /></p>
<b>${haslo2 }</b>
<p>Imię: <input name="imie" type="text" value="${operator.imie}"/></p>
<b>${imie }</b>
<p>Nazwisko: <input name="nazwisko" type="text" value="${operator.nazwisko }" /></p>
<b>${nazwisko }</b>
<p>Pesel: <input name="pesel" type="text" value="${operator.pesel }" /></p>
<b>${pesel }</b>
<p>Nr telefonu: <input name="telefon" type="text" value="${operator.telefon }" /></p>
<b>${telefon }</b>
<p><input type="submit" value="Dodaj" /></p>
<b>${komunikat }</b><br/>
<c:if test="${wynik.dodano eq false }">
<b>Imie: ${wynik.imie }, Nazwisko:	${wynik.nazwisko }, Login:	${wynik.login }, PESEL:	${wynik.pesel }</b>
</c:if>	
</form>
</body>
</html>