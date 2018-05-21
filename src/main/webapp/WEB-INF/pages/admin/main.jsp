<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<!--overview start-->
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<i class="fa fa-laptop"></i> Dashboard
				</h3>
				<ol class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
					<li><i class="fa fa-laptop"></i>Dashboard</li>
				</ol>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
				<div class="info-box blue-bg">
					<i class="fa fa-users"></i>
					<div class="count">${fn:length(users)}</div>
					<div class="title">Người Dùng</div>
				</div>
				<!--/.info-box-->
			</div>
			
			<!--/.col-->

			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
				<div class="info-box brown-bg">
					<i class="fa fa-user"></i>
					<div class="count">${fn:length(traders)}</div>
					<div class="title">Nhà Buôn</div>
				</div>
				<!--/.info-box-->
			</div>
			<!--/.col-->

			<%-- 			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
				<div class="info-box dark-bg">
					<i class="fa fa-thumbs-o-up"></i>
					<div class="count">${fn:length(sales)}</div>
					<div class="title">Order</div>
				</div>
				<!--/.info-box-->
			</div> --%>
			<!--/.col-->

			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
				<div class="info-box green-bg">
					<i class="fa fa-cubes"></i>
					<div class="count">${fn:length(sales)}</div>
					<div class="title">Đơn Hàng</div>
				</div>
				<!--/.info-box-->
			</div>
			<!--/.col-->
		</div>
		<!--/.row-->

		<div class="row">
			<div class="col-lg-9 col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h2>
							<i class="fa fa-map-marker red"></i><strong>Phân bố</strong>
						</h2>
						<div class="panel-actions">
							<img class="likei user_icon_active"
								src="/NongSanDD/resources/images/admin/user_icon_active.png"
								id="user_icon_img"> <img class="likei trader_icon_active"
								src="/NongSanDD/resources/images/admin/trader_icon_active.png"
								id="trader_icon_img"> <img class="likei sale_icon_active"
								src="/NongSanDD/resources/images/admin/sale_icon_active.png"
								id="sale_icon_img">
						</div>
					</div>
					<div class="panel-body-map">
						<input id="pac-input" class="controls" type="text"
							placeholder="Search Box">
						<div id="map" style="height: 400px; width: 100%"></div>
					</div>

				</div>
			</div>
			<div class="col-md-3">
				<!-- List starts -->
				<ul class="today-datas">
					<!-- List #1 -->
					<li>
						<!-- Graph -->
						<div>
							<span id="todayspark1" class="spark"></span>
						</div> <!-- Text -->
						<div class="datas-text">11,500 visitors/day</div>
					</li>
					<li>
						<div>
							<span id="todayspark2" class="spark"></span>
						</div>
						<div class="datas-text">15,000 Pageviews</div>
					</li>
					<li>
						<div>
							<span id="todayspark3" class="spark"></span>
						</div>
						<div class="datas-text">30.55% Bounce Rate</div>
					</li>
					<li>
						<div>
							<span id="todayspark4" class="spark"></span>
						</div>
						<div class="datas-text">$16,00 Revenue/Day</div>
					</li>
					<li>
						<div>
							<span id="todayspark5" class="spark"></span>
						</div>
						<div class="datas-text">12,000000 visitors every Month</div>
					</li>
				</ul>
			</div>


		</div>
		<br> <br>

	</section>
