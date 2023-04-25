<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Single+Day&display=swap" rel="stylesheet">

    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <link rel="stylesheet" href="/assets/css/main.css">
    <link rel="stylesheet" href="/assets/css/detail.css">


</head>

<body>

    <div class="card-container">
        <div class="card-wrapper">
            <section class="card" value="${content.boardNo}">
                <div class="card-title-wrapper">
                    <h2 class="card-title">${content.title}</h2>

                    <div class="time-view-wrapper">
                        <div class="time"><i class="far fa-clock"></i>${content.date}</div>
                        <div class="view">
                            <i class="fas fa-eye"></i>
                            <span class="view-count">${content.viewCount}</span>
                        </div>
                    </div>
                </div>
                <div class="card-content">
                    <p>
                        ${content.content}
                    </p>
                </div>
            </section>
            <div class="card-btn-group">
                <button class="del-btn">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        </div>




    </div>



</body>

</html>