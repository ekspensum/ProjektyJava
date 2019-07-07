<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src='<c:url value="/static/js/main.js" />'></script>
	<link href='<c:url value="/static/css/style.css" />' style="text/css" rel="stylesheet" media="all" />
	<title><tiles:getAsString name="title" /></title>
</head>
<body onload="dateTime()">
        <header id="header">
            <tiles:insertAttribute name="header" />
        </header>
     
        <section id="menu">
            <tiles:insertAttribute name="menu" />
        </section>
             
        <section id="content">
            <tiles:insertAttribute name="body" />
<%--             <tiles:insertAttribute name="belt" ignore="true"/> --%>
        </section>
         
        <footer id="footer">
            <tiles:insertAttribute name="footer" />
        </footer>
</body>
</html>