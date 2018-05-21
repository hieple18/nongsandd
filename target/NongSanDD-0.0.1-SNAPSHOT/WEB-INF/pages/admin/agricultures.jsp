<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="page-head.jsp"></jsp:include>
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<div class="row">
			<div style="width: 40%; margin: 50px auto 0">
				<section class="panel">
					<header class="panel-heading no-border"> Nông Sản </header>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th style="width: 10%">#</th>
								<th style="width: 25%">Mục</th>
								<th style="width: 40%">Tên</th>
								<th style="width: 25%"></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="stt" scope="session" value="1" />
							<c:forEach var="category" items="${agriCategories}">
								<tr>
									<td></td>
									<td>${category.name }</td>
									<td></td>
									<td><div class="btn-group">
											<a class="btn btn-primary" onclick="addAgri(${category.id})" href="#"><i
												class="icon_plus_alt2"></i></a> 
											<a class="btn btn-success" onclick="updateCategory(${category.id}, "${category.name }")" 
												href="#"><i class="icon_check_alt2"></i></a>
											<a class="btn btn-danger" onclick="deleteCategory(${category.id})"
												href="#"><i class="icon_close_alt2"></i></a>
												
										</div></td>
								</tr>

								<c:forEach var="agri" items="${agris}">
									<c:if test="${agri.agriCategory.id == category.id }">
										<tr>
											<td>${stt}</td>
											<td></td>
											<td>${agri.name }</td>
											<td><div class="btn-group">
													<a class="btn btn-danger" onclick="deleteAgri(${agri.id})" href="#"><i
														class="icon_close_alt2"></i></a>
													<a class="btn btn-success" onclick="updateAgri(${agri.id}, "${agri.name }")" 
														href="#"><i class="icon_check_alt2"></i></a>
												</div></td>
										</tr>
										<c:set var="stt" scope="session" value="${stt + 1}" />
									</c:if>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</section>
			</div>
		</div>
		<!-- page end-->
	</section>
</section>
<!-- Modal -->
<div class="modal fade" id="addAgri" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Thêm Nông Sản</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal form-label-left" id="addAgriForm"
					action="/NongSanDD/admin/add-agri" method="get">

					<div class="form-group">
						<label class="control-label col-lg-2" for="inputSuccess">Mục</label>
						<div class="col-lg-10">
							<select class="form-control m-bot15 category" name="categoryID">
								<c:forEach var="category" items="${agriCategories}">
									<option value="${category.id}">${category.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Tên</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name=name 
								required="required">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default" onclick="submitAddAgriFrom()">Thêm</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="updateAgri" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Sửa thông tin</h4>
			</div>
			<input type="hidden" id="agriID" name="id">
			<div class="modal-body">
				<form class="form-horizontal form-label-left" id="updateAgriForm"
					action="/NongSanDD/admin/update-agri" method="get">

					<div class="form-group">
						<label class="control-label col-lg-2" for="inputSuccess">Mục</label>
						<div class="col-lg-10">
							<select class="form-control m-bot15 category" name="categoryID">
								<c:forEach var="category" items="${agriCategories}">
									<option value="${category.id}">${category.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">Tên</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name=name id="agriName"
								required="required">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default" onclick="submitUpdateAgriFrom()">Cập Nhập</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="updateCategory" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Thêm Nông Sản</h4>
			</div>
			<input type="hidden" id="categoryID" name="id">
			<div class="modal-body">
				<form class="form-horizontal form-label-left" id="updateCategoryForm"
					action="/NongSanDD/admin/update-category" method="get">
					<div class="form-group">
						<label class="col-sm-2 control-label">Tên</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name=name id="categoryName"
								required="required">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default" onclick="submitUpdateCategoryFrom()">Cập Nhập</a>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>

	</div>
</div>
<jsp:include page="page-foot.jsp"></jsp:include>
<script>
	function deleteAgri(id) {
		$.confirm({
	        content: 'Có chắc muốn xóa nông sản này không?',
	        icon: 'fa fa-question-circle',
	        type: 'warning',
	        animation: 'scale',
	        closeAnimation: 'scale',
	        opacity: 0.5,
	        buttons: {
	            'confirm': function (){
	            	window.location.href = "/NongSanDD/admin/delete-agri?id=" + id;
	            	
	            },
	            cancel: function () {
	            	text: "Đóng"
	            }
	        }
	    });
	}

	function deleteCategory(id) {
	}
	
	function updateCategory(id, name){
		$("#categoryID").val(id);
		$("#categoryName").val(name);
		$("#updateCategory").modal("show");
	}
	
	function updateAgri(cid, id, name){
		$('.category option[value='+cid+']').attr('selected','selected');
		$("#agriID").val(id);
		$("#agriName").val(name);
		$("#updateAgri").modal("show");
	}
	
	function addAgri(id){
		$('.category option[value='+id+']').attr('selected','selected');
		$("#addAgri").modal("show");
	}
	
	function submitUpdateAgriFrom(){
		$("#updateAgriForm").submit();
	}
	
	function submitAddAgriFrom(){
		$("#addAgriForm").submit();
	}
	
	function submitUpdateCategoryFrom(){
		$("#updateCategoryForm").submit();
	}
</script>