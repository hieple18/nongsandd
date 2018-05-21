<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/trader-head.jsp"></jsp:include>
<div class="price_container container">
	<div>
		<table class="display nowrap responsive-table" id="sale_table">
			<caption>Các Tin Đã Mua</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 15%">Người Bán</th>
					<th scope="col" style="width: 20%">Thông tin</th>
					<th scope="col" style="width: 15%">Số Lượng</th>
					<th scope="col" style="width: 15%">Diện Tích</th>
					<th scope="col" style="width: 18%">Ngày Mua</th>
					<th scope="col" style="width: 17%">Hành Động</th>
				</tr>
			</thead>
			<tbody>
				<c:set var = "stt" scope = "session" value = "1"/>
				<c:forEach var="data" items="${datas}">
				<tr>
					<td><c:out value="${stt}"/></td>
					<td><a href="/NongSanDD/NhaBuon/thong-tin-nd?id=${data[1].user.id}">${data[1].user.name}</a></td>
					<td><ul>
						<li>${data[1].agriculture.name}</li>
						<li>Giá: ${data[1].price} (triệu)</li>
					</ul></td>
					<td>${data[1].quantity} (Ngìn Cây)</td>
					<td>${data[1].area} (Sào)</td>
					<td>"${data[2]}"</td>
					<td>
						<select class="form-control form-control-lg action_option">
							<option selected disabled>Chọn</option>
							<option value="NhaBuon/chi-tiet-tin-ban?id=${data[1].id}&state=3"> Chi tiết</option>
							<option value="${data[0]}"> Xóa</option>
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
function deleteSale(id){
	$.confirm({
        content: 'Nông sản này bạn đã mua rồi. Bạn có muốn xóa tin này không?',
        icon: 'fa fa-question-circle',
        animation: 'scale',
        closeAnimation: 'scale',
        opacity: 0.5,
        buttons: {
            'confirm': function (){
            	window.location.href = "xoa-tin-da-chon?id=" + id;
            	
            },
            cancel: function () {
            	text: "Đóng"
            }
        }
    });
}

$("#sale_table").on('change','.action_option', function () { 
    var value = $(this).val();
    if (value >= 0) {
    	deleteSale(value);
	} else {
		window.location.href = this.value;
	}
});
</script>

<script type='text/javascript'>
$(document).ready(function() {
	$('.option_action').chosen();
});
	
	$('#sale_table').DataTable({
        responsive: true,
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

	/* var api;
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
	
	function appendLink(saleID){
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
				
				setTimeout(function(){ deferred.resolve(); }, 5000);
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
		$.when(appendLink(saleID)).done(function(){
			alert("im here");
			api.toggleFullscreen(); 
		});
	}
</script>
