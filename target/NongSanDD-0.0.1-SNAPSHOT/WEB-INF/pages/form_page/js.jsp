<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Js File_________________________________ -->

<!-- Bootstrap JS -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/bootstrap.min.js"></script>

<!-- owl.carousel -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/owl.carousel.min.js"></script>
<!-- ui js -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery-ui.min.js"></script>
<!-- Responsive menu-->
<script type="text/javascript" src="/NongSanDD/resources/js/menuzord.js"></script>
<!-- Fancybox js -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery.fancybox.pack.js"></script>
<!-- js count to -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery.appear.js"></script>
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery.countTo.js"></script>
<!-- WOW js -->
<script type="text/javascript" src="/NongSanDD/resources/js/wow.min.js"></script>

<script type="text/javascript"
	src="/NongSanDD/resources/js/SmoothScroll.js"></script>

<script src="/NongSanDD/resources/js/bootstrap-select.min.js"></script>
<script src="/NongSanDD/resources/js/jquery.mixitup.min.js"></script>
<!-- Theme js -->
<script type="text/javascript" src="/NongSanDD/resources/js/theme.js"></script>
<script type="text/javascript" src="/NongSanDD/resources/js/slider.js"></script>

<!-- price Table -->
<script type="text/javascript"
	src="/NongSanDD/resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/rowreorder/1.2.3/js/dataTables.rowReorder.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/responsive/2.2.1/js/dataTables.responsive.min.js"></script>


<script type="text/javascript"
	src="/NongSanDD/resources/vendor/validator/jquery.validate.js"></script>

<script src="/NongSanDD/resources/js/moment.min.js"></script>
<script
	src="/NongSanDD/resources/vendor/datetimepicker/bootstrap-datetimepicker.min.js"></script>

<!-- chart -->
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>

<!-- choose  -->
<script type='text/javascript' src='/NongSanDD/resources/vendor/choose/chosen.jquery.js'></script>

<Script>

	$(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
</script>

<script>
	function showPassword() {
		var key_attr = $('#key').attr('type');
		if (key_attr != 'text') {
			$('.checkbox').addClass('show');
			$('#key').attr('type', 'text');
		} else {
			$('.checkbox').removeClass('show');
			$('#key').attr('type', 'password');
		}
	}
</script>

