<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.oreilly.servlet.*"%>
<%@ page import="com.oreilly.servlet.multipart.*"%>
<%@ page import="java.util.*"%>
<%@ page import="dto.Movie"%>
<%@ page import="dao.MovieRepository"%>

<%
	request.setCharacterEncoding("UTF-8");

	String filename = "";
	String realFolder = "C:\\upload"; //웹 어플리케이션상의 절대 경로
	String encType = "utf-8"; //인코딩 타입
	int maxSize = 5 * 1024 * 1024; //최대 업로드될 파일의 크기5Mb

	MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

	// 전달할 데이터 
	String movieId = multi.getParameter("moiveId");
	String title = multi.getParameter("title");
	String unitPrice = multi.getParameter("price");
	String description = multi.getParameter("description");
	String manufacturer = multi.getParameter("manufacturer");
	String supervisor = multi.getParameter("supervisor");
	String genre = multi.getParameter("genre");
	String unitsInStock = multi.getParameter("totalSeats");
	// 예매 가격 문자열로 받아서 Integer로 변환 후 저장
	int price;

	if (unitPrice.isEmpty())
		price = 0;
	else
		price = Integer.parseInt(unitPrice);

	int totalSeats;

	if (unitsInStock.isEmpty())
		totalSeats = 0;
	else
		totalSeats = Integer.parseInt(unitsInStock);

	
	Enumeration files = multi.getFileNames();
	String fname = (String) files.nextElement();
	String fileName = multi.getFilesystemName(fname);
	
	
	MovieRepository dao = MovieRepository.getInstance();

	Movie newMovie = new Movie();
	newMovie.setMovieId(movieId);
	newMovie.setTitle(title);
	newMovie.setPrice(price);
	newMovie.setDescription(description);
	newMovie.setManufacturer(manufacturer);
	newMovie.setGenre(genre);
	newMovie.setSupervisor(supervisor);
	
	newMovie.setTotalSeats(totalSeats);
	newMovie.setFilename(fileName);

	dao.addMovie(newMovie);

	response.sendRedirect("movies.jsp");
%>
