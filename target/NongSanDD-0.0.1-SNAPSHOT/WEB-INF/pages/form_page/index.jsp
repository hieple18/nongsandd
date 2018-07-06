<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
   <jsp:include page="head.jsp"></jsp:include>
  </head>  
  <body>
  	<div class="main_page">
  		<jsp:include page="header.jsp"></jsp:include>
   		<jsp:include page="${pageName}"></jsp:include> 
  		<jsp:include page="slide-img-footer.jsp"></jsp:include>
  		<jsp:include page="footer.jsp"></jsp:include>
  		<jsp:include page="js.jsp"></jsp:include>
  	</div> 
  	
  </body>
</html>