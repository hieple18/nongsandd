<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/page-head.jsp"></jsp:include>
<div class="container">
	<p class="tital-verify">Nhập SDT đã được quản trị xác nhận</p>
	<div style="width: 350px; padding: 20px; margin: auto;">
		<form role="form" action="/NongSanDD/TempNB/dang-ki-tk"
			id="verify-form">
			<div class="form-group">
				<label for="pass" class="sr-only">Nhập Số điện thoại</label> <input
					type="text" id="phone" name="phone" class="form-control"
					placeholder="Nhập Số điện thoại">
			</div>
		</form>
		<button id="submit" class="btn btn-success" onclick="submit()"
			style="float: left;">Đăng kí</button>
	</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script>
function validatePhoneNumber(phone) {
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

function warningPhoneError(content){
	$.alert({
        title: 'Lỗi',
        icon: 'fa fa-warning',
        type: 'orange',
        content: content,
    });
	$("#phone").val("");
	$("#phone").focus();
}

function phoneNumNotActive(phoneNum){
	$.confirm({
		content : 'Số điện thoại ' + phoneNum + 'đã được đăng kí nhưng chưa được kích hoạt?',
		icon : 'fa fa-question-circle',
		title: 'Xác nhận',
		animation : 'scale',
		closeAnimation : 'scale',
		type: 'orange',
		opacity : 0.5,
		buttons : {
			'kích hoạt tài khoản': {
        		btnClass: 'btn-blue',
        		action: function (){
        			window.location.href = "/NongSanDD/TempNB/kich-hoat-tk?phone="+phoneNum;
        		}
            },
            'Xem thông tin tài khoản': {
        		btnClass: 'btn-blue',
        		action: function (){
        			window.location.href = "/NongSanDD/TempNB/xn-tk-chua-kh?phone="+phoneNum;
        		}
            },
			'Hủy' : function() {
			}
		}
	});
}

function submit(){
	var phone = $("#phone").val();
	if(validatePhoneNumber(phone)){
		$.ajax({
			type: "get",
			data: {phoneNum : phone},
			url: "/NongSanDD/TempNB/kiem-tra-xt-sdt",
			success: function(response){
				switch(response) {
			    case -2:
			    	warningPhoneError('Số điện thoại này chưa được xác nhận bởi người quản trị.');
			        break;
			    case -1:
			    	$("#verify-form").submit();
			        break;
			    case 0:
			    	phoneNumNotActive(phone);
			        break;
			    case 1:
			    	warningPhoneError('Số điện thoại này chưa được xác nhận bởi người quản trị.');
			        break;
			    default:
			        alert("loi");
				}
			}
		});
	}else{
		warningPhoneError('Số điện thoại không hợp lệ. vui lòng nhập lại.');
	}
}
</script>