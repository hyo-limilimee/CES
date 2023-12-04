<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>

<html>
<head>
    <title>영화 목록 및 트레일러</title>
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
    <link rel="stylesheet" href="./resources/css/movieStyle.css" />
    <link rel="stylesheet" href="./resources/css/movieStyle.css" />
    <link rel="stylesheet" href="./resources/css/MovieList.css" />
    
</head>
<!-- 예매율 순위를 가로로 표시하는 Flexbox 스타일 -->
<style>
    .movie-rankings {
        display: flex;
        flex-direction: row;
        justify-content: space-between; /* 아이템들 간의 간격을 동일하게 설정 */
        align-items: center; /* 아이템들을 세로 중앙 정렬 */
        margin-bottom: 20px; /* 원하는 여백 설정 */
    }
	.title{
	    	font-weight:300px;
	    	text-align: center;
	}

    .center{
    	width:600px;
    	margin:auto;
    	font-size: 30px;
    	text-align: center;
    }
    

    .movie-rank {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .movie-rank span {
        font-size: 1.5rem;
        margin-bottom: 5px;
    }

    .movie-poster img {
        width: 150px; /* 원하는 이미지 폭 설정 */
        height: auto;
    }
</style>
<body>	
<%
	HttpSession session2 = request.getSession();
	String username = (String)session2.getAttribute("username");
%>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <a class="navbar-brand" href="movies.jsp">Web시네마</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
    
       <li class="nav-item">
        <a class="nav-link" href="./cart.jsp">장바구니</a>
       </li>
		<%
			if (username != null) {
				out.print("<a class=\"navbar-brand\" href=\"./welcome.jsp\">" + username + "</a>");
			} else {
		%>
      <li class="nav-item">
        <a class="nav-link" href="./login.jsp">로그인</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./register.jsp">회원가입</a>
      </li>
      	<%
      	}
		%>
    </ul>
  </div>  
</nav>
<div class="jumbotron">
    <h3 class="title"><%= "예매율 순위" %></h3>
    </div>
<div class="center">
	
    <div class="movie-rankings">
        <div class="movie-rank card-container">
            <span>1</span>
            <div class="movie-poster">
                <img src="resources/images/avatar.jpg" alt="아바타 ">
            </div>
        </div>
        <div class="movie-rank card-container">
            <span>2</span>
            <div class="movie-poster">
                <img src="resources/images/titanic.jpg" alt="타이타닉">
            </div>
        </div>
        <div class="movie-rank card-container">
            <span>3</span>
            <div class="movie-poster">
                <img src="resources/images/ironman.jpg" alt="아이언맨">
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
							<%
								int subLen = 20;
								String desc = movie.getDescription();
								
								if (desc.length() > subLen) {
									desc = desc.substring(0, subLen);
									desc += "...";
								}
								
							%>
                <p style="white-space: no-wrap; overflow: hidden; text-overflow: ellipsis;"><%= desc %></p>
                <p><a href="./movie.jsp?id=<%=movie.getMovieId()%>" class="btn btn-secondary btn-red" role="button"> 상세 정보 &raquo;</a>
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
