<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.servlet.http.*" %>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // DB 연동을 위한 코드
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        // DataSource 
		String url = "jdbc:mysql://localhost:3306/webticketdb";
		String dbid = "root";
		String dbpw = "root123456";

		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);

        // SQL 쿼리 작성 및 실행
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            // 로그인 성공 시 세션 생성
            HttpSession session2 = request.getSession();
            session2.setAttribute("username", username);
            response.sendRedirect("./welcome.jsp");
        } else {
        	response.sendRedirect("login.jsp?error=1");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
