<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>00시네마 홈페이지</title>
    <style>
        body {
            background-color: lightgray;
        }
        h1 {
            text-align: center;
            font-size: calc(1em - 3pt);
        }
        .menu {
            display: flex;
            justify-content: space-around;
            list-style-type: none;
            margin: 0;
            padding: 0;
            background-color: orange;
            color: white;
        }
        .center {
            display: flex;
            justify-content: space-around;
            align-items: center;
            height: 50vh;
        }
        .movie {
            margin: 0 40px;
        }
        img {
            width: 200%;
        }
    </style>
</head>
<body>
    <h1><%= "00시네마에 오신 것을 환영합니다." %></h1>
    <ul class="menu">
        <li><a href="movies_URL" style="color: white;"><%= "영화" %></a></li>
        <li><a href="register.jsp" style="color: white;"><%= "예매" %></a></li>
        <li><a href="login.jsp" style="color: white;"><%= "로그인" %></a></li>
        <li><a href="register.jsp" style="color: white;"><%= "회원가입" %></a></li>
    </ul>
    <div class="center">
    	 <h3><%= "예매율 순위" %></h3>
    	
        <div class="movie">
            <h2><%= "1" %></h2>
            <img src="<%= "product.jsp" %>" alt="영화1">
        </div>
        <div class="movie">
            <h2><%= "2" %></h2>
            <img src="<%= "product.jsp" %>" alt="영화2">
        </div>
        <div class="movie">
            <h2><%= "3" %></h2>
            <img src="<%= "product.jsp" %>" alt="영화3">
        </div>
    </div>
</body>
</html>
