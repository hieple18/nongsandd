<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/trader-head.jsp"></jsp:include>
<div class="price_container container">
	<div>
		<table class="display nowrap responsive-table" id="my_sale_table">
			<caption>Danh Sách Tin Bán</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 15%">Thông Tin</th>
					<th scope="col" style="width: 15%">Thông tin</th>
					<th scope="col" style="width: 15%">Vị Trí</th>
					<th scope="col" style="width: 15%">Số Lượng</th>
					<th scope="col" style="width: 15%">Diện Tích</th>
					<th scope="col" style="width: 15%">Ngày Đăng</th>
					<th scope="col" style="width: 10%">Hành Động</th>
				</tr>
			</thead>
			<tbody>
				<c:set var = "stt" scope = "session" value = "1"/>
				<c:forEach var="mining" items="${minings}">
				<tr id="${mining.sale.id}">
					<td><c:out value="${mining.score}"/></td>
					<td><ul>
						<li><a href="/NongSanDD/NhaBuon/thong-tin-nd?id=${mining.sale.user.id}">${mining.sale.user.name}</a></li>
						<li>${mining.sale.user.phoneNum}</li>
					</ul></td>
					<td><ul>
						<li>${mining.sale.agriculture.name}</li>
						<li>Giá: ${mining.sale.price} (triệu)</li>
					</ul></td>
					<td>${mining.sale.address.address}</td>
					<td>${mining.sale.quantity} (Ngìn Cây)</td>
					<td>${mining.sale.area} (Sào)</td>
					<td>${mining.sale.dateCreate}</td>
					<td>
						<select class="form-control form-control-lg action_option">
							<option selected disabled>Chọn</option>
							<option value="/NongSanDD/NhaBuon/chi-tiet-tin-ban?id=${mining.sale.id}&state=1"> Chi tiết</option>
							<option value="${mining.sale.id}"> Gửi Yêu Cầu</option>
						</select>
					</td>
				</tr>
				<c:set var = "stt" scope = "session" value = "${stt + 1}"/>
				</c:forEach>
			</tbody>
		</table>
	</div>
		<div id="my_sale_slide_img" style="display:none;"> 	
		</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<!-- user/index -->

<script type="text/javascript">
	function sendRequest(id){
		$.ajax({
			type : "GET",
			data : {
				saleID : id
			},
			url : "/NongSanDD/NhaBuon/gui-yeu-cau",
			success : function(response) {
				$.alert({
	                content: 'Gửi yêu cầu thành công',
	                icon: 'fas fa-check',
	                animation: 'scale',
	                closeAnimation: 'scale',
	                buttons: {
	                    okay: {
	                        text: 'Đóng',
	                        btnClass: 'btn-blue'
	                    }
	                }
	            });
				$("#"+id).remove();
			}
		});
	}
	
	$("#my_sale_table").on('change','.action_option', function () { 
	    var value = $(this).val();
	    if (value >= 0) {
	    	$.confirm({
		        content: 'Bạn có đồng ý gửi yêu cầu mua nông sản này cho Nhà Nông không?',
		        icon: 'fa fa-question-circle',
		        animation: 'scale',
		        closeAnimation: 'scale',
		        opacity: 0.5,
		        buttons: {
		            'Đồng Ý': function (){
		            	sendRequest(value);
		            },
		            cancel: function () {
		            }
		        }
		    });
		} else {
			window.location.href = this.value;
		}
	});

</script>

<script type='text/javascript'>
$(document).ready(function() {
	$('.option_action').chosen();
});
	
	$('#my_sale_table').DataTable({
        responsive: true,
        "aaSorting": [],
		"bLengthChange" : false,
		"bFilter" : true,
		"bInfo" : true,
		"bAutoWidth" : false,
		"columnDefs" : [ {
			"targets" : [ 0, 1, 6],
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
		$.when(appendLink(saleID)).done(function(){
			alert("im here");
			api.toggleFullscreen(); 
		});
	}
	
	
</script>
