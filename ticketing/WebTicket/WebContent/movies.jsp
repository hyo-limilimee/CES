<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>

<html>
<head>
    <title>영화 목록 및 트레일러</title>
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
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
    	/* margin-bottom: 5px; */
        font-size: 1.5rem;
        display: inline-block;
        border-radius : 50%;
        width : 30px;
        line-height : 30px;
        hegiht : auto;
        text-align : center;
        background-color : #ff0000;
    }

    
    .card-container h3{
    	text-align : center;
    }
    
    .card-container p{
    	text-align : center;
    	
    }
   
    .movie-poster-rank img {
        width: 100%; /* 원하는 이미지 폭 설정 */
        height: 150px; /* 원하는 이미지 높이 설정 */
        object-fit: cover; /* 이미지 비율 유지 및 빈 공간 없이 채우기 */
        margin-bottom: 10px;
        font-size : 20;
        
    }
    
    .movie-poster img{
    	width : 150px;
    	height : auto;
    }
    
    footer{
    position : fixed;
    bottom : 0;
    left : 0;
    }
    
    .btn-red{
    background-color : #008000;
    font-size: 0.8rem;
    }
    
 	.jumbotron {
        margin-bottom: 10px; /* 여백 제거 */
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
    <ul class="navbar-nav mr-auto"> <!-- Add mr-auto class here -->
       <li class="nav-item">
        <a class="nav-link" href="./cart.jsp">예매내역</a>
       </li>
       <li class="nav-item">
        <a class="nav-link" href="./addMovie.jsp">영화 등록</a>
       </li>
    </ul>
    <ul class="navbar-nav"> <!-- Separate ul for login and register -->
		<%
			if (username != null) {
				out.print("<a class=\"navbar-brand\" href=\"./orderConfirmation.jsp\">" + username + "</a>");
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
    <div class="row">
        <%
            int count = 0;
            for (Movie movie : listOfMovies) {
        %>
        <div class="col-md-4 mb-3">
            <div class="card-container">
                <div class="movie-poster-rank">
                    <img src="resources/images/<%= movie.getFilename() %>" class="img-fluid" >
                </div>
                <h3><%= movie.getTitle() %></h3>
               
                <p style="white-space: no-wrap; overflow: hidden; text-overflow: ellipsis;"></p>
                <p><a href="./movie.jsp?id=<%= movie.getMovieId() %>" class="btn btn-secondary btn-red" role="button"> 상세 정보 &raquo;</a></p>
            </div>
        </div>
        <%
            count++;
            // 한 줄에 3개씩 이미지를 출력하고 줄바꿈
            if (count % 3 == 0) {
        %>
    </div><div class="row">
        <%
            }
        }
        %>
    </div>
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
