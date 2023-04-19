<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
    <h1>[${key}]님 취미 목록</h1>
    <ol>
        <c:forEach var ="h" items = "${hList}">
           <li>${h}</li> 
        </c:forEach>
    </ol>


</body>
</html>