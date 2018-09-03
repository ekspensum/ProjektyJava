<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodawanie klienta firmowego</title>
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
<p>Nazwa firmy: <input name="nazwa" type="text" value="${firma.nazwa}"/></p>
<b>${nazwa }</b>
<p>REGON: <input name="regon" type="text" value="${firma.regon }" /></p>
<b>${regon }</b>
<p>NIP: <input name="nip" type="text" value="${firma.nip }" /></p>
<b>${nip }</b>
<p>Kod pocztowy: <input name="kod" type="text" value="${firma.kod }" /></p>
<b>${kod }</b>
<p>Miasto: <input name="miasto" type="text" value="${firma.miasto }" /></p>
<b>${miasto }</b>
<p>Ulica: <input name="ulica" type="text" value="${firma.ulica }" /></p>
<b>${ulica }</b>
<p>Nr domu: <input name="nrDomu" type="text" value="${firma.nrDomu }" /></p>
<b>${nrDomu }</b>
<p>Nr lokalu: <input name="nrLokalu" type="text" value="${firma.nrLokalu }" /></p>
<b>${nrLokalu }</b>
<b>Przedstawiciel</b>
<p>Imię: <input name="imie" type="text" value="${firma.imiePracownika }" /></p>
<b>${imiePracownika }</b>
<p>Nazwisko: <input name="nazwisko" type="text" value="${firma.nazwiskoPracownika }" /></p>
<b>${nazwiskoPracownika }</b>
<p>Nr telefonu: <input name="telefon" type="text" value="${firma.telefonPracownika }" /></p>
<b>${telefonPracownika }</b>
<p>Nr rachunku PLN: <input name="pln" type="text" value="${pln.nrRachunku }" /></p>
<b>${nrRachunku }</b>
<p><input type="submit" value="Dodaj" /></p>
<b>${komunikat }</b><br/>
<c:if test="${wynik.dodano eq false }">
<b>Nazwa firmy: ${wynik.nazwa }, Login:	${wynik.login }, Regon:	${wynik.regon }, NIP:	${wynik.nip }, Nr rachunku bakowego:	${wynik.nrRachunku }</b>
</c:if>	
</form>
</body>
</html>