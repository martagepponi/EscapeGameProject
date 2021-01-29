<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.*"%>
<%
	Quizgame minigame = (Quizgame) session.getAttribute("Minigame");
session.setAttribute("prize", minigame.getPrize());
String question = minigame.getQuestion();
String answer1 = minigame.getRightAnswer();
String answer2 = minigame.getWrong1();
String answer3 = minigame.getWrong2();

int initialAttempts = Quizgame.MAX_NUM_ERROR- minigame.getErrorNumber();
%>
<!DOCTYPE html>
<html>
<head>
		<link rel="stylesheet" href="css/Style.css">
	 	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
<title>Quiz</title>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>


<script>


	
	
	function load(){
		 var music = document.getElementById("myAudio"); 
	     music.play();
	}
	
	
	
	function Answer1(answerToCheck) {

		var answerToCheck = document.getElementById("key1").value;
		if (answerToCheck == "") {
			alert("");
			return false;
		} else {
			makeCall("GET", "QuizGame?action=insertAnswer&answer="
					+ answerToCheck, answerResponse);
		}
	}

	function Answer2(answerToCheck) {

		var answerToCheck = document.getElementById("key2").value;
		if (answerToCheck == "") {
			alert("");
			return false;
		} else {
			makeCall("GET", "QuizGame?action=insertAnswer&answer="
					+ answerToCheck, answerResponse);
		}
	}

	function Answer3(answerToCheck) {

		var answerToCheck = document.getElementById("key3").value;
		if (answerToCheck == "") {
			alert("");
			return false;
		} else {
			makeCall("GET", "QuizGame?action=insertAnswer&answer="
					+ answerToCheck, answerResponse);
		}
	}

	function answerResponse(req) {
		if (req.readyState == 4 && req.status == 200) {
			var response = JSON.parse(req.responseText);
			if (response.sessionExpired) {
				alert("Sessione scaduta!");
				document.location.href = "/Login.html";
			} else {
				if (response.outcome) {
					alert("Vinto!");
				    document.getElementById("divTentativi").style.display="none";
					document.getElementById("closechest").style.display = "none";
					document.getElementById("openchest").style.display = "block";
				    document.getElementById("score").innerHTML = response.score;
				  
					
				} else {
					if (response.finalOutcome == "L") {
						alert("Perso!");
						document.getElementById("attempts").innerHTML = attempts;
						document.getElementById("divTentativi").style.display="none"
						document.getElementById("closechest").style.display = "none";
						document.getElementById("openchest").style.display = "block";
						document.getElementById("score").innerHTML = response.score;
						
					} else {
						var attempts = response.attemptsRemained;
						document.getElementById("attempts").innerHTML = attempts;

					}
				}
			}
		}
	}

	function prize() {

		document.getElementById("Div").style.display = "none";
		document.getElementById("prize").style.display = "block";
	}
	
	function startMusic(){
		var music = document.getElementById("myAudio"); 
		music.play();
		}
	
	function pauseMusic(){
		var music = document.getElementById("myAudio"); 
		music.pause();
	}

</script>
<script type="text/javascript">
	function makeCall(method, url, cback) {

		var req = new XMLHttpRequest(); // visible dalla chiusura 
		req.onreadystatechange = function() { //associo un gestore dell'evento di cambio di stato di avanzamento della richiesta(0,1,2,3,4)
			cback(req)// chiusura, callback gestore evento di cambio stato
		};
		req.open(method, url);//una volta preparate la funzione di gestione evento  di risposta apro connessione http; true dafault asinc
		req.send();//invio richiesta http a server

	}

	
</script>
</head>
<body class="quizGame"  onload="load()">

	<div id="Div" class="quizQuestion">
	    <h2>QuizGame</h2>
	<p>
				Domanda:<br>
				<%=question%>
			</p>

		<div id="keys" align="center">

			<table  width="560" border="0">
				<tbody>
					<tr>
						<td  class="key" >
						<img src="images/quizgame/chiave.png" height="100" width="100">
							<input type="button" class="btnA" value="<%=answer1%>" id="key1" onclick="Answer1()"></td>

						<td class="key" >
						<img src="images/quizgame/chiave.png" height="100" width="100">
							<input type="button" class="btnA" value="<%=answer2%>" id="key2" onclick="Answer2()"></td>

						<td class="key" >
						<img src="images/quizgame/chiave.png" height="100" width="100">
							<input type="button" class="btnA" value="<%=answer3%>" id="key3" onclick="Answer3()"></td>

					</tr>
				</tbody>
			</table>
		</div>



		<div id="divTentativi" align="center">

			<p>
				 TENTATIVI RIMASTI: <span id="attempts"><%=initialAttempts%></span>
			</p>
		</div>


		<div id="chest" align="center">

			<img id="closechest" src="images/quizgame/closechest.png"
				height="250" width="270">
		</div>



		<div  id="openchest" align="center" style="display: none;">

			<img src="images/quizgame/openchest.png" height="250"width="270" > <br>
			<input type="button" class="btnA backR " value="Ritira premio" onclick="prize()">

		</div>
		

	<audio id="myAudio">
		<source src="music/askingquestions.mp3" type="audio/mpeg">

	</audio>


	<div id="musicButton" align="center">
		<img src="images/audioOff.png" onclick="pauseMusic()" >

		<img src="images/audioOn.png" onclick="startMusic()" >
	</div>
	</div>



	<div id="prize" align="center" style="display: none;">
	

		<p>Punteggio ottenuto: <span id="score"></span></p> 		

		<img src="images/<%=minigame.getPrize()%>.png" height="250"
			width="150">

		<p align="center">Hai trovato un oggetto. Troverai questo oggetto
			nell'inventario, ritorna alla stanza e clicca nel punto in cui
			l'oggetto trovato può risultarti utile!</p>

		<a href="./Game"> <input type="button" class="btnA backR" value="Torna alla stanza">
		</a>




</div>







</body>
</html>