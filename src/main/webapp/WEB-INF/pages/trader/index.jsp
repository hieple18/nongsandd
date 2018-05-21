<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../form_page/trader-head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/trader-index.css">
<div class="price_container container">
	<section class="wrapper">

		<div class="row">
			<a class="col-lg-3 col-md-3 col-sm-12 col-xs-12"  href="/NongSanDD/NhaBuon/ds-tin-ban">
				<div class="info-box blue-bg">
					<i class="fa fa-users"></i>
					<div class="count">${fn:length(sales)}</div>
					<div class="title">Tin Bán</div>
				</div>
				<!--/.info-box-->
			</a>
			
			<!--/.col-->

			<a class="col-lg-3 col-md-3 col-sm-12 col-xs-12"  href="/NongSanDD/NhaBuon/ds-yeu-cau">
				<div class="info-box brown-bg">
					<i class="fa fa-user"></i>
					<div class="count">${fn:length(requests)}</div>
					<div class="title">Dã Gửi Yêu Cầu</div>
				</div>
				<!--/.info-box-->
			</a>
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

			<a class="col-lg-3 col-md-3 col-sm-12 col-xs-12"  href="/NongSanDD/NhaBuon/tin-da-mua">
				<div class="info-box green-bg">
					<i class="fa fa-cubes"></i>
					<div class="count">${fn:length(selecteds)}</div>
					<div class="title">Đã Mua</div>
				</div>
				<!--/.info-box-->
			</a>
			<!--/.col-->
		</div>
		
		<div class="row">
			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" >
				<div id="agri-chart" style="min-width: 250px; height: 300px; max-width: 300px; margin: 0 auto"></div>
			</div>
			
			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" >
				<div id="commune-chart" style="min-width: 250px; height: 300px; max-width: 300px; margin: 0 auto"></div>
			</div>
			
			<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" >
				<div id="area-chart" style="min-width: 250px; height: 300px; max-width: 300px; margin: 0 auto"></div>
			</div>
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
				
			</div>


		</div>
		<br> <br>

	</section>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>
<script>
	var requestMaker = [];
	var selectedMaker = [];
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

		var requestMaker = [];
		var selectedMaker = [];
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
		loadselectedMaker(map);
		loadrequestMaker(map); 
	}

	function loadrequestMaker(map) {
		<c:forEach var="sale" items="${requests}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${sale.address.lat},
						${sale.address.lng}),
				map : map, // replaces  marker.setMap(map);
				icon : "/NongSanDD/resources/images/admin/user_iconmap.png"
			});
			requestMaker.push(marker); // store the marker in the array
		
			google.maps.event.addListener(marker, 'mouseover', function() {
				// first we want to know which marker the client clicked on.
				var index = requestMaker.indexOf(this);
				// we set the content of infoWindow, then we open it.
				userInfoWindow.setContent("${sale.agriculture.name}");
				userInfoWindow.open(map, requestMaker[index]);
			});
		
			google.maps.event.addListener(marker, 'click', function() {
				window.location.href = "/NongSanDD/NhaBuon/sale-info?id="
						+ ${sale.id} + "&state=2";
			});
		
			google.maps.event.addListener(marker, 'mouseout', function() {
				userInfoWindow.close();
			});
		</c:forEach>
	}

	function loadselectedMaker(map) {
		<c:forEach var="sale" items="${selecteds}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${sale.address.lat},
						${sale.address.lng}),
				map : map, // replaces  marker.setMap(map);
				icon : "/NongSanDD/resources/images/admin/trader_iconmap.png"
			});
			selectedMaker.push(marker); // store the marker in the array
		
			google.maps.event.addListener(marker, 'mouseover', function() {
				// first we want to know which marker the client clicked on.
				var index = selectedMaker.indexOf(this);
				// we set the content of infoWindow, then we open it.
				traderInfoWindow.setContent("${sale.agriculture.name}");
				traderInfoWindow.open(map, selectedMaker[index]);
			});
		
			google.maps.event.addListener(marker, 'click', function() {
				window.location.href = "/NongSanDD/NhaBuon/sale-info?id="
					+ ${sale.id} + "&state=3";
			});
		
			google.maps.event.addListener(marker, 'mouseout', function() {
				traderInfoWindow.close();
			});
		</c:forEach>
	}

	function loadSaleMaker(map) {
		<c:forEach var="sale" items="${sales}">
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(${sale.address.lat},
						${sale.address.lng}),
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
				window.location.href = "/NongSanDD/NhaBuon/sale-info?id="
					+ ${sale.id} + "&state=1";
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
				selectedMaker.forEach(hideMaker);
			} else {
				$(this).addClass("trader_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/trader_icon_active.png");
				selectedMaker.forEach(showMaker);
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
				requestMaker.forEach(hideMaker);
			} else {
				$(this).addClass("user_icon_active");

				$(this).attr("src", "/NongSanDD/resources/images/admin/user_icon_active.png");
				requestMaker.forEach(showMaker);
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


<script>
var agris = [];
<c:forEach var="agri" items="${agriCharts}">
	agris.push({name:"${agri[0]}", y:${agri[1]}});
</c:forEach>

var communes = [];
<c:forEach var="agri" items="${communeCharts}">
	communes.push({name:"${agri[0]}", y:${agri[1]}});
</c:forEach>

var areas = [];
<c:forEach var="area" items="${areas}">
	areas.push({name:"${area.name}", y:${area.y}});
</c:forEach>

Highcharts.chart('agri-chart', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: 'Thống kê theo tên Nông sản'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.y}</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false,
                format: '<b>{point.name}</b>: {point.y}',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: agris
    }]
});

Highcharts.chart('commune-chart', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: 'Thống kê theo xã'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.y}</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false,
                format: '<b>{point.name}</b>: {point.y}',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: communes
    }]
});

Highcharts.chart('area-chart', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: 'Thống kê theo diện tích'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.y}</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false,
                format: '<b>{point.name}</b>: {point.y}',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: areas
    }]
});
</script>
