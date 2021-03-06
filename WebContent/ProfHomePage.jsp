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
<title>Home Page</title>


<%

User user = (User) session.getAttribute("user");

%>


<script type="text/javascript">
	
	
	
	function carica(){
		document.getElementById('blocco2').style.display= "none";
		document.getElementById('blocco3').style.display= "none";
	}
	
	function mostra(numero){
		if (numero==2) {
		document.getElementById('blocco2').style.display = "block";
		document.getElementById('blocco3').style.display= "none";
		}
		
		if(numero==3){ 
			document.getElementById('blocco2').style.display= "none";
			document.getElementById('blocco3').style.display = "block";
		}
	}
	
	
	function logout() {
		window.location.href = "./Login.html";
	}
</script>
</head>
<body onload="carica()">

	<div name="main" id="blocco1">
		<table width="110%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>&nbsp; Benvenuto Prof.  <%= user.getSurname()%> <%= user.getName()%>  
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
									<td><input type="button" value="Stanze create"
										id="stanze" onclick="mostra(2)"></td>
								</tr>
								<tr>
									<td><input type="button" value="Visualizza punteggi"
										id="listaPunteggi" onclick="mostra(3)"></td>
									
								</tr>

								<tr>
									<td><input type="button" value="Crea stanza" id="crea"></td>
									<!-- CAMBIA FUNZIONE MOSTRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA -->
								</tr>
							</table>
						</div>
						<div id="menu" style="width: 80%; margin: 0px; height: 100%;">
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>


	<div id="blocco2">

		<%
	

List<Room> createdRooms = new ArrayList<>();
createdRooms = (List<Room>) request.getAttribute("createdRooms");
			
		for (Room room : createdRooms) {
		%>

		<p>
			<b>Stanza: </b><img id="immagine" height="62" width="62"
				src="images/<%= room.getThumbnail()  %>.jpg"
				alt="<%=room.getThumbnail()%>">
		</p>


		<p>
			<b>Data: </b>
			<%=room.getDate()%></p>
		<p>
		<p>
			<b>Anno: </b>
			<%=room.getYear()%></p>
		<p>
		<p>
			<b>Materia: </b>
			<%=room.getSubject()%></p>
		<p>
		<p>
			<b>ID Stanza: </b>
			<%=room.getIdRoom()%></p>
		<p>


			<%
			}
		%>
		
	</div>
	
	
	
	
<div id="blocco3">

		<%
	

List<Ranking> Rankings = new ArrayList<>();
Rankings = (List<Ranking>) request.getAttribute("rankings");
			
		for (Ranking ranking : Rankings) {
		%>

		<p>
			<b>Stanza: </b><img id="immagine" height="62" width="62"
				src="images/matematica/<%= ranking.getThumbnail()  %>.jpg"
				alt="<%=ranking.getThumbnail()%>">
		</p>


		<p>
			<b>Data: </b>
			<%=ranking.getDate()%></p>
		<p>
		<p>
			<b>Studente </b>
			<%=ranking.getUser()%></p>
		<p>
		<p>
			<b>minigame1 rank </b>
			<%=ranking.getRank1()%></p>
		<p>
		<p>
			<b>minigame2 rank </b>
			<%=ranking.getRank2()%></p>
		<p>
		<p>
			<b>minigame3 rank </b>
			<%=ranking.getRank3()%></p>
		<p>
		<p>
			<b>minigame4 rank </b>
			<%=ranking.getRank4()%></p>
		<p>
		<p>
			<b>total rank </b>
			<%=ranking.getTotalrank()%></p>
		<p>
		


			<%
			}
		%>
		
	</div>



</body>
</html>