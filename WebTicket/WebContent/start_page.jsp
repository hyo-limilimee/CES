<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Movie" %>
<%@ page import="dao.MovieRepository" %>



<html>
<head>
    <!-- 페이지 헤더 등 필요한 정보 추가 -->
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css"/>
    <title>영화 예매</title>
    <style>
        .movie-image {
            width: 100%;
        }
        .btn-container {
            margin-top: 10px;
        }
        .login-section {
            float: right;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="login-section">
    <%-- 세션이 유지되는 동안에만 로그아웃 링크 표시 --%>
    <% if (session.getAttribute("username") != null) { %>
        <a href="./logout.jsp" class="btn btn-secondary">로그아웃</a>
    <% } else { %>
        <a href="./login.jsp" class="btn btn-secondary">로그인</a>
        <a href="./register.jsp" class="btn btn-primary">회원가입</a>
    <% } %>
</div>

<jsp:include page="header.jsp" /> <!-- 헤더 부분 include -->

<div class="container">
    <div class="row">
        <%
            MovieRepository movieDao = MovieRepository.getInstance();
            ArrayList<Movie> listOfMovies = movieDao.getAllMovies();
            
            for (Movie movie : listOfMovies) {
        %>
        <div class="col-md-4">
            <img src="./resources/images/<%=movie.getFilename()%>" class="movie-image">
            <h3><%=movie.getTitle()%></h3>
            <p><%=movie.getDescription()%></p>
            <div class="btn-container">
                <a href="./movieDetail.jsp?id=<%=movie.getMovieId()%>" class="btn btn-secondary">상세보기</a>
                <a href="./booking.jsp?id=<%=movie.getMovieId()%>" class="btn btn-primary">예매하기</a>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>

<jsp:include page="footer.jsp" /> <!-- 푸터 부분 include -->

</body>
</html>
