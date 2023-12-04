<%
	HttpSession session2 = request.getSession();
	String username = (String)session2.getAttribute("username");
%>
<nav class="navbar navbar-expand  navbar-dark bg-dark">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="./welcome.jsp">Home</a>
			<%
				if (username != null) {
					out.print("<a class=\"navbar-brand\" href=\"./welcome.jsp\">" + username + "</a>");
				}
			%>
		</div>
	</div>
</nav>
