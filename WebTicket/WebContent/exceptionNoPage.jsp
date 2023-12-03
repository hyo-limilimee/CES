<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
    <title>페이지 오류</title>
</head>
<body>
    <jsp:include page="menu.jsp" />
    <div class="jumbotron" style="background-color: #B22222;">
        <div class="container">
            <h2 class="alert alert-danger">Error: 요청하신 페이지를 찾을 수 없습니다.</h2>
            <p class="text-white text-center">페이지 URL을 확인하고 다시 시도해주세요.</p>
        </div>
    </div>
    <div class="container">
        <p><%=request.getRequestURL()%></p>
        <p>
            <a href="movies.jsp" class="btn btn-secondary">상품 목록 &raquo;</a>
        </p>
        <!-- Add additional navigation links if needed -->
    </div>
</body>
</html>
