function CheckAddMovie() {

	var movieId = document.getElementById("movieId");
	var title = document.getElementById("title");
	var price = document.getElementById("price");
	var totalSeats = document.getElementById("totalSeats");
	var supervisor = document.getElementById("supervisor");
	var manufacturer = document.getElementById("manufacturer");
	var description =  document.getElementById("description");
	var genre =  document.getElementById("genre");
	
	
	// 영화 코드 체크
	if (!check(/^M[0-9]{4,11}$/, movieId,
			"[영화 코드]\nM와 숫자를 조합하여 5~12자까지 입력하세요\n첫 글자는 반드시 M로 시작하세요"))
		return false;
	// 영화 제목 체크
	if (title.value.length < 1 || title.value.length > 50) {
		alert("[영화 제목]\n최소 1자에서 최대 50자까지 입력하세요");
		title.select();
		title.focus();
		return false;
	}
	// 예매 가격 체크
	if (price.value.length == 0 || isNaN(price.value)) {
		alert("[예매 가격]\n숫자만 입력하세요");
		price.select();
		price.focus();
		return false;
	}

	if (price.value < 0) {
		alert("[예매 가격]\n음수를 입력할 수 없습니다");
		price.select();
		price.focus();
		return false;
	} else if (!check(/^\d+(?:[.]?[\d]?[\d])?$/, price,
			"[예매 가격]\n소수점 둘째 자리까지만 입력하세요"))
		return false;
	
	// 상세 설명 체크
	if (description.value.length < 1 || description.value.length > 300) {
		alert("[상세 설명]]\n최소 1자에서 최대 300자까지 입력하세요");
		description.select();
		description.focus();
		return false;
	}
		
	// 감독명 체크
	if (supervisor.value.length < 1 || supervisor.value.length > 50) {
		alert("[영화 감독]\n최소 1자에서 최대 50자까지 입력하세요");
		supervisor.select();
		supervisor.focus();
		return false;
	}
	
	// 제작사 체크
	if (manufacturer.value.length < 1 || manufacturer.value.length > 50) {
		alert("[제작사]\n최소 1자에서 최대 50자까지 입력하세요");
		manufacturer.select();
		manufacturer.focus();
		return false;
	}
	// 양화 장르 체크
	if (genre.value.length < 1 || genre.value.length > 50) {
		alert("[영화 장르]\n최소 1자에서 최대 50자까지 입력하세요");
		genre.select();
		genre.focus();
		return false;
	}
	
	
	// 좌석 수 체크
	if (totalSeats.value.length == 0 || isNaN(totalSeats.value)) {
		alert("[좌석 수]\n숫자만 입력하세요");
		totalSeats.select();
		totalSeats.focus();
		return false;
	}

	function check(regExp, e, msg) {

		if (regExp.test(e.value)) {
			return true;
		}
		alert(msg);
		e.select();
		e.focus();
		return false;
	}

	 document.newMovie.submit()
}
