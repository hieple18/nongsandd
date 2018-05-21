<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
				<c:set var="notifyCount" value="${fn:length(notifies)}" />
				<div class="top-nav notification-row">
					<!-- notification drop down start-->
					<ul class="nav pull-right top-menu">
						<!--notification end -->
						<!-- alert notification start-->
						<li id="alert_notificatoin_bar" class="dropdown">
						<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i
								class="icon-bell-l"></i> <c:if test="${notifyCount > 0}">
									<span class="badge bg-important"> ${notifyCount}</span>
								</c:if>
						</a>
							<ul class="dropdown-menu extended notification">
								<div class="notify-arrow notify-arrow-blue"></div>
								<li>
									<p class="blue">Bạn có ${notifyCount} Thông báo mới</p>
								</li>
								<c:if test="${notifyCount > 0}">
									<c:forEach var="notify" items="${notifies}">
										<li><div class="notify-content" onclick="updateNotify(${notify.id})"> 
											<span>${notify.content}</span>
											<span class="small italic pull-right">${notify.timeAgo}</span>
											<br><a class="notify-url" href="${notify.link}"><i><u>Chi Tiết</u></i></a>
										</div></li>
									</c:forEach>
								</c:if>
								<li><a href="#">Xem tất cả các thông báo</a></li>
							</ul></li>
						<!-- alert notification end-->
						<!-- user login dropdown start-->
						<li class="dropdown"><a data-toggle="dropdown"
							class="dropdown-toggle" href="#"> <span class="profile-ava">
									<img alt=""
									src="/NongSanDD/resources/images/notify/user_icon.png">
							</span> <span class="username">Jenifer Smith</span> <b class="caret"></b>
						</a>
							<ul class="dropdown-menu extended logout">
								<div class="log-arrow-up"></div>
								<li class="eborder-top"><a href="/NongSanDD/NhaBuon/thong-tin-tai-khoan"><i
										class="icon_profile"></i> Tài Khoản</a></li>
								<li><a href="/NongSanDD/dang-xuat"><i class="icon_key_alt"></i>
										Đăng Xuất</a></li>
							</ul></li>
						<!-- user login drop down end -->
					</ul>
					<!-- notification drop down end-->
				</div>

			</div>
		</div>
		<!-- End of .bottom_header -->
	</div>
</header>

<script>
function updateNotify(id){
	var currentNotifyCount = ${notifyCount};
	$.ajax({
		type : "GET",
		data : {
			notifyID : id
		},
		url : "/NongSanDD/NhaBuon/huy-thong-bao",
		success : function(response) {
			if(currentNotifyCount > 1){
				$(".bg-important").val(currentNotifyCount--);
			}else{
				$(".bg-important").remove();
			}
		}
	});
}
</script>

<!-- Menu ******************************* -->
<div class="theme_menu color1_bg">
	<div class="container" style="height: 50px;">
		<nav class="menuzord pull-left" id="main_menu">
			<ul class="menuzord-menu">
				<li><a href="/NongSanDD/NhaBuon">Trang Chủ</a></li>
				<li><a href="/NongSanDD/NhaBuon/gia-hom-nay">Danh Sách Giá</a></li>
				<li class="current_page"><a href="/NongSanDD/NhaBuon/">Nhà Buôn</a></li>
				<li><a href="/NongSanDD/NhaBuon/ds-tin-ban">Tin Bán</a></li>
				<li><a href="/NongSanDD/NhaBuon/ds-yeu-cau">DS Yêu Cầu</a></li>
				<li><a href="#">Giới Thiệu</a></li>
				<li><a href="#">Pages</a>
					<ul class="dropdown">
						<li><a href="Testimonials.html">Testimonials</a></li>
						<li><a href="404.html">404v page</a></li>
						<li><a href="faq.html">FAQ</a></li>
					</ul></li>
			</ul>
			<!-- End of .menuzord-menu -->
		</nav>

		<!-- End of #main_menu -->

	</div>
	<!-- End of .conatiner -->
</div>
<!-- End of .theme_menu -->