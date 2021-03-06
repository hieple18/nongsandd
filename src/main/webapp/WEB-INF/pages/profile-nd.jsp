<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="form_page/page-head.jsp"></jsp:include>
<div class="container">
	<form:form class="form-horizontal form-label-left" id="register_form" modelAttribute="user"
		action="/NongSanDD/TempND/cap-nhap-tt" style="padding-top: 50px">

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
					class="form-control col-md-7 col-xs-12" path="phoneNum" readonly="true"/>
			</div>
		</div>
		<input type="hidden" name="address.address" id="map_address" value="${user.address.address }"/>
		<input type="hidden" name="address.lat" id="map_lat" value="${user.address.lat }"/>
		<input type="hidden" name="address.lng" id="map_lng" value="${user.address.lng }"/> 
		<input type="hidden" name="address.id" value="${user.address.id }"/>
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
						<option <c:if test="${commune.communeID == user.commune}">selected="selected"</c:if>
							 value="${commune.communeID}">${commune.name}</option>
					</c:forEach>
				</form:select>
			</div>
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="address">Thôn/Xóm <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12" id="hamle_op">
				<select id="select-village" name="hamletID"
					data-live-search="true" class="form-control col-xs-12">
					<c:forEach var="hamlet" items="${hamlets}">
						<option <c:if test="${hamlet.hamletID == hamletID}">selected="selected"</c:if>
							 value="${hamlet.hamletID}">${hamlet.name}</option>
					</c:forEach>
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
				<input id="password2" type="password"
					class="form-control col-md-7 col-xs-12" required="required">
			</div>
		</div>
		<div class="ln_solid"></div>
		<div class="form-group" style="text-align: center">
			<div class="col-md-6 col-md-offset-3">

				<button type="submit" class="btn btn-success">Đăng Kí</button>
				<a href="#" class="btn btn-primary" style="margin-left: 50px">Hủy
					Bỏ</a>
			</div>
		</div>
	</form:form>
</div>
<jsp:include page="form_page/page-foot.jsp"></jsp:include>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>
<script>
	$(document).ready(function() {
		
		
		$('#register_form').validate({
			rules : {
				name : {
					required : true,
					minlength : 2
				},
				address : {
					required : true,
					minlength : 10
				},
				hamletID : {
					required : true,
				},
				password : {
					required : true,
					minlength : 6
				},
				confirm_password : {
					required : true,
					equalTo : "#password"
				}
			},
			messages : {
				address : {
					required : "Vui lòng nhập địa chỉ",
					minlength : "Địa chỉ Phải ít nhất 10 kí tự"
				},
				phone : {
					required : "Vui lòng nhập số điện thoại",
					minlength : "SDT Phải ít nhất 8 kí tự"
				},
				hamletID : {
					required : "Vui lòng chọn",
				},
				password : {
					required : "Vui lòng nhập mật khẩu",
					minlength : "mật khẩu phải có ít nhất 6 kí tự"
				},
				confirm_password : {
					required : "Vui lòng nhập lại mật khẩu",
					equalTo : "Mật khẩu nhập lại không trùng"
				}
			}
		});
	});
</script>

<script>
var updateFlag = true;
var previousAddress = "${user.address.address}";
var mapZoom = 15;
var currentLocation = {
		lat : ${user.address.lat},
		lng : ${user.address.lng}
	};
</script>

<script type="text/javascript"
	src="/NongSanDD/resources/js/init-google_map.js"></script>