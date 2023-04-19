<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <h1>로그인</h1>
<form action="/hw/s-login-check" method="post">
    <div>
       *아이디  : <input type="text" name="id" id="id"> <br>
       *비밀번호 :  <input type="text" name="pwd" id="pwd"> <br>
        <input type="submit" id="login" value="LOGIN">
    </div>
</form>


    <script>
        const $login = document.getElementById('login');
        $login.onclick = e => {
        const $id = document.getElementById('id').value;
        const $pwd = document.getElementById('pwd').value;

            // console.log($id);
            // console.log($pwd);
        
        }

      



    </script>

</body>
</html>