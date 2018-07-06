<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="form_page/page-head.jsp"></jsp:include>
<div class="price_container container">
	<div>
		<table class="display nowrap responsive-table" id="list_trader">
			<caption>Danh Sách Nhà Buôn</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 20%">Tên Nhà Buôn</th>
					<th scope="col" style="width: 20%">Số DT</th>
					<th scope="col" style="width: 30%">Địa Chỉ</th>
					<th scope="col" style="width: 20%">Đánh Giá</th>
				</tr>
			</thead>
			<tbody>
 				<c:forEach var="trader" items="${traders}">
 				<tr>
					<td></td>
					<td scope="row"><a href="#" class="link_h" style="font-weight: bold; color: #577903" data="${trader.id}">
						${trader.name}</a></td>
					<td>${trader.phoneNum}</td>
					<td>${trader.address.address}</td>
					<td>${trader.ratingSum} Điểm</td>
				</tr>
				</c:forEach> 
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="form_page/page-foot.jsp"></jsp:include>

<script>
$(document).ready(function() {
	$(".link_h").on('click', function () { 
	    var id = $(this).attr('data');
	    window.location.href = "thong-tin-nb?id=" + id;
	});
	
	/* price-list.js */
	$('#list_trader').DataTable({
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
})
</script>