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
<br>
<br>
<p id="optionHeader">Dodawanie produktu</p>
<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addProductForm" enctype="multipart/form-data">
<p id="productName">Nazwa produktu: <input type="text" name="productName" value="${RequestAttribute.productName }" /></p>
<p id="productDescription">Opis produktu: <textarea name="productDescription" rows="10" cols="50" form="addProductForm" >${RequestAttribute.productDescription }</textarea></p>
<p id="productPrice">Cena: <input type="text" name="productPrice" value="${RequestAttribute.productPrice }" /></p>
<p id="productUnitsInStock">Dostępna ilość: <input type="text" name="productUnitsInStock" value="${RequestAttribute.productUnitsInStock }" /></p>
<p>Wybierz kategorię 1: <select id="category1" name="category1">
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }">${cat.name }</option>
</c:forEach>
</select></p>
<p>Wybierz kategorię 2: <select id="category2" name="category2">
<c:forEach items="${listCat}" var="cat" begin="0" >
	<option value="${cat.id }">${cat.name }</option>
</c:forEach>
</select></p>
<p id="productAddFile">Dodaj plik: <input type="file" name="imageProduct" required="required" accept="image/*"/></p>
<p id="buttonAddProduct"><input type="submit" name="buttonAddProduct" value="Dodaj produkt" /></p>
</form>
<br>
<br>
<p id="optionHeader">Dodawanie kategorii</p>
<form action="/ShopAppWeb/OperatorPanel" method="POST" id="addCategoryForm" enctype="multipart/form-data">
<p id="categoryName">Nazwa kategorii: <input type="text" name="categoryName" value="${RequestAttribute.categoryName }" /></p>
<p id="categoryAddFile">Dodaj plik: <input type="file" name="imageCategory" required="required" accept="image/*"/></p>
<p id="buttonAddCategory"><input type="submit" name="buttonAddCategory" value="Dodaj kategorię" /></p>
</form>
<b id="message">${message }</b>
<a href="http://localhost:8080/ShopAppWeb/LogOutServlet" id="buttonLogOut"><button >Wyloguj</button></a>
</body>
</html>