<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../form_page/user-head.jsp"></jsp:include>
<div class=container style="padding-top: 30px">
	<div id="line-example">
		<select data-style="btn-success" style="width: 120px">
			<option>Tháng 1</option>
			<option>Tháng 2</option>
			<option>Tháng 3</option>
		</select> <select data-style="btn-primary"
			style="width: 120px; margin-left: 20px">
			<option>Năm 2017</option>
		</select> <label style="margin-left: 20px">So Sánh Với: </label> 
		<select data-style="btn-primary" style="width: 120px" id="select-agri">
			<option value="-1"></option>
			<c:forEach var="item" items="${agricultures}">
				<option value="${item.id}">${item.name}</option>
			</c:forEach>
		</select>

		<div id="chartContainer" style="height: 400px; width: 100%;"></div>

		<p>The above chart is generated using the data in the view model
			and rendered into the canvas</p>
	</div>
</div>
<jsp:include page="../form_page/page-foot.jsp"></jsp:include>
<script>
var chart;

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
	$("#select-agri").on("change", function(){
		var $option = $(this).find('option:selected');
		var idSelected = $option.val();
		
		if(idSelected !== -1){
			$.ajax({
				data : {
					"id" : idSelected
				},
				url : "lay-ds-gia",
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
		}
	})
})
</script>

