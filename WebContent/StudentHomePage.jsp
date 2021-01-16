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
<title>HomePage</title>
      <script type="text/javascript" src="script/StudentHomePageScript.js" ></script>
	  <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	  

<%
	//LISTA ROOM 

User user = (User) session.getAttribute("user");

List<Room> Rooms = new ArrayList<>();
Rooms = (List<Room>) session.getAttribute("Rooms");

List<Ranking> Rankings = new ArrayList<>();
Rankings = (List<Ranking>) session.getAttribute("Rankings");
%>



<!--  <script type="text/javascript">
	var id_room = localStorage.getItem("id_room");
	var room_password = localStorage.getItem("room_password");

	function load() {
		document.getElementById('block_2').style.display = "none";
		document.getElementById('block_3').style.display = "none";
		document.getElementById('block_4').style.display = "none";
		document.getElementById('block_5').style.display = "none";
	}

	function show(number) {
		if (number == 2) {
			document.getElementById('block_2').style.display = "block";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "none";
		}
		if (number == 3) {
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "block";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "none";
		}
		if (number == 4) {
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "none";
			document.getElementById('block_5').style.display = "none";
			document.getElementById('block_4').style.display = "block";
		}
		
		if (number == 5){
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "none";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "block";
		}

		
	}

	function movePassword(image) {
		var number_image = image.id.split('_')[1];
		id_room = document.getElementById('id_room_' + number_image).value;
		room_password = document.getElementById('room_password_'
				+ number_image).value;
		localStorage.setItem("id_room", id_room);
		localStorage.setItem("room_password", room_password);

	}

	function logout() {

		localStorage.removeItem("id_room");
		localStorage.removeItem("room_password");
		window.location.href = "./Login.html";
	}
	function passwordCheck() {
		var password = document.getElementById('password').value;
		if (password != '') {
			
			if (password != room_password ) {
				alert('password non valida!');
				return false;
			}else{
				
			document.getElementById('id_room').value = localStorage.getItem("id_room");
			document.getElementById("block_3").submit();
		}
		}else{
			alert('password non valida!');
		}
		
	}
</script>-->
</head>

<body >

<div class="introDue" >
<div class="HomeHead">
<button class= "btnLogout" id= "logout" type="button" name="esci" value="Logout" onClick="logout()" >Logout</button>
</div>
<div class="boxStHome">
<h1 id="welcome">
		Benvenuto
		<%=user.getName()%>,
		 ora tocca a te!
	</h1>
	<button class="btnHome mb-3 " data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">Gioca</button>
	<button class="btn2 btnScore" data-bs-toggle="modal" data-bs-target="#exampleModal2" data-bs-whatever="@fat" onclick="loadStudent()"><img src="images/trophy.png">Punteggi</button>
	<button class="btn2 btnScore" data-bs-toggle="modal" data-bs-target="#exampleModal3" data-bs-whatever="@fat" onclick="loadStudent()"><img src="images/infoLogo.png">Info</button>
		
		
		<!--  <input type="button" value="Stanze" id="listaS"onclick="show(2)"> 
		<input type="button" value="Punteggi" id="listaP" onclick="show(4)">
		<input type="button"value="Regole" id="rules" onclick="show(5)">-->
		

</div>



<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog d-flex flex-column align-items-center ">
    
	
		<%if(Rooms.isEmpty()){%>
			<p>Non ci sono stanze visualizzabili! </p>
		<%}
		int roomCount = 1;
		for (Room room : Rooms) {
		%>
		<div class="thumbnailRoom d-flex flex-column align-items-center p-4 mt-4 mb-4 justify-content-center">
		<p>"<%=room.getTitle()%> "</p>
		<p>
			<button class="thumbnailButton" data-bs-toggle="modal" data-bs-target="#exampleModal4" data-bs-whatever="@mdo"><img id="image_<%=roomCount%>"  src="images/<%=room.getThumbnail()%>.jpg" alt="<%=room.getThumbnail()%> " onclick=" movePassword(this)"></button>
		<p>
			<b>Professore: </b><%=room.getProfSurname()%> <%=room.getProfName()%>
		<p>
			<b>Materia: </b><%=room.getSubject()%> <input id="id_room_<%=roomCount%>" type="hidden" value="<%=room.getIdRoom()%>"> <input id="room_password_<%=roomCount%>" type="hidden" value="<%=room.getPassword()%>">
	    </div>
			<%
				roomCount++;
			
			}
			%>
		
	
</div>
</div>


<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" >
  <div class="modal-dialog d-flex flex-column align-items-center p-5">

		<%if(Rankings.isEmpty()){%>
		<p> Non sono presenti punteggi!</p>
		<%
		}else{
		%>
		
		<p><b> I tuoi punteggi: </b></p>
		<%
		for (Ranking ranking : Rankings) {
		%>
		
		<b>Titolo: </b>
			<%=ranking.getTitle()%></p>
			
			<b>Data: </b>
			<%=ranking.getDate()%></p>
		
			<b>Punteggio primo minigioco: </b>
			<%=ranking.getRank1()%></p>
		<p>
			<b>Punteggio secondo minigioco: </b>
			<%=ranking.getRank2()%></p>
		<p>
			<b>Punteggio terzo minigioco: </b>
			<%=ranking.getRank3()%></p>
		<p>
			<b>Punteggio quarto minigioco: </b>
			<%=ranking.getRank4()%></p>
		<p>
			<b>Punteggio totale: </b>
			<%=ranking.getTotalrank()%></p>

		<%
			}
		}
		%>


	


</div>
</div>


<div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog d-flex flex-column align-items-center ">
 
 

<p class= "p-5">		      
                
                Osserva la stanza e gli oggetti che la compongono, se noti
				qualcosa di strano o se qualcosa attira la tua attenzione clicca
				quell'area.
			    Se la tua intuizione è giusta sarai rimandato ad un
				minigioco, risolvilo e ottieni un oggetto in cambio, usa
				quell'oggetto dove ti sembra più utile e sblocca altri minigiochi.
				Attento però, durante i minigiochi cerca di fare meno errori
				possibili per ottenere un punteggio più alto a fine partita!  </p>
 
  
  </div>
  </div>
  
  <div class="modal fade" id="exampleModal4" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog d-flex flex-column align-items-center" style="
    min-height: 611px;">
  
  <form  id="block_3" action="./Game" method="POST" onsubmit="return false;">
	
		
		<h1>Password di accesso</h1>
		<input class="form-control text2" type="password" id="password" value="" placeholder="password" />
		<input id="id_room" name="id_room" type="hidden" value= "">
		<input class="btnA" type="submit" id="button" name="accedi" value="Accedi" onclick="passwordCheck()" />
	   

	</form>
	</div>
	</div>
<div class="foot">
<p>Copyright2020</p>
</div>

</div>





	  
<!-- onload="load()"
show3(); -->





	





</body>
</html>