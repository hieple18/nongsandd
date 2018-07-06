<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- Header *******************************  -->
<header>
	<div class="bottom_header">
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-sm-5 col-xs-6 logo-responsive">
					<div class="logo-area">
						<a href="/NongSanDD/" class="pull-left logo"><img
							src="/NongSanDD/resources/images/logo/logo.png" alt="LOGO"></a>
					</div>
				</div>

				<div class="col-md-4 col-sm-12 col-xs-12" style="float: right">
					<div class="autor">
						<a href="/NongSanDD/NguoiDung">Đăng nhập</a>
						<a href="/NongSanDD/TempND/dang-ki-tk">Tạo Tài Khoản</a>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- End of .bottom_header -->
</header>

<!-- Menu ******************************* -->
<div class="theme_menu color1_bg">
	<div class="container" style="height: 50px;">
		<nav class="menuzord pull-left" id="main_menu">
			<ul class="menuzord-menu">
				<li id="price-page"><a href="/NongSanDD/gia-hom-nay?page=1">Danh Sách Giá</a></li>
				<li id="list-page"><a href="/NongSanDD/ds-nha-buon">DS Nhà Buôn</a></li>
				<li><a href="#">Nhà Buôn</a>
					<ul class="dropdown">
						<li><a href="/NongSanDD/NhaBuon">Đăng Nhập</a></li>
						<li><a href="/NongSanDD/TempNB/yc-xac-thuc-sdt">Tạo Tài Khoản</a></li>
					</ul></li>
			</ul>
			<!-- End of .menuzord-menu -->
		</nav>

		<!-- End of #main_menu -->

	</div>
	<!-- End of .conatiner -->
</div>
<script>
var pathname = window.location.pathname;
if(pathname.indexOf("gia-hom-nay") !== -1){
	$("#price-page").addClass("current_page");
}else if(pathname.indexOf("ds-nha-buon") !== -1){
	$("#list-page").addClass("current_page");
}
</script>
<!-- End of .theme_menu -->