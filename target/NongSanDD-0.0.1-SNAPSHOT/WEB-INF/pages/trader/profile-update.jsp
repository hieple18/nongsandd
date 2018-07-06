<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../form_page/trader-head.jsp"></jsp:include>
<div class="container">
	<form:form class="form-horizontal form-label-left" id="register_form"
		modelAttribute="trader" action="/NongSanDD/NhaBuon/cap-nhap-thong-tin" style="padding-top: 50px">

		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Họ
				- Tên <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<form:input id="name" class="form-control col-md-7 col-xs-12"
					path="name" placeholder="Họ Và Tên" required="required" type="text" />
			</div>
		</div>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">Nông
				Sản Kinh Doanh <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<select data-placeholder="Chọn Nông Sản ..." id="tradingAgri-select"
					multiple tabindex="6" class="form-control col-md-7 col-xs-12">
					<c:forEach var="category" items="${agriCategories}">
						<optgroup label="${category.name }">
							<c:forEach var="agri" items="${agris}">
								<c:if test="${agri.agriCategory.id == category.id }">
									<option value="${agri.id}">
										${agri.name}</option>
								</c:if>
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>
			</div>
		</div>
		<input type="hidden" name="tradingList" id="tradingList" />
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">Tuổi</label>
			<div class="col-md-2 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<form:input type="number" class="form-control col-md-5 col-xs-12"
					path="age" />
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
		<input type="hidden" name="address.address" id="map_address" value="${address.address }"/>
		<input type="hidden" name="address.lat" id="map_lat" value="${address.lat }"/>
		<input type="hidden" name="address.lng" id="map_lng" value="${address.lng }"/> 
		<input type="hidden" name="address.id" value="${address.id }"/>
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
						<option <c:if test="${commune.communeID == communeID}">selected="selected"</c:if>
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
						<option <c:if test="${hamlet.hamletID == address.hamlet.hamletID}">selected="selected"</c:if>
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
		<div class="ln_solid"></div>
		<div class="form-group" style="text-align: center">
			<div class="col-md-6 col-md-offset-3">

				<a onclick="submitRegisterForm()" class="btn btn-success">Cập Nhập</a>
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
<c:forEach var="item" items="${tradingList}">
	$('#tradingAgri-select  option[value="${item}"]').attr("selected","selected");
</c:forEach>
	
	$("#tradingAgri-select").chosen();
	function submitRegisterForm() {
		$("#tradingList").val($("#tradingAgri-select").chosen().val());
		$("#register_form").submit();
	}

	$(document).ready(function() {
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
				}
			}
		});
	});
</script>

<script>
var updateFlag = true;
var previousAddress = "${address.address}";
var mapZoom = 15;
var currentLocation = {
		lat : ${address.lat},
		lng : ${address.lng}
	};
</script>

<script type="text/javascript"
	src="/NongSanDD/resources/js/init-google_map.js"></script>