<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.Movie" %>
<%@ page import="dao.MovieRepository" %>

<%
	String id = request.getParameter("id");
	if (id == null || id.trim().equals("")) {
		response.sendRedirect("movies.jsp");
		return;
	}

	MovieRepository dao = MovieRepository.getInstance();

	Movie movie = dao.getMovieById(id);
	if (movie == null) {
		response.sendRedirect("exceptionNoProductId.jsp");
	}

	ArrayList<Movie> goodsList = dao.getAllMovies();
	Movie goods = new Movie();
	for (int i = 0; i < goodsList.size(); i++) {
		goods = goodsList.get(i);
		if (goods.getMovieId().equals(id)) { 			
			break;
		}
	}
	
	ArrayList<Movie> list = (ArrayList<Movie>) session.getAttribute("cartlist");
	if (list == null) { 
		list = new ArrayList<Movie>();
		session.setAttribute("cartlist", list);
	}

	int cnt = 0;
	Movie goodsQnt = new Movie();
	for (int i = 0; i < list.size(); i++) {
		goodsQnt = list.get(i);
		if (goodsQnt.getMovieId().equals(id)) {
			cnt++;
			int orderQuantity = goodsQnt.getQuantity() + 1;
			goodsQnt.setQuantity(orderQuantity);
		}
	}

	if (cnt == 0) { 
		goods.setQuantity(1);
		list.add(goods);
	}

	response.sendRedirect("movie.jsp?id=" + id);
%>