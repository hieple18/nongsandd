<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/page-head.jsp"></jsp:include>
<div class="container">
	<p class="tital-verify">Nhập số điện thoại của bạn</p>
	<div style="width: 350px; padding: 20px; margin: auto;">
		<form role="form" action="/NongSanDD/TempNB/doi-mk" method="post"
			id="verify_form">
			<div class="form-group">
				<label for="pass" class="sr-only">Nhập Số điện thoại: </label> <input
					type="text" id="phone" name="phone" class="form-control"
					placeholder="Nhập Số điện thoại">
			</div>
			<a href="#" class="btn btn-success" id="checkPhone" onclick="checkPhone()">Kiểm tra SDT</a>
			<div class="form-group">
				<label for="pass" class="sr-only">Mật khẩu mới: </label> <input
					type="password" id="pass" name="pass" class="form-control"
					placeholder="Nhập mk" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="repass" class="sr-only">Nhập lại mật khẩu: </label> <input
					type="password" id="repass" name="repass" class="form-control"
					placeholder="Nhập mk" readonly="readonly">
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> 
			<input type="submit" id="btn-login"
				class="btn btn-custom btn-lg btn-block" value="Đổi mật khấu">
		</form>
	</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script>
	$('#verify_form').validate({
		rules : {
			phone : {
				required : true,
			},
			pass : {
				required : true,
				minlength : 6
			},
			repass : {
				required : true,
				equalTo : "#pass"
			}
		},
		messages : {
			phone : {
				required : "Vui lòng nhập sdt",
			},
			pass : {
				required : "Vui lòng nhập mật khẩu",
				minlength : "mật khẩu phải có ít nhất 6 kí tự"
			},
			repass : {
				required : "Vui lòng nhập lại mật khẩu",
				equalTo : "Mật khẩu nhập lại không trùng"
			}
		}
	});
</script>

<script>
	function validatePhoneNumber(phone) {
		if(phone === "")
			return false;
		
		var flag = false;
		phone = phone.replace('(+84)', '0');
		phone = phone.replace('+84', '0');
		if (phone != '') {
			var firstNumber = phone.substring(0, 2);
			if ((firstNumber == '09' || firstNumber == '08')
					&& phone.length == 10) {
				if (phone.match(/^\d{10}/)) {
					flag = true;
				}
			} else if (firstNumber == '01' && phone.length == 11) {
				if (phone.match(/^\d{11}/)) {
					flag = true;
				}
			}
		}
		return flag;
	}
	

	function checkPhoneServerSide(phoneNum){
		$.ajax({
			type : "GET",
			data : {
				phoneNum : phoneNum
			},
			url : "/NongSanDD/TempNB/kiem-tra-sdt",
			success : function(response) {
				if(response === 1){
					$("#phone").attr("readonly", true);
					$("#checkPhone").remove();
					$("#pass").attr("readonly", false); 
					$("#repass").attr("readonly", false);
					
				}else{
					$.alert({
                        title: 'Lỗi',
                        icon: 'fa fa-warning',
                        type: 'orange',
                        content: 'Hệ thống không tìm thấy số điện thoại này',
                    });
					
					$("#phone").val("");
					$("#phone").focus();
				}
			}
		});
	}
	
	function checkPhone(){
		var phoneNum = $("#phone").val();
		if(!validatePhoneNumber(phoneNum)){
			$.alert({
                title: 'Lỗi',
                icon: 'fa fa-warning',
                type: 'orange',
                content: 'Số điện thoại không hợp lệ. vui lòng nhập lại.',
            });
			
			$("#phone").val("");
			$("#phone").focus();
		}else{
			checkPhoneServerSide(phoneNum);
		}
	}

</script>