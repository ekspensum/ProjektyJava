<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" >
<title>Panel operatora</title>
<link href="css/arkusz.css" style="text/css" rel="stylesheet" />
</head>
<body>
<p id="pageHeader">Panel operatora</p>
<p>Zalogowany: ${SessionData.firstName } ${SessionData.lastName }</p>
<a href="/ShopAppWeb/LoginServlet" id="toMainPage"><button class="button">Do strony głównej</button></a>
<b id="message">${message }</b>
<a href="/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button class="button">Wyloguj</button></a>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addProductForm" enctype="multipart/form-data">
<table>
<tr><th id="optionHeader" colspan="2">Dodawanie /edycja produktu</th></tr>
<tr><td>Nazwa produktu:</td><td><input type="text" name="productName" value="${productToEdit != null ? productToEdit.name : clear == 'yes' ? '' : param['productName'] }" /></td></tr>
<tr><td>Opis produktu:</td><td><textarea name="productDescription" rows="10" cols="50" form="addProductForm" >${productToEdit != null ? productToEdit.description : clear == 'yes' ? '' :  param['productDescription'] }</textarea></td></tr>
<tr><td>Cena:</td><td><input type="text" name="productPrice" value="${productToEdit != null ? productToEdit.price : clear == 'yes' ? '' : param['productPrice'] }" /></td></tr>
<tr><td>Dostępna ilość:</td><td><input type="text" name="productUnitsInStock" value="${productToEdit != null ? productToEdit.unitsInStock : clear == 'yes' ? '' : param['productUnitsInStock'] }" /></td></tr>
<tr><td>Wybierz kategorię 1:</td><td><select id="category1" name="category1">
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }" ${categoryToEdit != null ? categoryToEdit[0].id == cat.id ? 'selected' : '' : clear == 'yes' ? '' : param['category1'] == cat.id ? 'selected' : ''  }>${cat.name }</option>
</c:forEach>
</select></td></tr>
<tr><td>Wybierz kategorię 2:</td><td><select id="category2" name="category2" >
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }" ${categoryToEdit != null ? categoryToEdit[1].id == cat.id ? 'selected' : '' : clear == 'yes' ? '' : param['category2'] == cat.id ? 'selected' : '' }>${cat.name }</option>
</c:forEach>
</select></td></tr>
<tr><td>Dodaj plik:</td><td><input type="file" name="imageProduct" accept="image/*"/></td></tr>
<tr><td colspan="2" align="right"><input type="submit" name="buttonAddProduct" value="Dodaj produkt" ${productToEdit != null ? 'disabled' : '' } class="button" /></td></tr>
</table>
</form>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addCategoryForm" enctype="multipart/form-data">
<table>
<tr><td id="optionHeader" colspan="2">Dodawanie kategorii</td></tr>
<tr><td>Nazwa kategorii:</td><td><input type="text" name="categoryName" value="${param['categoryName'] }" /></td></tr>
<tr><td>Dodaj plik:</td><td><input type="file" name="imageCategory" required="required" accept="image/*"/></td></tr>
<tr><td colspan="2" align="right"><input type="submit" name="buttonAddCategory" value="Dodaj kategorię" class="button" /></td></tr>
</table>
</form>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="searchProductForm">
<table id="tableSearchProduct">
<tr>
	<td colspan="2">Wyszukaj produkty o ilości < lub = od:</td>
	<td><input type="number" name="searchPruductByQuantity" min="1" value="5" style="width: 3em;" /></td>
	<td colspan="2" align="right"><button type="submit" name="searchQuantityButton" value="yes" form="searchProductForm" class="button">Wyszukaj</button></td>
</tr>
<tr>
	<td colspan="2">Wyszukaj produkt wg fragmentu nazwy:</td>
	<td><input type="text" name="searchPruductByName" value="${param['searchPruductByName'] }" /></td>
	<td colspan="2" align="right"><button type="submit" name="searchPruductButton" value="yes" form="searchProductForm" class="button">Wyszukaj</button></td>
</tr>
<tr><th>Lp</th><th>Nazwa produktu</th><th>Cena</th><th>Ilość na stanie</th><th>Edytuj</th></tr>
<c:forEach items="${Products }" var="pr" begin="0" varStatus="lp">
<tr><td>${lp.count }</td><td>${pr.name }</td><td>${pr.price }</td><td>${pr.unitsInStock }</td><td><input type="checkbox" name="idProduct" value="${pr.id }"></td></tr>
</c:forEach>
</table>
<p>Wybierz do edycji zaznaczony produkt: <button type="submit" class="button" name="editButton" value="yes" form="searchProductForm" ${param['searchPruductByName'] != null && param['searchPruductByName'] != '' || param['searchPruductByQuantity'] != null ? '' : 'disabled' }>Wybierz</button></p>
<p>Zapisz zmiany w wybranym produkcie: <button type="submit" class="button" name="saveButton" value="yes" form="addProductForm" ${productToEdit != null ? '' : 'disabled' }>Zapisz zmiany</button></p>
</form>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="execTransactionForm">
<table id="tableExecTransaction">
<tr><td id="optionHeader" colspan="8">Wyszukaj niezrealizowane zamówienia:</td></tr>
<tr><td>Data od:</td><td><input name="transactionDateFrom" type="date" value="${param['transactionDateFrom'] }" /></td><td align="right">Data do:</td><td><input name="transactionDateTo" type="date" value="${param['transactionDateTo'] }" /></td><td colspan="4" align="right"><button name="buttonSearchNoExecOrder" form="execTransactionForm" type="submit" value="yes" class="button">Wyszukaj</button></td></tr>
<tr><th>Lp</th><th>Imie klienta</th><th>Nazwisko klienta</th><th>Nazwa produktu</th><th>Ilość</th><th>Cena</th><th>Data zamówienia</th><th>Wybierz</th></tr>
<c:forEach items="${Transactions}" var="tr" begin="0" varStatus="lp">
<tr><td>${lp.count }</td><td>${tr.customer.firstName }</td><td>${tr.customer.lastName }</td><td>${tr.product.name }</td><td>${tr.quantity }</td><td>${tr.price }</td><td>${tr.dateTime }</td><td><input type="checkbox" name="idTransaction" value="${tr.id }" /></td></tr>
</c:forEach>
</table>
<p><button name="buttonExecOrder" form="execTransactionForm" type="submit" value="yes" class="button">Oznacz realizację</button></p>
</form>

</body>
</html>