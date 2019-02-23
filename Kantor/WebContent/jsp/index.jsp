<%@page import="model.dao.ObslugaBD"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kantor walutowy</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />

<script type="text/javascript" src="currencyRates.js"></script>

</head>
<body onload="callServlet()">
<p>Witamy w kantorze walutowym</p>
<p>Aktualne kursy walut:</p>

<b>USD kupno: <b id="usdBid" style="color: blue;"></b></b><br/>
<b>USD sprzedaż: <b id="usdAsk" style="color: blue;"></b></b><br/>
<br>
<b>EUR kupno: <b id="eurBid" style="color: blue;"></b></b><br/>
<b>EUR sprzedaż: <b id="eurAsk" style="color: blue;"></b></b><br/>
<br>
<b>CHF kupno: <b id="chfBid" style="color: blue;"></b></b><br/>
<b>CHF sprzedaż: <b id="chfAsk" style="color: blue;"></b></b><br/>

<%-- <b>USD kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/> --%>
<%-- <b>USD sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_usd * mnoznik.dolarAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/> --%>
<!-- <br> -->
<%-- <b>EUR kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/> --%>
<%-- <b>EUR sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_eur * mnoznik.euroAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/> --%>
<!-- <br> -->
<%-- <b>CHF kupno: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankBid }"  minFractionDigits="4" maxFractionDigits="4" /></b><br/> --%>
<%-- <b>CHF sprzedaż: <fmt:formatNumber pattern="#0.0000" value="${1 / kurs.pln_chf * mnoznik.frankAsk }"  minFractionDigits="4" maxFractionDigits="4" /> </b><br/> --%>
</body>
</html>