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
				<form:form action="/NongSanDD/admin/save-update-price" method="post"
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
									<c:forEach items="${priceList.prices}" var="price"
										varStatus="status">
										<tr>
											<td align="center">${status.count}<input type="hidden"
											 name="prices[${status.index}].id" value="${price.id}" ></td>
											<td></td>
											<td>${price.agriculture.name}<input type="hidden"
											 name="prices[${status.index}].agriculture.id" value="${price.agriculture.id}" ></td>
											<td><input name="prices[${status.index}].price" value="${price.price}"/></td>
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