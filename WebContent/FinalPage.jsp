 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Bean.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
User user= (User)session.getAttribute("user");
String name= user.getName();
String surname = user.getSurname();
String username = user.getUsername();
int idUser= user.getIduser();

Ranking ranking = (Ranking)session.getAttribute("ranking");
int rank1 = ranking.getRank1();
int rank2 = ranking.getRank2();
int rank3 = ranking.getRank3();
int rank4 = ranking.getRank4();
int totalRank = ranking.getTotalrank();
     
%>
<!DOCTYPE html>
<html>
<head>
        <link rel="stylesheet" href="css/Style.css">
	 	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
<title>Final Page</title>
       <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>


<script>
function playVideo(){
	document.getElementById("ranking").style.display= 'none';
	video = document.getElementById("myVideo");
	video.play();
	video.onended = function(e){
		document.getElementById("myVideo").style.display= 'none';
		document.getElementById("ranking").style.display= 'block';
	}
}

function returnHome(){
	 window.location = '/EscapeGameProject/HomePage';
}



</script>
</head>
<body class="finalPage" onload="playVideo()">


<video id="myVideo" width="100%" height="100%">
  <source src="video/door.mp4" type="video/mp4">
</video>

<div id="ranking" align="center">

<h2>Complimenti <%=name%> sei riuscito ad uscire dalla stanza!</h2><br>
I tuoi punteggi sono stati i seguenti:<br>
Minigioco 1: <%=rank1 %>/30<br>
Minigioco 2:<%=rank2 %>/30<br>
Minigioco 3:<%=rank3 %>/30<br>
Finalgame :<%=rank4 %>/30<br>
Il tuo score totale è: <%=totalRank %>/100!<br><br><br><br>


<input type="button" id="returnHome" value="Torna alla Home Page" class="btnA backR" onclick="returnHome()">
</div>




</body>
</html>