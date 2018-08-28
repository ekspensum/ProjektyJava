<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kantor walutowy</title>
</head>
<body bgcolor="gray">
<a href="http://localhost:8080/Kantor/wylogowanie">Wyloguj</a>
<br/>
<p>Panel Klienta Prywatnego</p>
<p>Zalogowany: ${userZalogowany.imieKlienta } ${userZalogowany.nazwiskoKlienta }</p>
<jsp:include page="index.jsp"></jsp:include>
<br>
<b>Stan posiadanych rachunków:</b><br/>
Rachunek PLN<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuPLN }<br/>
Dostępne środki: PLN${rachunkiKP.stanPLN }<br/>
<br/>
Rachunek USD<br/>
Nr rachunku: ${rachunkiKP.nrRachunkuUSD }<br/>
Dostępne środki: USD${rachunkiKP.stanUSD }<br/>
<br/>
<b>Wykonaj transakcję:</b>
<br>
</body>
</html>