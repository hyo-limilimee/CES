<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>
<%@ page errorPage ="exceptionNoProductId.jsp"%>
<html>
<head>
<link rel ="stylesheet" href ="./resources/css/bootstrap.min.css" />
<title>상품 상세 정보</title>
<script type="text/javascript">
	function addToCart() {
		if (confirm("장바구니에 추가하시겠습니까?")) {
			document.addForm.submit();
		} else {		
			document.addForm.reset();
		}
	}
</script>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상영 정보</h1>
		</div>
	</div>
	<%
		String id = request.getParameter("id");
		MovieRepository dao = MovieRepository.getInstance();
		Movie movie = dao.getMovieById(id);
	%>
	<div class="container">
		<div class="row">
			<div class ="col-md-5">
				<img src="resources/images/<%=movie.getFilename()%>" style="width: 100%" />
			</div>
			<div class="col-md-6">
				<h3><%=movie.getTitle()%></h3>
				<p><b>장르</b> : <%=movie.getGenre()%>
				<p><b>감독</b> : <%=movie.getSupervisor()%>
				<p><b>제작사</b> : <%=movie.getManufacturer()%>
				<hr>
				<p><b>여석</b> : <%=movie.getRemainingSeats()%> / <%=movie.getTotalSeats()%>
				<hr>
				<p><b>가격</b> : <%=movie.getPrice()%> 원
				<p><form name="addForm" action="./addCart.jsp?id=<%=movie.getMovieId()%>" method="post">
					<a href="./cart.jsp" class="btn btn-warning"> 장바구니 &raquo;</a>
					<a href="#" class="btn btn-info" onclick="addToCart()"> 티켓 예매 &raquo;</a>
				</form>
			</div>
		</div>
		<hr>
		<h3>영화 소개</h3>
		<br>
		<p><%=movie.getDescription()%>
		<p><a href="./movies.jsp" class="btn btn-secondary"> 다른 티켓 보러가기 &raquo;</a>
		<hr>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
