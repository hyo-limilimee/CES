<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<%
	String cartId = session.getId();
%>
<title>장바구니</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">장바구니</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<table width="100%">
				<tr>
					<td align="left"><a href="./deleteCart.jsp?cartId=<%=cartId%>" class="btn btn-danger">삭제하기</a></td>
					<td align="right"><a href="./shippingInfo.jsp?cartId=<%= cartId %>" class="btn btn-success">주문하기</a></td>
				</tr>
			</table>
		</div>
		<div style="padding-top: 50px">
			<table class="table table-hover">
				<tr>
					<th>영화 제목</th>
					<th>가격</th>
					<th>수량</th>
					<th>소계</th>
					<th>비고</th>
				</tr>
				<%				
					int sum = 0;
					ArrayList<Movie> cartList = (ArrayList<Movie>) session.getAttribute("cartlist");
					if (cartList == null)
						cartList = new ArrayList<Movie>();

					for (int i = 0; i < cartList.size(); i++) { // 상품리스트 하나씩 출력하기
						Movie movie = cartList.get(i);
						int total = movie.getPrice() * movie.getQuantity();
						sum = sum + total;
				%>
				<tr>
					<td><%=movie.getMovieId()%> - <%=movie.getTitle()%></td>
					<td><%=movie.getPrice()%></td>
					<td><%=movie.getQuantity()%></td>
					<td><%=total%></td>
					<td><a href="./removeCart.jsp?id=<%=movie.getMovieId()%>" class="badge badge-danger">삭제</a></td>
				</tr>
				<%
					}
				%>
				<tr>
					<th></th>
					<th></th>
					<th>총액</th>
					<th><%=sum%></th>
					<th></th>
				</tr>
			</table>
			<a href="./products.jsp" class="btn btn-secondary"> &laquo; 쇼핑 계속하기</a>
		</div>
		<hr>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
