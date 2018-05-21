<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<form action="/NongSanDD/admin/random-user" method="post" class = "form-vertical"
			enctype="multipart/form-data">
			<input type="file" name="file" accept=".xls,.xlsx"> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="random user">
		</form><br>
		
		<form action="/NongSanDD/admin/random-sale" method="post"
			enctype="multipart/form-data">
			<input type="file" name="file" accept=".xls,.xlsx"> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="random sale">
		</form><br>
		
		<form action="/NongSanDD/admin/random-request" method="post"
			enctype="multipart/form-data">
			<input type="file" name="file" accept=".xls,.xlsx"> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="random request">
		</form><br>
		
		<a href="/NongSanDD/admin/sale-expired">sale experid</a><br>
		
		<a href="/NongSanDD/admin/update-address">update address</a><br>
		
		<a href="/NongSanDD/admin/get-excel">get excel</a><br>
		
		<a href="/NongSanDD/admin/update-mining">update mining</a>
	</section>
</section>
<jsp:include page="page-foot.jsp"></jsp:include>