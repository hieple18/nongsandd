<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="row">
			<div style="width: 50%; margin: 50px auto 0">
				<section class="panel">
					<header class="panel-heading no-border"> Nhà Buôn đợi đăng kí </header>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th style="width: 10%">#</th>
								<th style="width: 35%">SDT</th>
								<th style="width: 35%">Ngày Thêm</th>
								<th style="width: 20%"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="stt" scope="session" value="1" />
							<c:forEach var="trader" items="${traders}">
								<tr>
									<td>${stt}</td>
									<td>${trader.phoneNum}</td>
									<td>${trader.dateCreate}</td>
									<td><div class="btn-group">
													
										<a class="btn btn-success" onclick="updateAgri(${trader.id}, '${trader.phoneNum}')" 
											href="#"><i class="fas fa-pencil-alt"></i></a>
										<a class="btn btn-danger" onclick="deleteAgri(${trader.id})" href="#"><i
											class="fas fa-trash-alt"></i></a>
									</div></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</section>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<!-- Modal -->
<div class="modal fade" id="updateAgri" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Sửa thông tin</h4>
			</div>
			
			<div class="modal-body">
				<form class="form-horizontal form-label-left" id="updateAgriForm"
					action="/NongSanDD/admin/update-trader-phone" method="get">
					<input type="hidden" id="traderID" name="id">
					<div class="form-group">
						<label class="col-sm-2 control-label">SDT</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="phone" id="phone_update"
								required="required">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default" onclick="submitUpdateAgriFrom()">Cập Nhập</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>

	</div>
</div>
<jsp:include page="page-foot.jsp"></jsp:include>
<script>
	function deleteAgri(id) {
		$.confirm({
	        content: 'Có chắc muốn xóa nông sản này không?',
	        icon: 'fa fa-question-circle',
	        type: 'warning',
	        animation: 'scale',
	        closeAnimation: 'scale',
	        opacity: 0.5,
	        buttons: {
	            'confirm': function (){
	            	window.location.href = "/NongSanDD/admin/delete-trader-phone?id=" + id;
	            	
	            },
	            cancel: function () {
	            	text: "Đóng"
	            }
	        }
	    });
	}
	function updateAgri(id, phone){
		$("#traderID").val(id);
		$("#phone_update").val(phone);
		$("#updateAgri").modal("show");
	}
	function submitUpdateAgriFrom(){
		$("#updateAgriForm").submit();
	}
	
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
		$("#phone_update").val("");
		$("#phone_update").focus();
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
	
	$("#phone_update").change(function(){
		var phone = $("#phone_update").val();
		if(validatePhoneNumber(phone)){
			verifyUserPhone(phone);
		}else{
			warningPhoneError('Số điện thoại không hợp lệ. vui lòng nhập lại.');
		}
	})

</script>