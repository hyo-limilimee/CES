<%@ page contentType="text/html; charset=utf-8"%>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
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



