<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/page-head.jsp"></jsp:include>
<div class="container">
	<p class="tital-verify">${title}</p>
	<div style="width: 350px; padding: 20px; margin: auto;">
	<form role="form" action="#" id="login-form">
		<div class="form-group">
			<label for="pass" class="sr-only">Mã Xác Nhận</label> <input
				type="number" id="code" name="code" class="form-control"
				placeholder="Nhập mã xác nhận">
		</div>
	</form>
	<button id="submit" class="btn btn-success" onclick="submit()"
		style="float:left;">Xác thực</button>
	<button id="resendVerify" class="btn btn-success" 
		style="float:right">Gửi lại mã</button>
</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script>
	$("#code").change(function(){
		if($("#code").val() === ""){
			$('#submit').prop('disabled', true);
		}else{
			
			$('#submit').prop('enable', true);
		}
	})

	function submit(){
		var id = ${id};
		var code = $("#code").val();
		$.ajax({
			type: "POST",
			data: {id: id, code: code},
			url: "${action}",
			success: function(response){
				if(response){
					window.location.href = "${success}";
				}else{
					alert("mã xác nhận không chính xác")
				}
			}
		});
	}
	
	$("#resendVerify").click(function(){
		var id = ${id};
		$.ajax({
			type: "POST",
			data: {id: id},
			url: "/NongSanDD/TempND/gui-lai-ma-xn",
			success: function(response){
				$.alert({
	                content: 'Đã gửi lại mã xác nhận',
	                icon: 'fas fa-check',
	                animation: 'scale',
	                type: 'green',
	                closeAnimation: 'scale',
	                buttons: {
	                    okay: {
	                        text: 'Đóng',
	                        btnClass: 'btn-blue'
	                    }
	                }
	            });
			}
		});
	})
</script>
