<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
	<script type="text/javascript" src='<c:url value="/static/js/main.js" />'></script>
	<link href='<c:url value="/static/css/style.css" />' style="text/css" rel="stylesheet" media="all" />
<%-- 	<link href='<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />' rel="stylesheet"> --%>
	<title><tiles:getAsString name="title" /></title>
</head>
<body onload="dateTime('<s:message code="header.dateTime" />'); changeSlide();">
        <header id="header">
            <tiles:insertAttribute name="header" />
        </header>
     
        <section id="menu">
            <tiles:insertAttribute name="menu" />
        </section>
             
        <section id="content">
            <tiles:insertAttribute name="body" />
        </section>
         
        <footer id="footer">
            <tiles:insertAttribute name="footer" />
        </footer>
</body>
</html>