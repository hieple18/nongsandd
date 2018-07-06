<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/page-head.jsp"></jsp:include>
<div class="container">
	<form:form class="form-horizontal form-label-left" id="register_form" modelAttribute="user"
		action="/NongSanDD/TempND/tao-tk" style="padding-top: 50px">

		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Họ
				- Tên <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<form:input id="name" class="form-control col-md-7 col-xs-12" path="name"
					placeholder="Họ Và Tên" required="required" type="text"/>
			</div>
		</div>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">Tuổi</label>
			<div class="col-md-2 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<form:input type="number" class="form-control col-md-5 col-xs-12" path="age"/>
				<span class="input-group-addon">Tuổi</span>
			</div>

			<label class="control-label col-md-2 col-sm-3 col-xs-12" for="phone">Số
				Điện Thoại <span class="required">*</span></label>
			<div class="col-md-2 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<form:input type="tel" id="telephone" name="phone" required="required"
					class="form-control col-md-7 col-xs-12" path="phoneNum" />
			</div>
		</div>
		<input type="hidden" name="address.lat" id="map_lat" value="0"/>
		<input type="hidden" name="address.lng" id="map_lng" value="0"/>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="address">Tỉnh <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<input type="text" id="website" name="address" required="required"
					placeholder="Tỉnh Lâm Đồng" class="form-control col-md-7 col-xs-12"
					disabled>
			</div>
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="address">Huyện <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<select class="form-control col-xs-12">
					<option>Huyện Đơn Dương</option>
					<option>Huyện Khác</option>
				</select>
			</div>
		</div>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="address">Phường/Xã <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<form:select id="select-commune" name="communeID" path="commune"
					data-live-search="true" class="form-control col-xs-12">
					<option>--Chọn phường/xã--</option> 
					<c:forEach var="commune" items="${communes}">
						<option value="${commune.communeID}">${commune.name}</option>
					</c:forEach>
				</form:select>
			</div>
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="address">Thôn/Xóm <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12" id="hamle_op">
				<select class="form-control col-xs-12" id="select-village" name="hamletID"
					name="select-village" data-live-search="true">
					<!-- <option>--Chọn thôn/xóm--</option> -->
				</select>
			</div>
		</div>
		
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">Chọn
				trên bản đồ </label>
			<div class="panel-body-map col-md-6 col-sm-6 col-xs-12">
				<input id="pac-input" class="controls" type="text"
					placeholder="Search Box">
				<div id="upload-sale-map" style="height: 300px; width: 100%"></div>
			</div>
		</div>
		
		<div class="item form-group">
			<label for="password" class="control-label col-md-3">Mật Khẩu
				<span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input id="password" type="password" name="account.password"
					class="form-control col-md-7 col-xs-12" required="required" />
			</div>
		</div>
		<div class="item form-group">
			<label for="password2"
				class="control-label col-md-3 col-sm-3 col-xs-12">Nhập Lại
				Mật Khẩu <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<input id="password2" type="password" name="confirm_password"
					class="form-control col-md-7 col-xs-12" required="required">
			</div>
		</div>
		<div style="height: 20px" class="ln_solid"></div>
		<div class="form-group" style="text-align: center">
			<div class="col-md-6 col-md-offset-3">

				<button type="submit" class="btn btn-success">Đăng Kí</button>
				<a href="#" class="btn btn-primary" style="margin-left: 50px">Hủy
					Bỏ</a>
			</div>
		</div>
	</form:form>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>
<script>

	function validatePhoneNumber(phone) {
	    var flag = false;
	    phone = phone.replace('(+84)', '0');
	    phone = phone.replace('+84', '0');
	    if (phone != '') {
	        var firstNumber = phone.substring(0, 2);
	        if ((firstNumber == '09' || firstNumber == '08') && phone.length == 10) {
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
	
	/* function phoneNumNotActive(phoneNum){
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
	        			window.location.href = "/NongSanDD/TempND/kich-hoat-tk?phone="+phoneNum;
	        		}
	            },
	            'Xem thông tin tài khoản': {
	        		btnClass: 'btn-blue',
	        		action: function (){
	        			window.location.href = "/NongSanDD/TempND/tk-chua-kh?phone="+phoneNum;
	        		}
	            },
				'Hủy' : function() {
				}
			}
		});
	} */
	
	$(document).ready(function() {
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
				        break;
				    case -1:
				    	warningPhoneError('Số điện thoại đang chờ Nhà Buôn đăng kí.');
				        break;
				    case 0:
				    	warningPhoneError('Số điện thoại đang chờ Nhà Buôn đăng kí.');
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
		
		
		$("#telephone").change(function(){
			var phoneNum = $(this).val();
			if(!validatePhoneNumber(phoneNum)){
				$.alert({
                    title: 'Lỗi',
                    icon: 'fa fa-warning',
                    type: 'orange',
                    content: 'Số điện thoại ' + phoneNum + ' không hợp lệ. vui lòng nhập lại.',
                });
				
				$("#telephone").val("");
				$("#telephone").focus();
			}else{
				verifyUserPhone(phone);
			}
		});
		
		$('#register_form').validate({
			rules : {
				name : {
					required : true,
					minlength : 4,
					maxlength: 30
				},
				age : {
					maxlength: 3
				},
				phone : {
					required : true,
					minlength : 8
				},
				'account.password' : {
					required : true,
					minlength : 6,
					maxlength : 40
				},
				confirm_password : {
					required : true,
					equalTo : "#password"
				},
				hamletID : {
					required : true,
				}
			},
			messages : {
				name : {
					required : "Vui lòng nhập địa chỉ",
					minlength : "Tên Phải ít nhất 4 kí tự",
					maxlength : "Tên tối đa 30 kí tự"
				},
				age : {
					maxlength : "Tuổi tối đa 3 kí tự"
				},
				phone : {
					required : "Vui lòng nhập số điện thoại",
					minlength : "SDT Phải ít nhất 8 kí tự"
				},
				name : {
					required : "Vui lòng nhập tên",
					minlength : "Tên Phải ít nhất 2 kí tự"
				},
				'account.password' : {
					required : "Vui lòng nhập mật khẩu",
					minlength : "mật khẩu phải có ít nhất 6 kí tự",
					maxlength : "mật khẩu tối đa 40 kí tự"
				},
				confirm_password : {
					required : "Vui lòng nhập lại mật khẩu",
					equalTo : "Mật khẩu nhập lại không trùng"
				},
				hamletID : {
					required : "Vui lòng chọn",
				}
			}
		});
	});
</script>

<script>
var updateFlag = false;
var mapZoom = 10;
var currentLocation = {
		lat : 11.709757,
		lng : 108.478252
	};
</script>

<script type="text/javascript"
	src="/NongSanDD/resources/js/init-google_map.js"></script>