<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Bean.Room"%>
<%@page import="Bean.User"%>
<%@page import="Bean.Ranking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
     <link rel="stylesheet" href="css/Style.css">
	 <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
     <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>Home Page</title>


<%

User user = (User) session.getAttribute("user");

%>


<script type="text/javascript">
	
	
	
	/*function load(){
		document.getElementById('block2').style.display= "none";
		document.getElementById('block3').style.display= "none";
	}
	
	function show(number){
		if (number==2) {
		document.getElementById('block2').style.display = "block";
		document.getElementById('block3').style.display= "none";
		}
		
		if(number==3){ 
			document.getElementById('block2').style.display= "none";
			document.getElementById('block3').style.display = "block";
		}
	}*/
	
	
	function logout() {
		window.location.href = "./Login.html";
	}
	function goCreation(){
		
		 window.location = '/EscapeGameProject/RoomCreation.jsp';
	}
	function goCreationMg(){
		
		window.location = '/EscapeGameProject/MiniGameCreation.jsp';
	}
	function  goAddSubject(){
		window.location = '/EscapeGameProject/SubjectInsertion.jsp';
		
	}
</script>
</head>
<body onload="load()">
<div class="introDue" >
<div class="HomeHead">
<button class= "btnLogout" id= "help" type="button" name="esci" value="Help" data-bs-toggle="modal" data-bs-target="#exampleModal3" data-bs-whatever="@mdo"  >Help</button>
<button class= "btnLogout" id= "logout" type="button" name="esci" value="Logout" onClick="logout()" >Logout</button>

</div>



<div class="boxStHome boxPHome">
<h1 id="welcome">
		Benvenuto Prof. <%=user.getSurname() %> <%= user.getName()%>
</h1>
<button class="btnHome mb-3 " data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">Stanze create</button>
<button class="btnHome mb-3" id="create" onclick="goCreation()">Crea stanze</button>
<button class="btnHome mb-3" id="createMg" onclick="goCreationMg()">Crea minigiochi</button>
<button class="btnHome mb-3" id="addSubject" onclick="goAddSubject()">Aggiungi nuova materia</button>
<button class="btn2 btnScore" data-bs-toggle="modal" data-bs-target="#exampleModal2" data-bs-whatever="@fat" ><img src="images/trophy.png"></button>

</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="thumbnailCon modal-dialog modal-dialog-table d-flex flex-row align-items-center ">
	
	
	<table>
	<tr><td>Miniatura</td><td>Data</td><td>Anno</td><td>Materia</td><td>ID stanza</td><td></td></tr>
	
	<%
	
	
	
	
List<Room> createtedRooms = new ArrayList<>();
createtedRooms = (List<Room>) request.getAttribute("createtedRooms");
			
		for (Room room : createtedRooms) {
		%>
<tr><td><img id="imageT" height="62" width="62" src="images/<%= room.getThumbnail()  %>.jpg" alt="<%=room.getThumbnail()%>"></td><td><%=room.getDate()%></td><td><%=room.getYear()%></td><td><%=room.getSubject()%></td><td><%=room.getIdRoom()%></td><td><button id="UpdateRoom" value=<%=room.getIdRoom() %>>modifica stanza</button></td></tr>
		
			

			<%
			}
		%>
		</table>
		</div>
</div>

	<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" >
    <div class="modal-dialog thumbnailCon modal-dialog-table2 d-flex flex-column align-items-center p-5">
    <table>
	<tr><td>Stanza</td><td>Data</td><td>Studente</td><td>ID stanza</td><td>Minigame 1</td><td>Minigame 2</td><td>Minigame 3</td><td>Minigame 4</td><td>Totale</td></tr>
  <%
	

  List<Ranking> Rankings = new ArrayList<>();
  Rankings = (List<Ranking>) request.getAttribute("rankings");
			
		for (Ranking ranking : Rankings) {
		%>
<tr><td><img id="imageT" height="62" width="62" src="images/stanza.jpg"></td><td><%=ranking.getDate()%></td><td><%=ranking.getUser()%></td><td><%=ranking.getIdroom() %></td><td><%=ranking.getRank1()%></td><td><%=ranking.getRank2()%></td><td><%=ranking.getRank3()%></td><td><%=ranking.getRank4()%></td><td><%=ranking.getTotalrank()%></td></tr>
		
			<%
			}
		%>
		</table>
  </div>
  </div>
  <div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" >
    <div class="modal-dialog thumbnailCon modal-dialog-table2 d-flex flex-column align-items-center p-5">
    <p>Spiegazioni</p>
  </div>
  </div>
<div class="foot">
<p>Copyright2020</p>
</div>
</div>
</body>
</html>