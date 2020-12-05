<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Bean.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Final Page</title>


<script>
function playVideo(){
	video = document.getElementById("myVideo");
	video.play();
}


</script>
</head>
<body onload="playVideo()">


<video id="myVideo" width="100%" height="100%">
  <source src="video/door.mp4" type="video/mp4">
</video>



</body>
</html>