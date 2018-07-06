<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="form_page/page-head.jsp"></jsp:include>
<link rel="stylesheet" href="/NongSanDD/resources/css/profile.css">
<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/jquery-barrating/fontawesome-stars.css">
<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/jquery-barrating/fontawesome-stars-o.css">
<div class="container padding-bottom-3x mb-1" style="margin-top: 30px">
	<section class="about-story">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-sm-12">
					<input id="pac-input" class="controls" type="text"
						placeholder="Search Box">
					<div id="upload-sale-map" style="height: 300px; width: 100%"></div>
				</div>
				<div class="col-md-6 col-sm-12">
					<div class="about-text">
						<div class="theme_title">
							<h3>${trader.name }</h3>
						</div>
						<div class="icon-box">
							<div class="single-item">
								<div class="icon">
									<i class="icon-nature-1"></i>
								</div>
								<div class="count name color1">
									Tuổi:
									<c:if test="${trader.age > 0 }">${trader.age }</c:if>
								</div>
								<div class="name color1">
									SDT: <span style="color: #333"> ${trader.phoneNum }</span>
								</div>
							</div>
							<div class="single-item">
								<div class="icon">
									<i class="icon-nature-1"></i>
								</div>
								<div class="count name color1">
									Ngày Đăng ký: <span style="color: #333">
										${trader.dateCreate }</span>
								</div>
								<div class="name color1">
									Tin Đã Mua: <span style="color: #333"> 0</span>
								</div>
							</div>
							<div class="single-item">
								<div class="icon">
									<i class="icon-wheat"></i>
								</div>
								<div class="count name color1"
									style="display: -webkit-inline-box; margin-bottom: 5px">
									Nông sản kinh doanh: &nbsp &nbsp <span style="color: #333">
									<c:forEach var="item" items="${tradingAgris}">
										${item}, 
									</c:forEach>
									</span>
								</div>

							</div>
							
							<div class="single-item">
								<div class="icon">
									<i class="icon-pencil-1"></i>
								</div>
								<div class="count name color1"
									style="display: -webkit-inline-box; margin-bottom: 5px">
									Điểm Đánh Giá: &nbsp
									<c:if test="${cmtSize == 0}"><span style="color: #333"> Chưa có đánh giá</span></c:if> 
									<c:if test="${cmtSize > 0}"> &nbsp
										<select id="sum-start-rating">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
										</select>
									</c:if>
								</div>
								<div class="name color1">
									Lượt Đánh Giá: <span style="color: #333"> ${cmtSize} Lượt</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<a href="http://localhost:8080/NongSanDD/NguoiDung/thong-tin-nb?id=1" class="btn btn-primary"
					style="float: right; margin-top: 20px">
					<i class="fas fa-plus"> Thêm Đánh Giá</i>
				</a>
				
				<div class="col-sm-12">

					<c:forEach var="cmt" items="${cmts}">
						<hr />
						<div class="review-block">
							<div class="row">
								<div class="col-sm-2">
									<div class="review-block-name">
										<a >${cmt.user.name }</a>
									</div>
									<div class="review-block-date">${cmt.dateCreate }</div>
								</div>
								<div class="col-sm-10">
									<div class="review-block-rate">
										<select class="start-rating"
											data-current-rating="${cmt.ratingCount }">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
										</select>
									</div>
									<div class="review-block-description">${cmt.content }</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
</div>
<jsp:include page="form_page/page-foot.jsp"></jsp:include>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>
<script type="text/javascript"
	src="/NongSanDD/resources/vendor/jquery-barrating/jquery.barrating.js"></script>

<script>

$(function() {
	$('#sum-start-rating').barrating({
		theme: 'fontawesome-stars-o',
		initialRating: ${trader.ratingSum},
		readonly: true
	});
});

$(".start-rating").each(function(){
	$(this).barrating({
		theme: 'fontawesome-stars',
		initialRating: ${trader.ratingSum},
		readonly: true
	});
})


var adress_sale_upload = "Đơn Dương, Lâm Đồng";
var myMarker = null;
var currentLocation = {
		lat : ${address.lat},
		lng : ${address.lng}
	};
function initialize() {
	var map = new google.maps.Map(document.getElementById('upload-sale-map'), {
		center : currentLocation,
		zoom : 15,
		mapTypeId : 'roadmap'
	});
	placeMarkerAndPanTo(currentLocation, map);

	var input = document.getElementById('pac-input');
	var searchBox = new google.maps.places.SearchBox(input);
	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	$('#pac-input').val("${address.hamlet.address}");

	// Bias the SearchBox results towards current map's viewport.
	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});

	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();

		if (places.length == 0) {
			return;
		}

		var markers = [];
		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		markers = [];

		// For each place, get the icon, name and location.
		var bounds = new google.maps.LatLngBounds();
		places.forEach(function(place) {
			if (!place.geometry) {
				console.log("Returned place contains no geometry");
				return;
			}
			var icon = {
				url : place.icon,
				size : new google.maps.Size(71, 71),
				origin : new google.maps.Point(0, 0),
				anchor : new google.maps.Point(17, 34),
				scaledSize : new google.maps.Size(25, 25)
			};

			// Create a marker for each place.
			markers.push(new google.maps.Marker({
				map : map,
				icon : icon,
				title : place.name,
				position : place.geometry.location
			}));

			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});

		map.fitBounds(bounds);
	});
}
google.maps.event.addDomListener(window, 'load', initialize);



function placeMarkerAndPanTo(latLng, map) {
	if (myMarker != null) {
		myMarker.setMap(null);
	}
	myMarker = new google.maps.Marker({
		position : latLng,
		map : map
	});
	map.panTo(latLng);
}



</script>