<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel klienta</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<c:if test="${SessionData != null }">
	<p id="loginParagraph">Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
	<a href="/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button class="button" >Wyloguj</button></a>
		<form action="/ShopAppWeb/CustomerPanel" method="POST" id="customerLoginUpdateForm">
			<p>Potwierdź swoje dane w celu edycji:</p>
			<p>Login: <input type="text" name="login"/></p>
			<p>Hasło: <input type="password" name="password" /></p>
			<p><button type="submit" name="buttonOpenEdit" value="yes" form="customerLoginUpdateForm" class="button">Otwórz dane do edycji</button>
			<p><button type="submit" name="buttonSaveEdit" value="yes" form="customerDataForm" class="button" ${requestScope['openToEdit'] eq 'yes' || requestScope['saveEdit'] eq 'yes' ? '' : 'disabled'}>Zapisz zmienione dane</button>
		</form>
</c:if>

<a href="/ShopAppWeb/LoginServlet" id="toMainPage"><button class="button">Do strony głównej</button></a>
<b id="message">${message }</b>

<c:if test="${SessionData.idRole == null || (SessionData.idRole == 2 && requestScope['openToEdit'] eq 'yes') || (SessionData.idRole == 2 && requestScope['saveEdit'] eq 'yes')  }">
<form action="/ShopAppWeb/CustomerPanel" method="POST" id="customerDataForm" >
<p>Login: <input type="text" name="login" ${requestScope['openToEdit'] eq 'yes' || requestScope['saveEdit'] eq 'yes' ? 'readonly="readonly"' : ''} value="${requestScope['openToEdit'] eq 'yes' ? userData.login : param['login']}"/></p>
<p>Hasło: <input type="password" name="password" value="${requestScope['openToEdit'] eq 'yes' ? '' : param['password']}" /></p>
<p>Imię: <input type="text" name="firstName" value="${requestScope['openToEdit'] eq 'yes' ? customerData.firstName : param['firstName']}" /></p>
<p>Nazwisko: <input type="text" name="lastName" value="${requestScope['openToEdit'] eq 'yes' ? customerData.lastName : param['lastName']}"/></p>
<p>Pesel: <input type="text" name="pesel" value="${requestScope['openToEdit'] eq 'yes' ? customerData.pesel : param['pesel']}"/></p>
<p>Kraj: <input type="text" name="country" value="${requestScope['openToEdit'] eq 'yes' ? customerData.country : param['country']}"/></p>
<p>Kod pocztowy: <input type="text" name="zipCode" value="${requestScope['openToEdit'] eq 'yes' ? customerData.zipCode : param['zipCode']}"/></p>
<p>Miasto: <input type="text" name="city" value="${requestScope['openToEdit'] eq 'yes' ? customerData.city : param['city']}"/></p>
<p>Ulica: <input type="text" name="street" value="${requestScope['openToEdit'] eq 'yes' ? customerData.street : param['street']}"/></p>
<p>Nr domu: <input type="text" name="streetNo" value="${requestScope['openToEdit'] eq 'yes' ? customerData.streetNo : param['streetNo']}"/></p>
<p>Nr lokalu: <input type="text" name="unitNo" value="${requestScope['openToEdit'] eq 'yes' ? customerData.unitNo : param['unitNo']}"/></p>
<p>E-mail: <input type="email" name="email" value="${requestScope['openToEdit'] eq 'yes' ? customerData.email : param['email']}"/></p>
<p>Czy firma?: <input type="checkbox" name="isCompany" value="yes" ${requestScope['openToEdit'] eq 'yes' ? customerData.company == true ? "checked" : '' : (param['isCompany'] != null && param['isCompany'] eq 'yes' ? "checked" : '')}/></p>
<p>Nazwa firmy: <input type="text" name="companyName" value="${requestScope['openToEdit'] eq 'yes' ? (customerData.company == true ?  customerData.companyName : '') : param['companyName'] }"/></p>
<p>NIP: <input type="text" name="taxNo" value="${requestScope['openToEdit'] eq 'yes' ? (customerData.company == true ?  customerData.taxNo : '') : param['taxNo'] }" /></p>
<p>REGON: <input type="text" name="regon" value="${requestScope['openToEdit'] eq 'yes' ? (customerData.company == true ?  customerData.regon : '') : param['regon'] }"/></p>
<p><input type="submit" value="Dodaj" name="buttonAddCustomer" class="button" ${requestScope['openToEdit'] eq 'yes' || requestScope['saveEdit'] eq 'yes' ? 'disabled' : ''}/></p>
</form>
</c:if>

<c:if test="${SessionData.idRole == 2}">
<form action="/ShopAppWeb/CustomerPanel" method="POST" id="customerTransactionForm">
<p>Wyszukiwanie transakcji wg daty</p>
<p>Data od: <input type="date" name="searchProductDateFrom" value="${param['searchProductDateFrom']}" />	
Data do: <input type="date" name="searchProductDateTo" value="${param['searchProductDateTo']}" />	
Wyświetl: <select name="showRowTransactions">
	<option value="5" ${param['showRowTransactions'] == '5' ? 'selected' : ''} >5 transakcji</option>
	<option value="10" ${param['showRowTransactions'] == '10' ? 'selected' : ''} >10 transakcji</option>
	<option value="15" ${param['showRowTransactions'] == '15' ? 'selected' : ''} >15 transakcji</option>
</select>
<button type="submit" name="buttonSearchTransaction" value="yes" form="customerTransactionForm" class="button">Wyszukaj</button></p>
<p>
<button name="rowResultDriver" value="stepLeftEnd"><img src="icons/16x16_move_left_to_end.png"></button>
<button name="rowResultDriver" value="stepLeft" ${buttonMoveLeftDiesabled eq 'YES' ? 'disabled' : ''} ><img src="icons/16x16_move_left.png"></button>
<button name="rowResultDriver" value="stepRight" ${buttonMoveRightDiesabled eq 'YES' ? 'disabled' : ''} ><img src="icons/16x16_move_right.png"></button>
<button name="rowResultDriver" value="stepRightEnd"><img src="icons/16x16_move_right_to_end.png"></button></p>
<table id="tableTransactions">
<tr><th>Lp</th><th>Data zakupu</th>
<th><button type="submit" name="sortBy" value="productNameAscending" form="customerTransactionForm"><img src="icons/iconfinder_BT_sort_az_905641.png"></button>
Nazwa produktu
<button type="submit" name="sortBy" value="productNameDescending" form="customerTransactionForm"><img src="icons/iconfinder_BT_sort_za_905640.png"></button></th>
<th><button type="submit" name="sortBy" value="productPriceAscending" form="customerTransactionForm"><img src="icons/iconfinder_BT_sort_09_905643.png"></button>
Cena
<button type="submit" name="sortBy" value="productPriceDescending" form="customerTransactionForm"><img src="icons/iconfinder_BT_sort_90_905642.png"></button></th>
<th>Zakupiona ilość</th><th>Wartość zakupu</th><th>Realizacja</th><th>Data wysyłki</th></tr>
<c:forEach items="${transactionsDataList}" var="tr" begin="0" varStatus="loop">
<tr><td>${loop.count }</td><td>${tr.dateTime.toLocalDate() }</td><td>${tr.productName }</td><td>${tr.price }</td><td>${tr.quantity }</td><td>${tr.price * tr.quantity }</td><td>${tr.execOrder == true ? "Tak" : "Nie" }</td><td>${tr.execOrderDate.toLocalDate() }</td></tr>
</c:forEach>
</table>
</form>
</c:if>

</body>
</html>