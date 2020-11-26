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
%>

<style>
img {
	cursor: pointer;
}
button{
cursor: pointer;
}
</style>

<script type="text/javascript">
	var id_stanza = localStorage.getItem("id_stanza");
	var password_stanza = localStorage.getItem("password_stanza");

	function carica() {
		document.getElementById('blocco_2').style.display = "none";
		document.getElementById('blocco_3').style.display = "none";
		document.getElementById('blocco_4').style.display = "none";
		/*if (id_stanza != null && password_stanza != null){
			mostra(2);}*/
	}

	function mostra(numero) {
		if (numero == 2) {
			document.getElementById('blocco_2').style.display = "block";
			document.getElementById('blocco_4').style.display = "none";
		}
		if (numero == 3) {
			document.getElementById('blocco_2').style.display = "none";
			document.getElementById('blocco_3').style.display = "block";
			document.getElementById('blocco_4').style.display = "none";
		}
		if (numero == 4) {
			document.getElementById('blocco_2').style.display = "none";
			document.getElementById('blocco_3').style.display = "none";
			document.getElementById('blocco_4').style.display = "block";

		}
	}

	function spostaPassword(immagine) {
		var numero_immagine = immagine.id.split('_')[1];
		id_stanza = document.getElementById('id_stanza_' + numero_immagine).value;
		password_stanza = document.getElementById('password_stanza_'
				+ numero_immagine).value;
		localStorage.setItem("id_stanza", id_stanza);
		localStorage.setItem("password_stanza", password_stanza);

	}

	function logout() {

		localStorage.removeItem("id_stanza");
		localStorage.removeItem("password_stanza");
		window.location.href = "./Login.html";
	}
	function controlloPassword() {
		var password = document.getElementById('password').value;
		if (password != '') {
			
			if (password != password_stanza ) {
				alert('password non valida!');
				return false;
			}else{
				
			document.getElementById('id_stanza').value = localStorage.getItem("id_stanza");
			document.getElementById("blocco_3").submit();
		}
		}else{
			alert('password non valida!');
		}
		
	}
</script>
</head>
<body onload="carica()">

	<div name="main" id="blocco_1">
		<table width="110%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>&nbsp; Benvenuto <%=user.getName()%> !
				</td>
				<td align="center"><input type="button" name="esci"
					value="Logout" onClick="logout()" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="maindiv" name="maindiv"
						style="width: 100%; display: flex; height: 200px; margin-top: 5px;">
						<div id="menu"
							style="margin: 0px; width: 20%; height: 100%; border: 1px black solid;">
							<table>
								<tr>
									<th align="center">MENU'</th>
								</tr>
								<tr>
									<td><input type="button" value="Lista Stanze" id="listaS"
										onclick="mostra(2)"></td>
								</tr>
								<tr>
									<td><input type="button" value="Punteggi" id="listaP"
										onclick="mostra(4)"></td>
								</tr>
							</table>
						</div>
						<div id="content" style="width: 80%; margin: 0px; height: 100%;">
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>







	<div id="blocco_2">
		<%
			List<Room> Rooms = new ArrayList<>();
		Rooms = (List<Room>) request.getAttribute("Rooms");
		int contaStanza = 1;
		for (Room room : Rooms) {
		%>
		<p>
			<b>Stanza:</b><a><img id="immagine_<%=contaStanza%>" height="62"
				width="62" src="images/<%=room.getThumbnail()%>.jpg"
				alt="<%=room.getThumbnail()%> "
				onclick="mostra(3); spostaPassword(this)"></a><br> <br>
		<p>
			<b>Creatore stanza:</b><%=room.getProfName()%><br> <br>
		<p>
			<b>Materia:</b><%=room.getSubject()%><br> <br> <input
				id="id_stanza_<%=contaStanza%>" type="hidden"
				value="<%=room.getIdRoom()%>"> <input
				id="password_stanza_<%=contaStanza%>" type="hidden"
				value="<%=room.getPassword()%>">
			<%
				contaStanza++;
			}
			%>
		
	</div>





	<form id="blocco_3" action="./Game" method="POST" onsubmit="return false;">
	
		<div style ="margin: auto;">
		<h1>Password di accesso</h1>
		<input type="password" id="password" value="" placeholder="password" />
		<input id="id_stanza" name="id_stanza" type="hidden" value= "">
		<input type="submit" id="button" name="accedi" value="Accedi" onclick="controlloPassword()" />
	   </div>

	</form>






	<div id="blocco_4">
		<%
			List<Ranking> Rankings = new ArrayList<>();
		Rankings = (List<Ranking>) request.getAttribute("Rankings");
		for (Ranking ranking : Rankings) {
		%>

		<p>
			<b>Stanza: </b><img id="immagine" height="62" width="62"
				src="images/matematica/<%=ranking.getThumbnail()%>.jpg"
				alt="<%=ranking.getThumbnail()%>">
		</p>


		<p>
			<b>Data: </b>
			<%=ranking.getDate()%></p>
		<p>
			<b>ESCAPEGAME ID: </b>
			<%=ranking.getIdroom()%></p>
		<p>
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
		%>


	</div>


</body>
</html>