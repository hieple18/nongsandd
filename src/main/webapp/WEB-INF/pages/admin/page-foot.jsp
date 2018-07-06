</section>
</body>
<!-- container section end -->
<!-- javascripts -->
<script src="/NongSanDD/resources/js/admin/jquery.js"></script>
<script src="/NongSanDD/resources/js/admin/bootstrap.min.js"></script>
<!-- nicescroll -->
<script src="/NongSanDD/resources/js/admin/jquery.scrollTo.min.js"></script>
<script src="/NongSanDD/resources/js/admin/jquery.nicescroll.js" type="text/javascript"></script>
<!--custome script for all page-->
<script src="/NongSanDD/resources/js/admin/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<Script>

	$(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
</script>
</html>