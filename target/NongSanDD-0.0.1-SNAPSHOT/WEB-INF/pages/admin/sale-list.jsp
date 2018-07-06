<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="row">
			<div style="width: 100%; margin: 50px auto 0">
				<section class="panel">
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
							</tr>
						</thead>
						<tbody>
							<c:set var="stt" scope="session" value="1" />
							<c:forEach var="sale" items="${sales}">
								<tr id="${mining.sale.id}">
									<td><c:out value="${mining.score}" /></td>
									<td><ul>
											<li><a
												href="/NongSanDD/admin/user-info?id=${sale.user.id}">${sale.user.name}</a></li>
											<li>${sale.user.phoneNum}</li>
										</ul></td>
									<td><ul>
											<li>${sale.agriculture.name}</li>
											<li>Giá: ${sale.price} (triệu)</li>
										</ul></td>
									<td>${sale.address.address}</td>
									<td>${sale.quantity}(Ngìn Cây)</td>
									<td>${sale.area}(Sào)</td>
									<td>${sale.dateCreate}</td>
								</tr>
								<c:set var="stt" scope="session" value="${stt + 1}" />
							</c:forEach>
						</tbody>
					</table>
				</section>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<jsp:include page="page-foot.jsp"></jsp:include>