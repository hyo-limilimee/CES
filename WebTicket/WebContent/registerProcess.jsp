<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // DB 연동을 위한 코드
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        // DataSource 
		String url = "jdbc:mysql://localhost:3306/webticketdb";
		String dbid = "root";
		String dbpw = "root123456";

		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);

        // SQL 쿼리 작성 및 실행
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            out.println("<h2>회원가입 성공</h2>");
            // 회원가입 후 다른 페이지로 이동하거나 메시지를 출력할 수 있습니다.
            response.sendRedirect("welcome.jsp");
        } else {
            out.println("<h2>회원가입 실패</h2>");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
