<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
    <title>Product ID Error</title>
</head>
<body>  
<%@ include file="/nav.jsp" %>
    <div class="jumbotron" style="background-color: #B22222;">
        <div class="container">
            <h2 class="alert alert-danger">Error: 해당 상품이 존재하지 않습니다.</h2>
             <p class="text-white text-center">상품 ID를 확인해주세요.</p>
        </div>
    </div>
    <div class="container">
        <p><%=request.getRequestURL()%>?<%=request.getQueryString()%></p>
        <p><a href="movies.jsp" class="btn btn-secondary">상품 목록 &raquo;</a></p>
        <!-- Add additional navigation links if needed -->
    </div>
</body>
</html>
