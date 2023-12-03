<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>

<html>
<head>
<link rel ="stylesheet" href ="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href = "./resources/css/movieStyle.css" />
<title>영화 목록</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품목록</h1>
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
				<img src ="resources/images/<%=movie.getFilename()%>" style ="width: 100%">
				<h3><%=movie.getTitle()%></h3>
				<p><%=movie.getDescription()%>
				<p><%=movie.getPrice()%>원
				<p><a href="./movie.jsp?id=<%=movie.getMovieId()%>" class="btn btn-secondary btn-red" role="button"> 상세 정보 &raquo;</a>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
