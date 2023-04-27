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

    <ul>
     <li> #국어:${score.kor}점</li>
       <li> #영어: ${score.eng}점</li>
       <li> #수학: ${score.math}점</li>
       <li>#총점: ${score.total}점</li>
       <li id="avg"></li>
       <li>#학점: ${score.grade}</li>
   </ul>
   <button><a href="/score/list">목록</a></button> <button><a href="/score/modify?stuNum=${score.stuNum}">수정</a></button>


    <script>
        const score = '${score.average}';
        const $ceil = Math.ceil(score*100)/100;
        document.getElementById('avg').textContent = '#평균: '+$ceil+'점';



    </script>

</body>
</html>