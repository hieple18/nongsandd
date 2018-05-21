<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/trader-head.jsp"></jsp:include>
<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/gallery/ug-theme-default.css" />
<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/gallery/unite-gallery.css" />
<div class="container padding-bottom-3x mb-1" style="margin-top: 30px">
	<div class="row">
		<!-- Poduct Gallery-->
		<div class="col-md-7">
			<div id="gallery" style="display:none;">
				<c:forEach var="link" items="${links}">
					<img src="${link}" />
				</c:forEach>
					 			 
			</div>
		</div>
		<!-- Product Info-->
 		<div class="col-md-5">

			<h2 class="padding-top-1x text-normal" style="margin-bottom: 30px">${sale.agriculture.name }</h2>
			<div class="row margin-top-1x">
				<div class="col-sm-4">
					<div class="form-group">
						<label for="size">Diện Tích</label> <input class="form-control"
							type="text" value="${sale.area}" readonly="readonly" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label for="color">Số Cây</label> <input class="form-control"
							type="text" value="${sale.quantity}" readonly="readonly" />
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<label for="quantity">Giá Khởi Điểm</label> <input
							class="form-control" type="text" value="${sale.price}"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<div class="row row-detail">
				<label>Người Bán: </label> <a href="/NongSanDD/NhaBuon/thong-tin-nd?id=${sale.user.id}">${sale.user.name}</a>
			</div>
			<div class="row row-detail">
				<label>Địa Điểm: </label> <span>${sale.address.address}</span>
			</div>
			<div class="row row-detail">
				<label>Ngày Đăng: </label> <span>${sale.dateCreate}</span>
			</div>
			<div class="row row-detail row-content">
				<label>Mô Tả: </label> <span></span>
			</div>
			<hr class="mb-3">
			<div class="d-flex flex-wrap justify-content-between">
				<div class="sp-buttons mt-2 mb-2" style="text-align: center">
					<c:if test="${state == 1 }">
						<a href="/NongSanDD/NguoiDung/chinh-sua?id=${sale.id}" class="btn btn-success btn-lg" style="margin: 10px">
						<i class="fas fa-phone-volume"></i> Gửi Yêu Cầu</a> </c:if>
					<c:if test="${state == 2 }">
						<a href="/NongSanDD/NguoiDung/chinh-sua?id=${sale.id}" class="btn btn-success btn-lg" style="margin: 10px">
						<i class="fas fa-times"></i> Hủy Yêu Cầu</a> </c:if>
					<c:if test="${state == 3 }">
						<a href="/NongSanDD/NguoiDung/chinh-sua?id=${sale.id}" class="btn btn-success btn-lg" style="margin: 10px">
						<i class="fas fa-trash-alt"></i> Xóa Tin Mua</a> </c:if>
					<a href="/NongSanDD/NhaBuon" class="btn btn-default btn-lg" style="margin-left: 20px">
						<i class="glyphicon glyphicon-arrow-left"></i> Trở Về</a>
				</div>
			</div>
		</div> 
	</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>

<!-- gallery thumbnail -->
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-common-libraries.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-functions.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-thumbsgeneral.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-thumbsstrip.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-touchthumbs.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-panelsbase.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-strippanel.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-gridpanel.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-thumbsgrid.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-tiles.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-tiledesign.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-avia.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-slider.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-sliderassets.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-touchslider.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-zoomslider.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-video.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-gallery.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-lightbox.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-carousel.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-api.js'></script>
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/gallery/ug-theme-default.js'></script>	
<!-- end gallery thumbnail -->

<script type="text/javascript">
	jQuery("#gallery").unitegallery({
		gallery_autoplay : true, //true / false - begin slideshow autoplay on start
		gallery_play_interval : 3000, //play interval of the slideshow
		gallery_pause_on_mouseover : true,
	});
</script>