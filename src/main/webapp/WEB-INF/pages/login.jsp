<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<jsp:include page="form_page/page-head.jsp"></jsp:include>
<div style="padding: 30px; margin: auto;" >
	<security:authorize access="hasRole('ROLE_USER')">
		<span> Người Dùng Đăng Nhập</span>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_TRADER')">
		<span> Nhà Buôn Đăng Nhập</span>
	</security:authorize>
</div>
<div style="width: 350px; padding: 20px; margin: auto;">
	<form role="form" action="<%=request.getContextPath()%>/appLogin" method="post" id="login-form"
		autocomplete="off">
		<div class="form-group">
			<label for="usename" class="sr-only">Tên Đăng Nhập</label> <input type="text"
				name="username" class="form-control" placeholder="Tên Đăng Nhập">
		</div>
		<div class="form-group">
			<label for="pass" class="sr-only">Mật Khẩu</label> <input
				type="password" id="key" name="password" class="form-control"
				placeholder="Mật Khẩu">
		</div>
		<div class="checkbox">
			<span class="character-checkbox" onclick="showPassword()"></span> <span
				class="label">Hiện Mật Khẩu</span>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" id="btn-login"
			class="btn btn-custom btn-lg btn-block" value="Đăng Nhập">
	</form>
	<a href="#" class="forget" data-toggle="modal"
		data-target=".forget-modal">Quên Mật Khẩu?</a>
</div>
<jsp:include page="form_page/page-foot.jsp"></jsp:include>