<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../form_page/user-head.jsp"></jsp:include>
<div class="price_container container">
	<div>
		<div class="col-md-10 col-xs-12" style="display: flex;">
			<div class="col-md-2 col-sm-6 col-xs-12 form-group has-feedback">
				<select class="form-control" id="select-category">
					<option value="0">Tất cả</option>
					<c:forEach var="item" items="${agriCategories}">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</div>
			<form class="col-md-4" action="gia-ngay-truoc" method="get">
				<div class="col-md-8 col-sm-6 col-xs-12 form-group has-feedback">
				<input type='text' class="form-control" id='price_datepicker' name="date" />
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12 form-group has-feedback">
					<button class="btn btn-success" type="submit">Cập Nhập</button>
				</div>
			</form>
			
		</div>
		<table class="display nowrap responsive-table" id="price_table">
			<caption>Giá Nông Sản ${title}</caption>
			<thead>
				<tr>
					<th scope="col"></th>
					<th scope="col" style="width: 25%">Tên Nông
						Sản</th>
					<th scope="col" style="width: 15%">Giá Hôm Nay</th>
					<th scope="col" style="width: 10%">Thay Đổi</th>
					<th scope="col" style="width: 15%">Cao Nhất Tháng Này</th>
					<th scope="col" style="width: 15%">Thấp Nhất Tháng này</th>
					<th scope="col" style="width: 15%">Trung Bình Tháng này</th>
				</tr>
			</thead>
			<tbody>
 				<c:forEach var="agriPrice" items="${agriPrices}">
					<tr>
					<td scope="row">${agriPrice[0]}</td>
					<td scope="row"><a href="#" class="link_h" style="font-weight: bold; color: #577903" data="${agriPrice[0]}">
						${agriPrice[1]}</a></td>
					<td>${agriPrice[3]}</td>
					<c:choose>
					    <c:when test="${agriPrice[4] == 0}">
					        <td>0</td>
					    </c:when> 
					    <c:when test="${agriPrice[4] > 0}">
					        <td style="background-color: #d3f5d7">+${agriPrice[4]}</td>
					    </c:when>   
					    <c:otherwise>
					        <td style="background-color: #fbdcdc">${agriPrice[4]}</td>
					    </c:otherwise>
					</c:choose>
					<td>18.000</td>
					<td>13.000</td>
					<td>14.500</td>
				</tr>
				</c:forEach> 
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7">Lưu ý: Đây là giá cho mặt hàng đẹp nhất. Đối
						với mỗi nhà buôn có thể chênh lệnh nhỏ so với giá hiển thị</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<jsp:include page="../form_page/page-foot.jsp"></jsp:include>

<script>
$(document).ready(function() {
	$(".link_h").on('click', function () { 
	    var id = $(this).attr('data');
	    window.location.href = "bieu-do-gia?id=" + id;
	});
	
	/* price-list.js */
	$('#price_table').DataTable({
		rowReorder: {
            selector: 'td:nth-child(2)'
        },
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

	$('#price_datepicker').datetimepicker({
		format : 'MM/DD/YYYY',
		defaultDate : new Date(),
		maxDate : moment(),
		minDate : "${minDate}"
	});
})
</script>