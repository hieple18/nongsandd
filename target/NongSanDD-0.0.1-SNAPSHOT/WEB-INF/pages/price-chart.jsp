<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="form_page/page-head.jsp"></jsp:include>
<div class=container style="padding-top: 30px">
	<div id="line-example">
		<label style="text-align: center">So Sánh Với: </label> 
		<select data-style="btn-primary" id="compare_select" style="width: 150px; margin-bottom: 14px;" 
			data-placeholder="Chọn Nông Sản">
			<c:forEach var="category" items="${agriCategories}">
				<optgroup label="${category.name }">
					<c:forEach var="agri" items="${agris}">
						<c:if test="${agri.agriCategory.id == category.id }">
							<option value="${agri.id}">${agri.name}</option> </c:if>
					</c:forEach>
				</optgroup>
			</c:forEach>
		</select>

		<div id="chartContainer" style="height: 400px; width: 100%;"></div>

		<!-- <p>The above chart is generated using the data in the view model
			and rendered into the canvas</p> -->
	</div>
</div>
<jsp:include page="form_page/page-foot.jsp"></jsp:include>
<script>
var chart;
$("#compare_select").chosen();
$(function () {
	chart = new Highcharts.stockChart('chartContainer', {
		tooltip: {
		    dateTimeLabelFormats:{
		    	day: '%d/%m/%y'
	    	}
		},
		xAxis: {
		    type: 'datetime',
		    labels: {
		        format: '{value:%d/%m/%y}',
		        align: 'left'
		    }
		},
		legend: {
	        enabled: true,
		},
		series: [{
			data: ${agriPrices},
			name: "${currAgriName}",
		    pointStart: Date.UTC(2010, 0, 1),
		    pointInterval: 24 * 3600 * 1000 // one day
		}]
	});
});

$(document).ready(function(){
	$("#compare_select").on("change", function(){
		var $option = $(this).find('option:selected');
		var idSelected = $option.val();
		
		if(idSelected !== -1){
			$.ajax({
				data : {
					"id" : idSelected
				},
				url : "/NongSanDD/lay-ds-gia",
				type : "POST",

				success : function(data) {
					chart.addSeries({
				        data: data,
				        name: $option.text(),
				        pointStart: Date.UTC(2010, 0, 1),
					    pointInterval: 24 * 3600 * 1000 // one day
				    });
				}
			});
			
			$('option:selected', this).remove();
			$("#compare_select").trigger('chosen:updated');
		}
	})
})
</script>

