<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .upload-box {
        width: 100px;height: 100px;
        border: 3px dashed yellow;
        display: flex;
        color: orangered;
        cursor: pointer;

    }


#img-input{
    display: none;
}

</style>
</head>
<body>

    <h1>파일업로드 예제</h1>

    <div class="upload-box">파일첨부</div>

    <form action="/upload-file" method="post" enctype="multipart/form-data">
        <input type="file" id="img-input" name="thumbnail" accept="image/*" multiple>
        multiple을 쓰면 한번에 여러개를 올릴 수 있음 
        그렇지만 제한이 없기때문에 input을 여러개 만듦. 
        enctype="multipart/form-data"를 반드시 붙여줘야 함 
        <button type="submit">전송</button>
    </form>

    <script>
const $box = document.querySelector('.upload-box');        
const $input = document.getElementById('img-input');

$box.onclick = e => {
    $input.click();
}

    </script>
</body>
</html>