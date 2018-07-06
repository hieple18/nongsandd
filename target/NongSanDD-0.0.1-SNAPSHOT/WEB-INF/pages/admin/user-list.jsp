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
					<header class="panel-heading no-border"> Người Dùng </header>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="col"></th>
								<th scope="col" style="width: 20%">Tên User</th>
								<th scope="col" style="width: 20%">Số DT</th>
								<th scope="col" style="width: 30%">Địa Chỉ</th>
								<th scope="col" style="width: 20%">Đánh Giá</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users}">
								<tr>
									<td></td>
									<td scope="row"><a href="#" class="link_h"
										style="font-weight: bold; color: #577903" data="${user.id}">
											${user.name}</a></td>
									<td>${user.phoneNum}</td>
									<td>${user.address.address}</td>
									<td>${user.ratingSum} Điểm</td>
								</tr>
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