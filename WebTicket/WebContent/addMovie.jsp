<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link rel ="stylesheet" href ="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href = "./resources/css/movieStyle.css" />

<script type ="text/javascript" src ="./resources/js/validation.js"></script>
<title>영화 등록</title>
<style>
  #imagePreview img {
    width: 100%; 
    max-width: 300px; 
    height: 100%;
    margin-bottom: 10px; 
  }
  #imagePreview {
    display: inline-block; 
    vertical-align: top; 
  }
  .form-container {
    display: flex; 
  }
  
  .btn-red {
    background-color: #B22222; 
    color: white; 
    border: none; 
    
  }  
	
	.form-border {
	font-size: 20px;
	font-weight: 300px;
	background-color:  rgb(41, 78, 101);
	background-color: rgba(43, 78, 100, 0.5); 
	color: white;
    border: 4px solid rgb(43, 64, 100); 
    border-radius: 10px; 
    padding: 25px; 
    box-shadow: 5px 5px 10px rgba(43, 64, 100, 0.9); /* 오른쪽과 아래쪽에 그림자 추가 */
}

	.white-border {
		border: 2px solid white; 
	}
	
}
</style>
</head>
<body>
	<fmt:setLocale value='<%= request.getParameter("language") %>'/>
	<fmt:bundle basename="bundle.message" > 
	<jsp:include page="menu.jsp" />	
	<div class="jumbotron" >
		<div class="container">
			<h1 class="display-3"><fmt:message key="title" /></h1>
		</div>
	</div>
	
	<form name="newMovie" action="./processAddMovie.jsp" class="form-horizontal" method="post" enctype ="multipart/form-data">
	
	<div class="container form-container">
		
		<div class ="col-sm-6 text-center d-flex flex-column align-items-center">
				<br>
				<br>
				<br>
				<label><fmt:message key="movieImage"/></label>
				<div id="imagePreview" class="card-container">
					<input type="file" id="imageInput" name="movieImage" class="form-control">
					<div id="selectedImage"></div> 
				</div>
		</div>
		
		
		<div class="form-content col-sm-6">
		
			<div class="text-right"> 
				<a href="?language=ko" >한국어</a>|<a href="?language=en" >English</a>
				<a href="logout.jsp" class="btn btn-m btn-red pull-right">logout</a>   
			</div>	
		<br><br>
		
		<div class="form-border">
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="movieId"/></label>
				<div class="col-sm-4">
					<input type="text" id ="movieId" name="movieId" class="form-control" >
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="mname" /></label>
				<div class="col-sm-6">
					<input type="text" id ="title" name="title" class="form-control" >
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="price"/></label>
				<div class="col-sm-4">
					<input type="text" id ="price" name="price" class="form-control" >
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="description" /></label>
				<div class="col-sm-8">
					<textarea id="description" name="description" cols="50" rows="4"
						class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="supervisor"/></label>
				<div class="col-sm-6">
					<input type="text" id="supervisor" name="supervisor" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="manufacturer"/></label>
				<div class="col-sm-6">
					<input type="text" id="manufacturer" name="manufacturer" class="form-control">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="genre" /></label>
				<div class="col-sm-6">
					<input type="text" id="genre" name="genre" class="form-control" >
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-4"><fmt:message key="totalSeats" /></label>
				<div class="col-sm-2">
					<input type="text" id ="totalSeats" name="totalSeats" class="form-control" > 
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col-sm-8"></div>
				<div class="col-sm-4">
					<input type ="button" class="btn btn-red white-border" value="<fmt:message key="button" />" onclick ="CheckAddMovie()">
				</div>
			</div>
		</div>
		</div>
	</div>
	</form>
	</fmt:bundle>
	<script>
	 document.getElementById('imageInput').onchange = function(event) {
	        var reader = new FileReader();
	        reader.onload = function(e) {
	            var img = new Image();
	            img.src = e.target.result;
	            img.style.width = '100%';
	            img.style.maxWidth = '300px';
	            img.style.height = 'auto';
	            img.style.marginTop = '10px';

	            var imageContainer = document.getElementById('selectedImage');
	            imageContainer.innerHTML = ''; // 이전 이미지를 지우고 새 이미지를 추가
	            imageContainer.appendChild(img);
	        };
	        reader.readAsDataURL(event.target.files[0]);
	    };
</script>
</body>
</html>