</section>
<!--main content end-->
<jsp:include page="page-foot.jsp"></jsp:include>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>
<script>
	var userMaker = [];
	var traderMaker = [];
	var saleMaker = [];

	var userInfoWindow, traderInfoWindow, saleInfoWindow;

	function initialize() {
		var map = new google.maps.Map(document.getElementById('map'), {
			center : {
				lat : 11.709757,
				lng : 108.478252
			},
			zoom : 10,
			mapTypeId : 'roadmap'
		});

		var input = document.getElementById('pac-input');
		var searchBox = new google.maps.places.SearchBox(input);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

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

		var userMaker = [];
		var traderMaker = [];
		var saleMaker = [];

		userInfoWindow = new google.maps.InfoWindow({
			content : ''
		});

		traderInfoWindow = new google.maps.InfoWindow({
			content : ''
		});

		saleInfoWindow = new google.maps.InfoWindow({
			content : ''
		});

		loadSaleMaker(map); 
		loadTraderMaker(map);
		loadUserMaker(map); 
	}

	function loadUserMaker(map) {
		<c:forEach var="item" items="${users}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${item[2]},
						${item[3]}),
				map : map, // replaces  marker.setMap(map);
				icon : "/NongSanDD/resources/images/admin/user_iconmap.png"
			});
			userMaker.push(marker); // store the marker in the array
		
			google.maps.event.addListener(marker, 'mouseover', function() {
				// first we want to know which marker the client clicked on.
				var index = userMaker.indexOf(this);
				// we set the content of infoWindow, then we open it.
				userInfoWindow.setContent("${item[1]}");
				userInfoWindow.open(map, userMaker[index]);
			});
		
			google.maps.event.addListener(marker, 'click', function() {
				window.location.href = "/NongSanDD/admin/user-info?id="
						+ ${item[0]};
			});
		
			google.maps.event.addListener(marker, 'mouseout', function() {
				userInfoWindow.close();
			});
		</c:forEach>
	}

	function loadTraderMaker(map) {
		<c:forEach var="item" items="${traders}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${item[2]},
						${item[3]}),
				map : map, // replaces  marker.setMap(map);
				icon : "/NongSanDD/resources/images/admin/trader_iconmap.png"
			});
			traderMaker.push(marker); // store the marker in the array
		
			google.maps.event.addListener(marker, 'mouseover', function() {
				// first we want to know which marker the client clicked on.
				var index = traderMaker.indexOf(this);
				// we set the content of infoWindow, then we open it.
				traderInfoWindow.setContent("${item[1]}");
				traderInfoWindow.open(map, traderMaker[index]);
			});
		
			google.maps.event.addListener(marker, 'click', function() {
				window.location.href = "/NongSanDD/admin/trader-info?id="
						+ ${item[0]};
			});
		
			google.maps.event.addListener(marker, 'mouseout', function() {
				traderInfoWindow.close();
			});
		</c:forEach>
	}

	function loadSaleMaker(map) {
		<c:forEach var="item" items="${sales}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${item[2]},
						${item[3]}),
				map : map, // replaces  marker.setMap(map);
				icon : "/NongSanDD/resources/images/admin/sale_iconmap.png"
			});
			saleMaker.push(marker); // store the marker in the array
		
			google.maps.event.addListener(marker, 'mouseover', function() {
				// first we want to know which marker the client clicked on.
				var index = saleMaker.indexOf(this);
				// we set the content of infoWindow, then we open it.
				saleInfoWindow.setContent("${item[1]}");
				saleInfoWindow.open(map, saleMaker[index]);
			});
		
			google.maps.event.addListener(marker, 'click', function() {
				window.location.href = "/NongSanDD/admin/sale-info?id="
						+ ${item[0]};
			});
		
			google.maps.event.addListener(marker, 'mouseout', function() {
				saleInfoWindow.close();
			});
		</c:forEach>
	}

	$(document).ready(function() {
		$("#trader_icon_img").click(function() {
			if ($(this).hasClass("trader_icon_active")) {
				$(this).removeClass("trader_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/trader_icon.png");
				traderMaker.forEach(hideMaker);
			} else {
				$(this).addClass("trader_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/trader_icon_active.png");
				traderMaker.forEach(showMaker);
			}
		});

		$("#sale_icon_img").click(function() {
			if ($(this).hasClass("sale_icon_active")) {
				$(this).removeClass("sale_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/sale_icon.png");
				saleMaker.forEach(hideMaker);
			} else {
				$(this).addClass("sale_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/sale_icon_active.png");
				saleMaker.forEach(showMaker);
			}
		});

		$("#user_icon_img").click(function() {
			if ($(this).hasClass("user_icon_active")) {
				$(this).removeClass("user_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/user_icon.png");
				userMaker.forEach(hideMaker);
			} else {
				$(this).addClass("user_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/user_icon_active.png");
				userMaker.forEach(showMaker);
			}
		});

	});

	function hideMaker(value) {
		value.setVisible(false);
	}

	function showMaker(value) {
		value.setVisible(true);
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>