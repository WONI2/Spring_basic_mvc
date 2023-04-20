<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    
</style>
</head>
<body>

    <h1>${score.name}님의 성적 정보</h1>

<form action="/score/modify" method="post">
    <input type="hidden" name="stuNum" value="${score.stuNum}">
    <ul>
       <li> #국어: <input type="text" value=${score.kor}  name="kor">점</li>
       <li> #영어: <input type="text" value=${score.eng} name="eng" >점</li>
       <li> #수학: <input type="text" value=${score.math} name="math">점</li>
   </ul>

   <button type="submit">수정완료</button>

</form>
    <script>
  


    </script>

</body>
</html>