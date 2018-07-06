<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/page-head.jsp"></jsp:include>
<div class="container">
	<p class="tital-verify">Mã xác nhận đã được gửi về SDT: ${phone}</p>
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
</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script>

	function submit(){
		var phone = "${phone}";
		var code = $("#code").val();
		$.ajax({
			type: "POST",
			data: {phone: phone, code: code},
			url: "/NongSanDD/TempNB/xac-nhan-doi-mk",
			success: function(response){
				if(response === true){
					$.alert({
		                content: 'Đổi mật khẩu thành công',
		                icon: 'fas fa-check',
		                title: 'Thông báo',
		                animation: 'scale',
		                type: 'green',
		                closeAnimation: 'scale',
		                buttons: {
		                    okay: {
		                        text: 'Đóng',
		                        btnClass: 'btn-blue',
		                        action: function(){
		                        	window.location.href = "/NongSanDD/NhaBuon";
		                        }
		                    }
		                }
		            });
					
				}else{
					alert("mã xác nhận không chính xác")
				}
			}
		});
	}
</script>
