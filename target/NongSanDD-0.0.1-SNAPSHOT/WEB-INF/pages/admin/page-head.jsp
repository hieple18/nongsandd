<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Creative - Bootstrap 3 Responsive Admin Template">
<meta name="author" content="GeeksLabs">
<meta name="keyword"
	content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
<link rel="shortcut icon" href="img/favicon.png">

<title>Admin NongSanDD</title>

<!-- Bootstrap CSS -->
<link href="/NongSanDD/resources/css/admin/bootstrap.min.css"
	rel="stylesheet">
<!-- bootstrap theme -->
<link href="/NongSanDD/resources/css/admin/bootstrap-theme.css"
	rel="stylesheet">
<!--external css-->
<!-- font icon -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css"
	integrity="sha384-v2Tw72dyUXeU3y4aM2Y0tBJQkGfplr39mxZqlTBDUZAb9BGoC40+rdFCG0m10lXk"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/fontawesome.css"
	integrity="sha384-q3jl8XQu1OpdLgGFvNRnPdj5VIlCvgsDQTQB6owSOHWlAurxul7f+JpUOVdAiJ5P"
	crossorigin="anonymous">
<!-- Custom styles -->
<link href="/NongSanDD/resources/css/admin/style.css" rel="stylesheet">
<link href="/NongSanDD/resources/css/admin/style-responsive.css"
	rel="stylesheet" />
	
<!-- jquey confirm -->
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/vendor/jquery-confirm/jquery-confirm.min.css">

<!-- =======================================================
      Theme Name: NiceAdmin
      Theme URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
      Author: BootstrapMade
      Author URL: https://bootstrapmade.com
      ======================================================= -->
</head>

<body>
	<!-- container section start -->
	<section id="container" class="">
		<!--header start-->
		<header class="header dark-bg">
			<div class="toggle-nav">
				<div class="icon-reorder tooltips"
					data-original-title="Toggle Navigation" data-placement="bottom">
					<i class="icon_menu"></i>
				</div>
			</div>

			<!--logo start-->
			<a href="/NongSanDD/admin" class="logo"><span class="lite">Quản
					trị</span></a>
			<!--logo end-->

			<div class="top-nav notification-row">
				<!-- notificatoin dropdown start-->
				<ul class="nav pull-right top-menu">
					<!-- inbox notificatoin end -->
					<!-- user login dropdown start-->
					<li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <span class="profile-ava">
								<img alt="" src="img/avatar1_small.jpg">
						</span> <span class="username">Quản trị</span> <b class="caret"></b>
					</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<li><a href="/NongSanDD/dang-xuat"><i class="icon_key_alt"></i>
									Log Out</a></li>
						</ul></li>
					<!-- user login dropdown end -->
				</ul>
				<!-- notificatoin dropdown end-->
			</div>
		</header>
		<!--header end-->

		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse ">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu">
					<li class="active"><a class="" href="/NongSanDD/admin"> <i
							class="icon_house_alt"></i> <span>Trang Chủ</span>
					</a></li>
					<li><a class="" href="/NongSanDD/admin/agri"> <i class="icon_table"></i>
							<span>Nông Sản</span>
					</a></li>
					<li class="sub-menu"><a href="javascript:;" class=""> <i
							class="icon_document_alt"></i> <span>Giá</span> <span
							class="menu-arrow arrow_carrot-right"></span>
					</a>
						<ul class="sub">
							<li><a href="/NongSanDD/admin/price-add">Thêm</a></li>
							<li><a href="/NongSanDD/admin/price-update">Cập Nhập</a></li>
						</ul></li>

					<li class="sub-menu"><a href="javascript:;" class=""> <i
							class="icon_currency"></i> <span>Nhà Buôn</span> <span
							class="menu-arrow arrow_carrot-right"></span>
					</a>
						<ul class="sub">
							<li><a href="/NongSanDD/admin/add-trader-phone">Thêm SDT</a></li>
							<li><a href="/NongSanDD/admin/add-trader">Thêm mới</a></li>
							<li><a href="/NongSanDD/admin/wait-to-register">Chờ
									xác nhận</a></li>
						</ul></li>
					<li><a class="" href="/NongSanDD/admin//sale-expired"> <i class="icon_table"></i>
							<span>Cập nhập tin bán</span>
					</a></li>
				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>