<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<title>회원가입</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">회원가입</h1>
		</div>
	</div>
	<div class="container" align="center">
		<p>회원 가입을 위한 아이디와 비밀번호를 입력해주세요
		<div class="col-md-5 col-md-offset-5">
			<%
				String error = request.getParameter("error");
				if (error != null) {
					out.println("<div class='alert alert-danger'>");
					out.println("이미 존재하는 id입니다.");
					out.println("</div>");
				}
			%>
			<form class="form-signin" action="./registerProcess.jsp" method="post">
				<div class="form-group">
					<label for="inputUserName" class="sr-only">ID - 영문 대소문자</label> 
					<input	type="text" class="form-control" placeholder="ID"  pattern="[a-zA-Z]+"	name='username' required autofocus>
				</div>
				<div class="form-group">
					<label for="inputPassword" class="sr-only">Password - 최소 9자 이상, 20자 이하, 영문, 숫자, 기호 조합</label> 
					<input 	type="password" class="form-control" placeholder="Password" pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{9,20}$" name='password' required>
				</div>
				<button class="btn btn btn-lg btn-success btn-block" type="submit">가입하기</button>
			</form>
		</div>
	</div>
</body>
</html>