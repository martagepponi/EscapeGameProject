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
<meta charset="ISO-8859-1">
<title>HomePage</title>

<%
	//LISTA ROOM 

User user = (User) session.getAttribute("user");

List<Room> Rooms = new ArrayList<>();
Rooms = (List<Room>) session.getAttribute("Rooms");

List<Ranking> Rankings = new ArrayList<>();
Rankings = (List<Ranking>) session.getAttribute("Rankings");
%>

<style>
#welcome {
text-align: center;
margin-top: 15%;
font-family: fantasy;


}

#block_1{
text-align: center;
}

img {
	cursor: pointer;
}

button {
	cursor: pointer;
}


body {
	background-image: url("images/backgroundHome.jpg");
	background-repeat: no-repeat;
	background-size: cover;
}
</style>

<script type="text/javascript">
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
</script>
</head>
<body onload="load()">



<h1>TITOLO SW</h1>
	<h1 id="welcome">
		Benvenuto
		<%=user.getName()%>!
	</h1>

	<div name="main" id="block_1">

		<input id= "logout" type="button" name="esci" value="Logout" onClick="logout()" />
		<input type="button" value="Stanze" id="listaS"
			onclick="show(2)"> <input type="button" value="Punteggi"
			id="listaP" onclick="show(4)"> <input type="button"
			value="Regole" id="rules" onclick="show(5)">

	</div>



	<div id="block_2">
		<%if(Rooms.isEmpty()){%>
			<p>Non ci sono stanze visualizzabili! </p>
		<%}
		int roomCount = 1;
		for (Room room : Rooms) {
		%>
		
		<p><b> Titolo: </b><%=room.getTitle()%> </p>
		<p>
			<b>Stanza:</b><a><img id="image_<%=roomCount%>" height="62"
				width="62" src="images/<%=room.getThumbnail()%>.jpg"
				alt="<%=room.getThumbnail()%> "
				onclick="show(3); movePassword(this)"></a><br> <br>
		<p>
			<b>Creatore stanza:</b><%=room.getProfSurname()%> <%=room.getProfName()%><br> <br>
		<p>
			<b>Materia:</b><%=room.getSubject()%><br> <br> <input
				id="id_room_<%=roomCount%>" type="hidden"
				value="<%=room.getIdRoom()%>"> <input
				id="room_password_<%=roomCount%>" type="hidden"
				value="<%=room.getPassword()%>">
			<%
				roomCount++;
			}
			%>
		
	</div>





	<form id="block_3" action="./Game" method="POST" onsubmit="return false;">
	
		<div style ="margin: auto;">
		<h1>Password di accesso</h1>
		<input type="password" id="password" value="" placeholder="password" />
		<input id="id_room" name="id_room" type="hidden" value= "">
		<input type="submit" id="button" name="accedi" value="Accedi" onclick="passwordCheck()" />
	   </div>

	</form>






	<div id="block_4">
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



<div id="block_5">
<p>		        Osserva la stanza e gli oggetti che la compongono, se noti
				qualcosa di strano o se qualcosa attira la tua attenzione clicca
				quell'area.
			    Se la tua intuizione � giusta sarai rimandato ad un
				minigioco, risolvilo e ottieni un oggetto in cambio, usa
				quell'oggetto dove ti sembra pi� utile e sblocca altri minigiochi.
				Attento per�, durante i minigiochi cerca di fare meno errori
				possibili per ottenere un punteggio pi� alto a fine partita!  </p>

</div>

</body>
</html>