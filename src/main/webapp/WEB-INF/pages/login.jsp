<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="form_page/page-head.jsp"></jsp:include>
<style type="text/css">
.tital-warning {
	text-align: -webkit-center;
	margin: 20px;
	font-size: 20px;
	color: red;
}
.tital-login {
	text-align: -webkit-center;
	margin: 20px;
	color: #278c29;
}
</style>
<div class="container">
	<h1 class="tital-login">Đăng Nhập</h1>
	<security:authorize access="hasRole('ROLE_USER')">
		<p class="tital-verify"> Bạn đang đăng nhập với quyền người
			dùng. để trở lại nhấn <a href="/NongSanDD/NguoiDung/">vào đây</a>
		</p>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_TRADER')">
		<p class="tital-verify"> Bạn đang đăng nhập với quyền nhà buôn.
		 để trở lại nhấn <a href="/NongSanDD/NhaBuon/">vào đây</a>
		</p>
	</security:authorize>
	<p class="tital-warning">${message}</p>
	<div style="width: 350px; padding: 20px; margin: auto;">
		<form role="form" action="<%=request.getContextPath()%>/appLogin"
			method="post" id="login-form" autocomplete="off"
			style="padding-bottom: 20px;">
			<div class="form-group">
				<label for="usename" class="sr-only">Tên Đăng Nhập</label> <input
					type="text" name="username" class="form-control"
					placeholder="Tên Đăng Nhập">
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
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="submit" id="btn-login"
				class="btn btn-custom btn-lg btn-block" value="Đăng Nhập">
		</form>
		<a href="#" onclick="forgotPass()" class="forget" data-toggle="modal"
			data-target=".forget-modal">Quên Mật Khẩu?</a>
	</div>
</div>
<jsp:include page="form_page/page-foot.jsp"></jsp:include>

<script>
function forgotPass(){
	$.confirm({
		content: 'Loại tài khoản của bạn?',
        icon: 'fa fa-question-circle',
        title: 'Xác nhận',
        animation: 'scale',
        closeAnimation: 'scale',
        opacity: 0.5,
        type: 'green',
        buttons: {
        	'Nhà nông': {
        		btnClass: 'btn-blue',
        		action: function (){
        			window.location.href = "/NongSanDD/TempND/quen-mk";
        		}
            },
            'Nhà buôn' : {
            	btnClass: 'btn-blue',
        		action: function (){
        			window.location.href = "/NongSanDD/TempNB/quen-mk";
        		}
            },
            "Hủy" : function() {
            	
            }
        }
    });
}
</script>