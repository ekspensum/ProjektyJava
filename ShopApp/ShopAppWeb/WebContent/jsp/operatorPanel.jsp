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
<a href="/ShopAppWeb/LoginServlet">Do strony głównej</a>
<b id="message">${message }</b>
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addProductForm" enctype="multipart/form-data">
<p id="optionHeader">Dodawanie /edycja produktu</p>
<p id="productName">Nazwa produktu: <input type="text" name="productName" value="${productToEdit != null ? productToEdit.name : clear == 'yes' ? '' : param['productName'] }" /></p>
<p id="productDescription">Opis produktu: <textarea name="productDescription" rows="10" cols="50" form="addProductForm" >${productToEdit != null ? productToEdit.description : clear == 'yes' ? '' :  param['productDescription'] }</textarea></p>
<p id="productPrice">Cena: <input type="text" name="productPrice" value="${productToEdit != null ? productToEdit.price : clear == 'yes' ? '' : param['productPrice'] }" /></p>
<p id="productUnitsInStock">Dostępna ilość: <input type="text" name="productUnitsInStock" value="${productToEdit != null ? productToEdit.unitsInStock : clear == 'yes' ? '' : param['productUnitsInStock'] }" /></p>
<p>Wybierz kategorię 1: <select id="category1" name="category1">
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }" ${categoryToEdit != null ? categoryToEdit[0].id == cat.id ? 'selected' : '' : clear == 'yes' ? '' : param['category1'] == cat.id ? 'selected' : ''  }>${cat.name }</option>
</c:forEach>
</select></p>
<p>Wybierz kategorię 2: <select id="category2" name="category2" >
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }" ${categoryToEdit != null ? categoryToEdit[1].id == cat.id ? 'selected' : '' : clear == 'yes' ? '' : param['category2'] == cat.id ? 'selected' : '' }>${cat.name }</option>
</c:forEach>
</select></p>
<p id="productAddFile">Dodaj plik: <input type="file" name="imageProduct" accept="image/*"/></p>
<p id="buttonAddProduct"><input type="submit" name="buttonAddProduct" value="Dodaj produkt" ${productToEdit != null ? 'disabled' : '' } /></p>
</form>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addCategoryForm" enctype="multipart/form-data">
<p id="optionHeader">Dodawanie kategorii</p>
<p id="categoryName">Nazwa kategorii: <input type="text" name="categoryName" value="${param['categoryName'] }" /></p>
<p id="categoryAddFile">Dodaj plik: <input type="file" name="imageCategory" required="required" accept="image/*"/></p>
<p id="buttonAddCategory"><input type="submit" name="buttonAddCategory" value="Dodaj kategorię" /></p>
</form>

<form action="/ShopAppWeb/OperatorPanel" method="POST" id="searchProductForm">
<p>Wyszukaj produkty o ilości mniejszej lub równej niż: <input type="number" name="searchPruductByQuantity" min="1" value="5" style="width: 3em;" />	<button type="submit" name="searchQuantityButton" value="yes" form="searchProductForm">Wyszukaj</button></p>
<p>Wyszukaj produkt wg fragmentu nazwy: <input type="text" name="searchPruductByName" value="${param['searchPruductByName'] }" />	<button type="submit" name="searchPruductButton" value="yes" form="searchProductForm">Wyszukaj</button></p>
<table><tr><th>Lp</th><th>Nazwa produktu</th><th>Cena</th><th>Ilość na stanie</th><th>Edytuj</th></tr>
<c:forEach items="${Products }" var="pr" begin="0" varStatus="lp">
<tr><td>${lp.count }</td><td>${pr.name }</td><td>${pr.price }</td><td>${pr.unitsInStock }</td><td><input type="checkbox" name="idProduct" value="${pr.id }"></td></tr>
</c:forEach>
</table>
<p>Wybierz do edycji zaznaczony produkt: <button type="submit" name="editButton" value="yes" form="searchProductForm" ${param['searchPruductByName'] != null && param['searchPruductByName'] != '' || param['searchPruductByQuantity'] != null ? '' : 'disabled' }>Wybierz</button></p>
<p>Zapisz zmiany w wybranym produkcie: <button type="submit" name="saveButton" value="yes" form="addProductForm" ${productToEdit != null ? '' : 'disabled' }>Zapisz zmiany</button></p>
</form>

</body>
</html>