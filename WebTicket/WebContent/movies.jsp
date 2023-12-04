<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>
<%@ include file="/nav.jsp" %>

<html>
<head>
    <title>영화 목록 및 트레일러</title>
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
    <link rel="stylesheet" href="./resources/css/movieStyle.css" />
    <link rel="stylesheet" href="./resources/css/movieStyle.css" />
    <link rel="stylesheet" href="./resources/css/MovieList.css" />
    
</head>
<body>

<div class="center">
    <h3><%= "예매율 순위" %></h3>
    
    <div class="movie-rankings">
        <div class="movie-rank">
            <span>1</span>
            <div class="movie-poster">
                <img src="avatar.jpg" alt="아바타 ">
            </div>
        </div>
        <div class="movie-rank">
            <span>2</span>
            <div class="movie-poster">
                <img src="P1235.png" alt="영화2 ">
            </div>
        </div>
        <div class="movie-rank">
            <span>3</span>
            <div class="movie-poster">
                <img src="P1236.png" alt="영화3 ">
            </div>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <h3 style="font-size: 2rem;">영화목록</h3>
    </div>
</div>

<%
    MovieRepository dao = MovieRepository.getInstance();
    ArrayList<Movie> listOfMovies = dao.getAllMovies();
%>

<div class="container">
    <div class="row" align="center">
        <%
            for (int i = 0; i < listOfMovies.size(); i++) {
                Movie movie = listOfMovies.get(i);
        %>
        <div class="col-md-4">
            <div class="card-container">
                <div class="movie-poster-rank">
                    <img src="resources/images/<%= movie.getFilename() %>" style="width: 100%">
                </div>
                <h3><%= movie.getTitle() %></h3>
                <p><%= movie.getDescription() %></p>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <hr>
</div>

<div class="container">
    <div class="row" align="center">
        <div class="col-md-12">
            <div class="card-container">
                <!-- YouTube 트레일러를 나타내는 iframe -->
                <iframe width="560" height="315" src="https://www.youtube.com/embed/kihrFxwdMb4" frameborder="0" allowfullscreen></iframe>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
