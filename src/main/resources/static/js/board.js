let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{//this를 바인딩하기위해.
			this.save();
		});
		$("#btn-delete").on("click", ()=>{//this를 바인딩하기위해.
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{//this를 바인딩하기위해.
			this.update();
		});
		$("#btn-reply-save").on("click", ()=>{//this를 바인딩하기위해.
			this.replySave();
		});
	},
	save: function() {
		//alert('user의 save함수 호출됨.');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		$.ajax({
		type: "POST",
		url: "/api/board",
		data: JSON.stringify(data),//http body 데이터
		contentType: "application/json; chaset=utf-8", //body 데이터가 어떤 타입인지
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("게시물 등록이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	},
	deleteById: function() {
		let id = $("#id").text();
		$.ajax({
		type: "DELETE",
		url: "/api/board/" + id,
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	},
	update: function() {
		let id = $("#id").val();
				
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		$.ajax({
		type: "PUT",
		url: "/api/board/" + id,
		data: JSON.stringify(data),//http body 데이터
		contentType: "application/json; chaset=utf-8", //body 데이터가 어떤 타입인지
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("게시물 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	},
	replySave: function() {

		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
						
		$.ajax({
		type: "POST",
		url: `/api/board/${data.boardId}/reply`,
		data: JSON.stringify(data),//http body 데이터
		contentType: "application/json; chaset=utf-8", //body 데이터가 어떤 타입인지
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("댓글 작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	},
	replyDelete: function(boardId, replyId) {						
		$.ajax({
		type: "DELETE",
		url: `/api/board/${boardId}/reply/${replyId}`,
		dataType: "json" //요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String (생긴게 json 이라면) => JavaScript 오브젝트로 변경
		}).done(function(resp){
			alert("댓글 삭제 성공!");
			location.href = `/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
	}
}
index.init();