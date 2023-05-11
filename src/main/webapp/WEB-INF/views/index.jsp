<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <%@ include file ="./include/static-head.jsp" %>

    <style>
        #main-title {
            margin-top: 200px;
            font-size: 30px;
            font-weight: 400px;
        }


    </style>

</head>
<body>
<%@ include file = "include/header.jsp" %>
<h1 id="main-title">
<%-- session.getAttribute("login") DTO를 가져오는 것--%>
<c:if test="${sessionScope.login == null}">
 초보자님 안녕하세요

</c:if>
<c:if test="${sessionScope.login != null}">
    ${sessionScope.login.nickName}님 안녕하세요

</c:if>
   
</h1>


    
</body>
</html>