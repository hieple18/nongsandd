<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="row">
			<div style="width: 40%; margin: 50px auto 0">
				<form role="form" action="/NongSanDD/admin/create-trader-phone"
					id="verify-form" method="post">
					<div class="form-group">
						<label class="sr-only">Mã Xác Nhận</label> <input type="text"
							name="phone" class="form-control" required="required" id="phone"
							placeholder="Nhập sdt">
						<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					</div>
				</form>
				<button onclick="submit()">Xác thực</button>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<jsp:include page="page-foot.jsp"></jsp:include>
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
	
	function verifyUserPhone(phone){
		$.ajax({
			type: "get",
			data: {phoneNum: phone},
			url: "/NongSanDD/TempND/kiem-tra-sdt",
			success: function(response){
				if(response === true){
					warningPhoneError('Số điện thoại đã được Nhà Nông đăng kí.');
				}else{
					verifyTraderPhone(phone);
				}
			}
		});
	}

	function verifyTraderPhone(phone){
		$.ajax({
			type: "get",
			data: {phoneNum : phone},
			url: "/NongSanDD/TempNB/kiem-tra-xt-sdt",
			success: function(response){
				switch(response) {
			    case -2:
			    	$("#verify-form").submit();
			        break;
			    case -1:
			    	warningPhoneError('Số điện thoại đang chờ Nhà Buôn đăng kí.');
			        break;
			    case 0:
			    	warningPhoneError('Số điện thoại đã được Nhà Buôn đăng kí nhưng chưa được xác nhận.');
			        break;
			    case 1:
			    	warningPhoneError('Số điện thoại đã được Nhà Buôn đăng kí.');
			        break;
			    default:
			        alert("loi");
				}
			}
		});
	}
	
	function submit(){
		var phone = $("#phone").val();
		if(validatePhoneNumber(phone)){
			verifyUserPhone(phone);
		}else{
			warningPhoneError('Số điện thoại không hợp lệ. vui lòng nhập lại.');
		}
	}
</script>