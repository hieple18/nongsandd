<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/user-head.jsp"></jsp:include>
<style>
<!--
.nav_side_content .icon_header li a {
    display: block;
    line-height: 40px;
    color: #fff;
    text-align: center;
    font-size: 14px;
}
.pull-right {
    float: right !important;
    display: -webkit-box;
}

.nav_side_content {
    margin-top: 5px;
    margin-bottom: 13px;
}
.nav_side_content .icon_header li {
    display: inline-block;
    width: 40px;
    height: 40px;
    background: transparent;
    border: 1px solid #7fb401;
    margin-right: 5px;
    border-radius: 50%;
}
-->
</style>
<div class="price_container container">
	<div>
		<table class="display nowrap responsive-table" id="user_index">
			<caption>Các Tin Đã Đăng</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 20%">Thông tin</th>
					<th scope="col" style="width: 15%">Số Lượng</th>
					<th scope="col" style="width: 15%">Diện Tích</th>
					<th scope="col" style="width: 15%">Ngày Đăng</th>
					<th scope="col" style="width: 15%">Số Yêu Cầu</th>
					<th scope="col" style="width: 15%">Hành Động</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="stt" scope="session" value="1" />
				<c:forEach var="sale" items="${sales}">
					<tr>
						<td><c:out value="${stt}" /></td>
						<td><ul>
								<li>${sale.agriculture.name}</li>
								<li>Giá: ${sale.price} (triệu)</li>
							</ul></td>
						<td>${sale.quantity}(NgìnCây)</td>
						<td>${sale.area}(Sào)</td>
						<td>${sale.dateCreate}</td>
						<td><c:if test="${sale.status == 2 }">Đã bán</c:if> <c:if
								test="${sale.status < 2 }">${sale.requestCount}</c:if></td>
						<td>
							<select class="form-control form-control-lg action_option">
								<option value="-1" disabled selected>Chọn</option>
								<c:if test="${sale.requestCount > 0 && sale.status < 2 }">
									<option value="/NongSanDD/NguoiDung/ds-yeu-cau?id=${sale.id}">DS Yêu
										Cầu</option>
								</c:if>
								<option value="/NongSanDD/NguoiDung/chi-tiet-tin-ban?id=${sale.id}">Chi Tiết</option>
								<option value="/NongSanDD/NguoiDung/chinh-sua-tin-ban?id=${sale.id}">Sửa</option>
								<c:if test="${sale.status < 2 }">
									<option value="${sale.id}">Xóa</option>
								</c:if>
							</select>
						</td>
					</tr>
					<c:set var="stt" scope="session" value="${stt + 1}" />
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="my_sale_slide_img" style="display: none;"></div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<!-- user/index -->

<script type="text/javascript">
	$("#user_index").on('change','.action_option', function () { 
	    var value = $(this).val();
	    if (value >= 0) {
			$.confirm({
				content : 'Bạn có muốn xóa tin bán này?',
				icon : 'fa fa-question-circle',
				title: 'Xác nhận',
				type: 'orange',
				animation : 'scale',
				closeAnimation : 'scale',
				opacity : 0.5,
				buttons : {
					'Đồng Ý': {
		        		btnClass: 'btn-blue',
		        		action: function (){
		        			window.location.href = "/NongSanDD/NguoiDung/xoa-tin-ban?id="
								+ value;
		        		}
		            },
					"Hủy" : function() {
					}
				}
			});
		} else {
			window.location.href = this.value;
		}
	    $(this).filter('[value=-1]') .attr('selected', true)
	});
</script>

<script type='text/javascript'>
	$(document).ready(function() {
		$('.option_action').chosen();
	});

	$('#user_index').DataTable({
		rowReorder : {
			selector : 'td:nth-child(2)'
		},
		responsive : true,
		"bLengthChange" : false,
		"bFilter" : true,
		"bInfo" : true,
		"bAutoWidth" : false,
		"columnDefs" : [ {
			"targets" : [ 0, 1 ],
			"orderable" : false
		} ],
		"language" : {
			search : "",
			"zeroRecords" : "Không Có Dữ Liệu Nào Được Tìm Thấy",
			"info" : "Trang _PAGE_ Trên _PAGES_ Trang",
			"emptyTable" : "Không Có Dữ Liệu",
			"infoEmpty" : "Hiện 0 Đến 0 Của 0 Dòng",
			"searchPlaceholder" : "Nhập Tên Nông Sản",
			"paginate" : {
				"first" : "Đầu",
				"last" : "Cuối",
				"next" : "Tiếp",
				"previous" : "Trước"
			},
		},
	});

	/* 	var api;
	 jQuery(document).ready(function() {
	 api = jQuery("#my_sale_slide_img").unitegallery();
	 $("#my_sale_slide_img").hide();

	 api.on("enter_fullscreen", function() { //on enter fullscreen
	 $("#my_sale_slide_img").show();
	 });

	 api.on("exit_fullscreen", function() { //on exit fulscreen
	 $("#my_sale_slide_img").hide();
	 });
	 }); */

	function appendLink(saleID) {
		var deferred = $.Deferred();
		$.ajax({
			type : "GET",
			data : {
				saleID : saleID
			},
			url : "/NongSanDD/NguoiDung/lay-link-tin-ban",
			success : function(response) {
				$("#my_sale_slide_img").empty();
				$("#my_sale_slide_img").html(response);

				setTimeout(function() {
					deferred.resolve();
				}, 5000);
			}
		});
		return deferred.promise();
	}

	function show_img_fullscreen(saleID) {
		/* $.ajax({
			type : "GET",
			data : {
				saleID : saleID
			},
			url : "/NongSanDD/NguoiDung/lay-link-tin-ban",
			success : function(response) {
				$("#my_sale_slide_img").empty();
				$("#my_sale_slide_img").html(response);
				
				setTimeout(function(){ api.toggleFullscreen(); }, 2000);
			}
		});  */
		$.when(appendLink(saleID)).done(function() {
			alert("im here");
			api.toggleFullscreen();
		});
	}
</script>
