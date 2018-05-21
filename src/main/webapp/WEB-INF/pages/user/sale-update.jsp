<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../form_page/user-head.jsp"></jsp:include>
<style type="text/css">
.stageParent {
	background: #fff;
	border: 3px dashed #444;
	border-radius: 5px;
	padding: 10px;
}

.stage {
	min-height: 220px;
	max-height: 400px;
	overflow-y: auto;
}

.fileinput-button {
  position: relative;
  overflow: hidden;
  float: left;
  margin-right: 4px;
}
.fileinput-button input {
  position: absolute;
  top: 0;
  right: 0;
  margin: 0;
  opacity: 0;
  filter: alpha(opacity=0);
  transform: translate(-300px, 0) scale(4);
  font-size: 23px;
  direction: ltr;
  cursor: pointer;
}
</style>

<div class="container">
	<form:form class="form-horizontal form-label-left" id="sale_info_form"
		modelAttribute="sale" action="/NongSanDD/NguoiDung/cap-nhap-tin-ban" style="padding-top: 50px">

		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Tên
				Nông Sản <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<select data-placeholder="Chọn Nông Sản ..." id="agri-select" tabindex="2"
					class="form-control col-md-7 col-xs-12" name="agriculture.id">
					<c:forEach var="agri" items="${agris}">
						<option <c:if test="${agri.id == sale.agriculture.id}">selected="selected"</c:if>
						value="${agri.id}" >${agri.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12">Diện
				Tích <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<input type="text" value="${sale.area}" class="form-control col-md-5 col-xs-12" name="area"/>
				<span class="input-group-addon">Sào</span>
			</div>

			<label class="control-label col-md-2 col-sm-3 col-xs-12" for="phone">Số
				Lượng <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<input type="text" value="${sale.quantity}" class="form-control col-md-5 col-xs-12" name="quantity"/>
				<span class="input-group-addon">Ngìn Cây</span>
			</div>
		</div>
		
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="address">Tỉnh <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<input type="text" id="website" required="required"
					placeholder="Tỉnh Lâm Đồng" class="form-control col-md-7 col-xs-12"
					disabled>
			</div>
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="address">Huyện <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<select class="form-control col-xs-12">
					<option>Huyện Đơn Dương</option>
					<option>Huyện Khác</option>
				</select>
			</div>
		</div>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12"
				for="address">Phường/Xã <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12">
				<select id="select-commune"
					data-live-search="true" class="form-control col-xs-12">
					<c:forEach var="commune" items="${communes}">
						<option <c:if test="${commune.communeID == communeID}">selected="selected"</c:if>
							 value="${commune.communeID}">${commune.name}</option>
					</c:forEach>
				</select>
			</div>
			<label class="control-label col-md-2 col-sm-3 col-xs-12"
				for="address">Thôn/Xóm <span class="required">*</span>
			</label>
			<div class="col-md-2 col-sm-6 col-xs-12" id="hamle_op">
				<select id="select-village" name="hamletID"
					data-live-search="true" class="form-control col-xs-12">
					<c:forEach var="hamlet" items="${hamlets}">
						<option <c:if test="${hamlet.hamletID == address.hamlet.hamletID}">selected="selected"</c:if>
							 value="${hamlet.hamletID}">${hamlet.name}</option>
					</c:forEach>
				</select> 
			</div>
		</div>
		
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12">Chọn
				trên bản đồ </label>
			<div class="panel-body-map col-md-6 col-sm-6 col-xs-12">
				<input id="pac-input" class="controls" type="text"
					placeholder="Search Box">
				<div id="upload-sale-map" style="height: 300px; width: 100%"></div>
			</div>
		</div>
		<input type="hidden" name="address.lat" id="map_lat" value = "${address.lat}"/>
		<input type="hidden" name="address.lng" id="map_lng" value = "${address.lng}"/> 
		<input type="hidden" name="address.id" value = "${address.id}"/>
		<input type="hidden" name="id" value = "${sale.id}"/> 
		<input type="hidden" name="status" value = "${sale.status}"/>
		<input type="date" hidden="hidden" name="dateCreate" value = "${sale.dateCreate}"/>
		
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12">Giá
				Khởi Điểm </label>
			<div class="col-md-6 col-sm-6 col-xs-12"
				style="position: relative; display: table; border-collapse: separate;">
				<input type="text" value="${sale.price}" class="form-control col-md-5 col-xs-12" name="price"/>
				<span class="input-group-addon">Triệu </span>
			</div>
		</div>
		<div class="item form-group">
			<label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">Mô
				Tả </label>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<textarea id="telephone" name="sellingDescribe"
					class="form-control col-md-7 col-xs-12"></textarea>
			</div>
		</div>
		<div class="item form-group">
			<label
				class="control-label col-md-3 col-sm-3 col-xs-12">Hình Ảnh <span class="required">*</span>
			</label>
			<div class="col-md-6 col-sm-6 col-xs-12">
	 			<span class="btn btn-primary fileinput-button">
                    <i class="fa fa-upload"></i>
                    <span>Thêm Ảnh...</span>
                    <input type="file" id="files" multiple="multiple" accept="image/*">
                </span>
	 			
				<div class="col-xs-12">
					<input type="hidden" id = "imgUploaded">
					<c:forEach var="link" items="${links}">
                    	<span id="${link.name}" class="pip"><img class="old_imageThumb" src="${link.link}" /><br/>
						<span class = "remove_style" onclick="deleteExistsImg(${link.name})">Xóa</span>
						</span>
                    </c:forEach>
				</div>
			</div>
		</div>
		<div style="height: 20px" class="ln_solid"></div>
		<div class="form-group" style="text-align: center">
			<div class="col-md-9 col-md-offset-3">
	
				<a class="btn btn-success" onclick="submitUploadSaleForm()">Đăng Kí</a>
				<button class="btn btn-primary" style="margin-left: 50px">Hủy Bỏ</button>
			</div>
		</div>
	</form:form>
	
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<!-- firebase -->
<script src="https://www.gstatic.com/firebasejs/4.10.1/firebase.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQvN2xdEEQKXzw1vlLYZTtXhqyjZv_IHw&libraries=places"></script>

<script> /* init, config */
	// Initialize Firebase
	var config = {
		apiKey : "AIzaSyAGmqXHkfVvlwjRz_6ba-9_Zlg5SsVC328",
		authDomain : "my-project-1511089672003.firebaseapp.com",
		databaseURL : "https://my-project-1511089672003.firebaseio.com",
		projectId : "my-project-1511089672003",
		storageBucket : "my-project-1511089672003.appspot.com",
		messagingSenderId : "232158743052"
	};
	firebase.initializeApp(config);
	
	// init chosen
	$("#agri-select").chosen();
	
	// resize chosen
	$(document).ready(function(){      
		   resizeChosen();
		   jQuery(window).on('resize', resizeChosen);
	});
	function resizeChosen() {
	   $(".chosen-container").each(function() {
	       $(this).attr('style', 'width: 100%');
	   });    
	   $(".chosen-single").attr('style','height:30px');
	}
</script>

<script> /* validation */
$(document).ready(function() {
	$('#sale_info_form').validate({
		rules : {
			area : {
				required : true
			},
			quantity : {
				required : true
			},
			price : {
				required : true
			}
		},
		messages : {
			area : {
				required : "Vui lòng nhập diện tích"
			},
			quantity : {
				required : "Vui lòng nhập số lượng"
			},
			price : {
				required : "Vui lòng nhập giá"
			}
		}
	});
});
</script>

<script> /* handle upload image */

	//save image uploaded
	var imgArray = {};
	
	//sum image uploaded
	var countImage_h = 0;
	var previousCount = ${linksSize};
	var ValidImageTypes = ["image/gif", "image/jpeg", "image/png"];
	$(document).ready(function() {
		if (window.File && window.FileList && window.FileReader) {
			$("#files").on("change",function(e) {
				var files = e.target.files, filesLength = files.length;
				for (var i = 0; i < filesLength; i++) {
					//upload maximun is 10 images
					if(countImage_h + previousCount < 10){
						var f = files[i];
						if (!imgArray.hasOwnProperty(f.name) && $.inArray(f["type"], ValidImageTypes) > 0) {
							imgArray[f.name] = files[i];
							countImage_h++;
							
							var fileReader = new FileReader();
							fileReader.onload = (function(e) {
								var imgName = e.target.fileName;
								$("<span class=\"pip\">"
												+ "<img class=\"imageThumb\" src=\"" + e.target.result + "\"/>"
												+ "<br/><span id=\"" + imgName + "\"" + "class=\"remove_style remove\">Xóa</span>"
												+ "</span>").insertAfter("#imgUploaded");
								$(".remove").click(function() {
									var name = $(this).attr("id");
									if(imgArray.hasOwnProperty(name)){
										$(this).parent(".pip").remove();
										delete imgArray[name];
										countImage_h--;
										console.log(name + ", " + countImage_h);
									}
								});
	
							});
							fileReader.fileName = f.name;
							fileReader.readAsDataURL(f);
						}
					}else{
						$.alert({
                            title: 'Lỗi!',
                            icon: 'fa fa-warning',
                            type: 'orange',
                            content: 'Một tin bán chỉ được đăng tối đa 10 hình ảnh',
                        });
					}
				}
			});
		} else {
			alert("Your browser doesn't support to File API")
		}
	});
</script>

<script> /* define function relate to upload image to firebase */
	
	// delete image, which saved in firebase
	function deleteExistsImg(name){
		var storage = firebase.storage();
		var storageRef = storage.ref('images/' + name);
		
		// Delete the file
		storageRef.delete().then(function() {
			delLink(name);
			$("#" + name).remove();
			previousCount--;
		}).catch(function(error) {
		  	alert("lỗi xóa ảnh");
		});  
	}
	
	// create image's name to store in database 
	function createImgName(count){
		var dCreate = new Date();
		return ${userID} + count + dCreate.getTime();
	}
	
	
	// get image url after upload image to firebase success
	function getUploadUrl(){
		var deferred = $.Deferred();
		imgNames.forEach(function(element){
			var storage = firebase.storage();
			var storageRef = storage.ref('images/' + element);
			storageRef.getDownloadURL().then(function(url) {
				addLink(element + "," + url);
			});
		});
		
		setTimeout(function(){ deferred.resolve(); }, 1000);
		return deferred.promise();
	}
	
	function addLink(value){
		var input = $("<input>").attr({"type":"hidden","name":"addlinks[]"}).val(value);
        $('#sale_info_form').append(input); 
	}
	
	function delLink(value){
		var input = $("<input>").attr({"type":"hidden","name":"dellinks[]"}).val(value);
        $('#sale_info_form').append(input); 
	}
	
	//save name of image to get link in function getUploadUrl()
	var imgNames = [];
	
	function submitImg(){
		var deferred = $.Deferred();
		// to detect when finish upload image
		var currentCount_h = 0;
		// to create name
		var countNameImg = 1;
		
		// to caculate progress
		var imgArrlength = imgArray.length;
		var currentCountUpload = 0;
		var sumProgress = 0;
		
		// Get a reference to the storage service, which is used to create references in your storage bucket
		var storage = firebase.storage(); 

		for ( var i in imgArray) {
			var item = imgArray[i];
			var imgName = createImgName(countNameImg++);
			imgNames.push(imgName);
			
			// Create a storage reference from our storage service
			var storageRef = storage.ref('images/' + imgName);

			// Upload file and metadata to the object 'images/mountains.jpg'
			var uploadTask = storageRef.put(item);

			uploadTask.on(
				firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
				function(snapshot) {
					// Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
					var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
					var avgProgress = 0
					
					if(currentCountUpload < imgArrlength){
						currentCountUpload++;
						sumProgress += progress
					}else{
						avgProgress = sumProgress/imgArrlength;
						currentCountUpload = 0;
						sumProgress = 0;
					}
					
					if(progress === 100){
						if(currentCount_h === countImage_h - 1){
							setTimeout(function(){ deferred.resolve(); }, 1000);
						}
						currentCount_h++;
					}
					
					switch (snapshot.state) {
					case firebase.storage.TaskState.PAUSED: // or 'paused'
						console.log('Upload is paused');
						break;
					case firebase.storage.TaskState.RUNNING: // or 'running'
						console.log('Upload is running');
						break;
					}
				},
				function(error) {
					console.log(error);
				}
			); 				
		};
		
		return deferred.promise(); 
	}
	
</script>

<script> /* submit form */
	function submitUploadSaleForm(){
		if($('#sale_info_form').valid()){
			//upload more images
			if(countImage_h > 0){
				$.confirm({
				    icon: 'fa fa-spinner fa-spin',
				    type: 'green',
				    title: 'Vui Lòng Chờ!',
				    content: 'Việc tải hình ảnh lên có thể mất một lúc. Vui lòng chờ!'
				});
				
				$.when(submitImg()).done(function(){
					$.when(getUploadUrl()).done(function(){
						$("#sale_info_form").submit();
					});
				});
			}else{
				$("#sale_info_form").submit();
			}
		}
	}
</script>

<script>
var updateFlag = true;
var previousAddress = "${address.hamlet.address}";
var mapZoom = 15;
var currentLocation = {
		lat : ${address.lat},
		lng : ${address.lng}
	};
</script>

<script type="text/javascript"
	src="/NongSanDD/resources/js/init-google_map.js"></script>
