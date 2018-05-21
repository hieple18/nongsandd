<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="row">
			<div style="width: 40%; margin: 50px auto 0">
				<form:form action="/NongSanDD/admin/create-price" method="post"
					modelAttribute="priceList">
					<section class="panel">
						<header class="panel-heading no-border"> Nông Sản </header>
						
							<table class="table table-bordered">
								<thead>
									<tr>
										<th style="width: 10%">#</th>
										<th style="width: 25%">Mục</th>
										<th style="width: 40%">Tên</th>
										<th style="width: 25%">Giá</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${agricultures}" var="agri"
										varStatus="status">
										<tr>
											<td align="center">${status.count}<input type="hidden"
											 name="list[${status.index}].agriID" value="${agri.id}" ></td>
											<td>${agri.agriCategory.name}</td>
											<td>${agri.name}</td>
											<td><input name="list[${status.index}].price" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
					</section>
					<input type="submit" value="Tạo Giá" style="float:right">
				</form:form>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<jsp:include page="page-foot.jsp"></jsp:include>