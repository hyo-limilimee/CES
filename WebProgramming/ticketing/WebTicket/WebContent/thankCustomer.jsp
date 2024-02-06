<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href = "./resources/css/movieStyle.css" />
<title>티켓 구매 완료</title>
</head>

<style>
	.finish{
		text-align:center;
		margin: auto;
		padding:30px;
		
	}
	
	.h2-font{
		font-weight:600;
		color: rgb(41, 78, 101);
	}
</style>

<body>
<%@ include file="/nav.jsp" %>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3" style="color: white">티켓 구매 완료</h1>
		</div>
	</div>
	<div class="finish">
		<h2 class="h2-font">구매해주셔서 감사합니다.</h2>	
	</div>
	<div class="container">
		<p>	<a href="./movies.jsp" class="btn btn-secondary btn-red"> &laquo; 상품 목록</a>		
	</div>
</body>
</html>

