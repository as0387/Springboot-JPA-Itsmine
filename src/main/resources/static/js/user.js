let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{//this를 바인딩하기위해.
			this.save();
		});
	},
	save: function() {
		//alert('user의 save함수 호출됨.');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		};
		
		//console.log(data);
		//ajax호출시 default가 비동기 호출
		$.ajax({
		type: "POST",
		url: "/itsmine/api/user",
		data: JSON.stringify(data),//http body 데이터
		contentType: "application/json; chaset=utf-8", //body 데이터가 어떤 타입인지
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href = "/itsmine";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	}
}
index.init();