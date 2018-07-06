<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<!-- For IE -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- For Resposive Device -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />


<title>Nông Sản DD</title>
<!-- Favicon -->
<link rel="apple-touch-icon" sizes="57x57"
	href="images/fav-icon/apple-icon-57x57.png">

<!-- Price Table -->
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="/NongSanDD/resources/css/normalize.min.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/price-table.css">

<!-- Custom Css -->
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/style.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/slider.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/responsive.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/data-table.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/rowreorder/1.2.3/js/dataTables.rowReorder.min.js">	
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/responsive/2.2.1/css/responsive.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/notify.css">

<!-- dropdown -->
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/dropdown-style.css">

<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/login-model.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/drop-zone.css">
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/css/validation.css">

<!-- jquey confirm -->
<link rel="stylesheet" type="text/css"
	href="/NongSanDD/resources/vendor/jquery-confirm/jquery-confirm.min.css">

<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/datetimepicker/bootstrap-datetimepicker.min.css" />

<!-- choose -->
<link rel="stylesheet"
	href="/NongSanDD/resources/vendor/choose/chosen.css" />

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css"
	integrity="sha384-v2Tw72dyUXeU3y4aM2Y0tBJQkGfplr39mxZqlTBDUZAb9BGoC40+rdFCG0m10lXk"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/fontawesome.css"
	integrity="sha384-q3jl8XQu1OpdLgGFvNRnPdj5VIlCvgsDQTQB6owSOHWlAurxul7f+JpUOVdAiJ5P"
	crossorigin="anonymous">

<!-- j Query -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery-2.1.4.js"></script>
<!-- jquery confirm -->
<script type='text/javascript'
	src='/NongSanDD/resources/vendor/jquery-confirm/jquery-confirm.min.js'></script>

<style>
form {
	margin-bottom: 0px !important;
}
</style>
<style type="text/css">
input[type="file"] {
	display: block;
}

.imageThumb {
	max-height: 75px;
	border: 2px solid;
	padding: 1px;
	cursor: pointer;
}

.old_imageThumb {
	max-height: 75px;
	border: 2px solid red;
	padding: 1px;
	cursor: pointer;
}

.pip {
	display: inline-block;
	margin: 10px 10px 0 0;
}

.remove_style {
	display: block;
	color: black;
	text-align: center;
	cursor: pointer;
}

.remove_style:hover {
	color: red;
}
</style>
