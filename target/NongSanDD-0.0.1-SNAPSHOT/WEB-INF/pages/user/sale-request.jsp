<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/user-head.jsp"></jsp:include>
<!-- gallery -->
<link rel='stylesheet'
	href='/NongSanDD/resources/vendor/gallery/unite-gallery.css'
	type='text/css' />
<link rel='stylesheet'
	href='/NongSanDD/resources/vendor/gallery/ug-theme-default.css'
	type='text/css' />
<div class="price_container container">
	<div>
		<table class="display nowrap responsive-table" id="sale_request">
			<caption>Danh Sách Yêu Cầu</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 15%">Tên Nhà Buôn</th>
					<th scope="col" style="width: 30%">Địa Chỉ</th>
					<th scope="col" style="width: 30%">SDT</th>
					<th scope="col" style="width: 17%">Điểm Đánh Giá</th>
					<th scope="col" style="width: 18%">Ngày Yêu Cầu</th>
					<th scope="col" style="width: 15%"></th>
				</tr>
			</thead>
			<tbody>
				<c:set var = "stt" scope = "session" value = "1"/>
				<c:forEach var="request" items="${requests}">
				<tr>
					<td><c:out value="${stt}"/></td>
					<td><a href="/NongSanDD/NguoiDung/thong-tin-nb?id=${request.trader.id }">${request.trader.name }</a></td>
					<td>${request.trader.name }</td>
					<td>${request.trader.phoneNum }</td>
					<td>${request.trader.ratingSum }</td>
					<td>${request.dateRequest}</td>
					
					<td><a href="#" data="${request.id}" class="btn btn-info confirm_request"><i class="fas fa-check-circle"></i>
						 Xác Nhận</a></td>
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

<script type='text/javascript'>
$(document).ready(function() {
	$('.option_action').chosen();
	
	$("#sale_request").on('click','.confirm_request', function () {
		var id = $(this).attr('data');
		
		$.confirm({
	        content: 'Bạn có muốn các nhận yêu cầu của Nhà buôn',
	        icon: 'fa fa-question-circle',
	        animation: 'scale',
	        closeAnimation: 'scale',
	        opacity: 0.5,
	        buttons: {
	            'Đồng Ý': function (){
	            	cofirmRequest(id);
	            	
	            },
	            cancel: function () {
	            }
	        }
	    });
	})
});

function cofirmRequest(id){
	window.location.href = "NguoiDung/xac-nhan-yeu-cau?id="+id;
}
	
	$('#sale_request').DataTable({
		rowReorder: {
            selector: 'td:nth-child(2)'
        },
        responsive: true,
		"bLengthChange" : false,
		"bFilter" : true,
		"bInfo" : true,
		"bAutoWidth" : false,
		"columnDefs" : [ {
			"targets" : [ 0, 1, 5],
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
	
</script>
