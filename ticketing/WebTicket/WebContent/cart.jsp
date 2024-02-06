﻿<%@ page contentType="text/html; charset=utf-8"%>
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
<link rel = "stylesheet" href = "./resources/css/movieStyle.css"/>

<style>
	.font-black{
		color:black;	
	}
	
	.border-black{
		border: 3px solid rgb(43, 64, 100);
	border-radius: 10px; 	
	}
	
</style>

<body>
<%@ include file="/nav.jsp" %>
    <div class="jumbotron" style="background-color: #B22222;">
        <div class="container">
             <h1 class="display-3 text-white">장바구니</h1>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <table width="100%">
                <tr>
                    <td align="left"><a href="./deleteCart.jsp?cartId=<%=cartId%>" class="btn btn-danger">삭제하기</a></td>
                    <td align="right"><a href="./orderConfirmation.jsp" class="btn btn-success">주문하기</a></td>
                </tr>
            </table>
        </div>
        <div style="padding-top: 20px">
            <div class="row">
                <%
                    int sum = 0;
                    ArrayList<Movie> cartList = (ArrayList<Movie>) session.getAttribute("cartlist");
                    if (cartList == null)
                        cartList = new ArrayList<Movie>();

                    for (int i = 0; i < cartList.size(); i++) {
                        Movie movie = cartList.get(i);
                        int total = movie.getPrice() * movie.getQuantity();
                        sum = sum + total;
                %>
                <div class="col-md-4 mb-4 ">
                    <div class="card border card-container  ">
                        <img src="resources/images/<%=movie.getFilename()%>" style="width: 100%" />
                        <div class="card-body  font-black">
                            <h5 class="card-title"><%=movie.getTitle()%></h5>
                            <p class="card-text"><%=movie.getPrice()%>원</p>
                            <p class="card-text">수량: <%=movie.getQuantity()%></p>
                            <p class="card-text">소계: <%=total%>원</p>
                            <a href="./removeCart.jsp?id=<%=movie.getMovieId()%>" class="btn btn-red">삭제</a>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            <div class="row mt-3">
                <div class="col-md-12">
                    <div class="card card-container font-black">
                        <div class="card-body">
                            <h5 class="card-title">총액</h5>
                            <p class="card-text"><%=sum%>원</p>
                        </div>
                    </div>
                </div>
            </div>
            <a href="./movies.jsp" class="btn btn-secondary btn-red mt-3"> &laquo; 쇼핑 계속하기</a>
        </div>
        <hr>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>