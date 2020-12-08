<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.*"%>
<%
	Affinitygame minigame = (Affinitygame)session.getAttribute("Minigame");
session.setAttribute("prize", minigame.getPrize());
    String word1 = minigame.getWord1();
    String word2 = minigame.getWord2();
    String word3 = minigame.getWord3();
    String word4 = minigame.getWord4();
    
    String question2 = minigame.getHint();
    int initialAttempts = Affinitygame.MAX_NUM_ERROR- minigame.getErrorNumber();
     
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Affinity</title>

<style>
/* /* Google Fonts */

/* @import url(https://fonts.googleapis.com/css?family=Anonymous+Pro);  */
/*  Global  */
html {
	min-height: 100%;
	overflow: hidden;
}

body {
	height: calc(100vh - 8em);
	padding: 4em;
	color: rgba(255, 255, 255, .75);
	font-family: 'Anonymous Pro', monospace;
	background-color: rgb(25, 25, 25);
}

.line-1 {
	position: relative;
	top: 50%;
	width: 24em;
	margin: 0 auto;
	border-right: 2px solid rgba(255, 255, 255, .75);
	font-size: 180%;
	text-align: center;
	white-space: nowrap;
	overflow: hidden;
	transform: translateY(-50%);
}

.correctWord {
	position: relative;
	top: 50%;
	width: 24em;
	margin: 0 auto;
	font-size: 180%;
	text-align: center;
	
}

/* Animation */
.anim-typewriter {
	animation: typewriter 4s steps(44) 1s 1 normal both, blinkTextCursor
		500ms steps(44) infinite normal;
}

@keyframes typewriter {
	from {width: 0;
}

to {
	width: 24em;
}

}
@keyframes blinkTextCursor {
	from {border-right-color: rgba(255, 255, 255, .75);
}

to {
	border-right-color: transparent;
}
}



.button{
 background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;

}
</style>

<script>
var hintRequested = 0;



function load(){
	 var music = document.getElementById("myAudio"); 
     music.play();
}


function hintResponse(req) {
	
	if (req.readyState == 4) {
		var message = req.responseText;
		//alert(message);
		if (req.status == 200) {
			var response = JSON.parse(req.responseText);
			if (response.sessionExpired) {
				alert("Sessione scaduta!");
				document.location.href="/Login.html";
			} else {
				if (response.outcome) {
					
					document.getElementById("question2").innerHTML = response.question2;
					document.getElementById("question2").style.display = "block";
				}
			}
		} else {
			// SE LA RISPOSTA � UN ERRORE(400, 401, 500)
			// TODO: FABIO.
		}
	}		
}

function Word(wordToCheck){
	var wordToCheck = document.getElementById("wordToCheck").value;
	if(wordToCheck == ""){
		alert("Non puoi inserire una parola vuota");
		return false;
	}else{
		makeCall("GET", "AffinityGame?action=insertWord&word=" + wordToCheck, wordResponse);
	}
}

function wordResponse(req){
	if (req.readyState == 4 && req.status == 200) {
		var response = JSON.parse(req.responseText);
		if (response.sessionExpired) {
			alert("Sessione scaduta!");
			document.location.href="/Login.html";
		} else {
			if(response.outcome) {
				alert("Vinto!");
				alert("punti di errore: " + response.errorNumber);
				alert("Punteggio ottenuto: " + response.score );
				document.getElementById("prize").style.display="block";
				document.getElementById("divMain").style.display="none";
				document.getElementById("div2").style.display="none";
				document.getElementById("score").innerHTML = response.score;
				document.getElementById("divAttempts").style.display="none";
				document.getElementById("question2").style.display="none";
			} else  {
				if (response.finalOutcome == "L") {
					alert("Perso!");
					alert("la parola corretta era: " + response.correctWord)
					alert("punti di errore: " + response.errorNumber);
					alert("Punteggio ottenuto: " + response.score );
					document.getElementById("prize").style.display="block";
					document.getElementById("divMain").style.display="none";
					document.getElementById("div2").style.display="none";
					document.getElementById("correctWord").innerHTML = response.correctWord;
					document.getElementById("showWord").style.display="block";
					document.getElementById("score").innerHTML = response.score;
					document.getElementById("divAttempts").style.display="none";
					document.getElementById("question2").style.display="none";
				} else {
					var attempts = response.attemptsRemained;
					alert("number attempts rimasti" + attempts);
					document.getElementById("attempts").innerHTML = attempts;
					
				}
			}
		}		 
	}
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
	req.onreadystatechange = function() {              //associo un gestore dell'evento di cambio di stato di avanzamento della richiesta(0,1,2,3,4)
		cback(req)// chiusura, callback gestore evento di cambio stato
	}; 
	req.open(method, url);//una volta preparate la funzione di gestione evento  di risposta apro connessione http; true dafault asinc
	req.send();//invio richiesta http a server

}


function hint() {
	// chiamata al controller per visionareil suggerimento
	// outcome: suggerimento
	makeCall("GET", "AffinityGame?action=hint", hintResponse);

}
</script>

</head>



<body onload="load()">
	<div id="divMain" class="line-1 anim-typewriter">
		<p><%=word1 %>, <%=word2%>, <%=word3%>, <%=word4%></p>
	
	</div>
	<h2 id="question2" align="center" style="display: <%= (minigame.isHintSelected() ? "block" : "none") %>;"><%=(minigame.isHintSelected() ? minigame.getHint() : "") %></h2>

	<div id="div2" class="correctWord">
		<input id="wordToCheck" type="text" placeholder="Inserisci parola" value="">
		<input type="button" value="Invia" onclick="Word()">
		
		<p>
			<a href="javascript:hint()">Suggerimento</a>
				
		</p>
		
		
<audio id="myAudio">
		<source src="music/askingquestions.mp3" type="audio/mpeg">

	</audio>


	<div id="musicButton" align="center">
		<button onclick="pauseMusic()" type="button">Pause Audio</button>

		<button onclick="startMusic()" type="button">Play Audio</button>
	</div>

	</div>
	



	<div id="prize" align="center" style="display: none;">
	
		<p id="showWord" style="display: none;">La parola corretta era: <span id="correctWord"></span></p> 		

		<p>Punteggio ottenuto: <span id="score"></span></p> 		

		<img src="images/<%=minigame.getPrize()%>.png" height="500"
			width="300">
			
			
<a href="./Game"> <input type="button" class="button" value="Torna alla stanza"></a>  


		<p align="center">Hai trovato un oggetto. Troverai questo oggetto
			nell'inventario, ritorna alla stanza e clicca nel punto in cui
			l'oggetto trovato pu� risultarti utile!</p>

	</div>
	
	
	




	<div id="divAttempts" align="center">

 		<p>number TENTATIVI RIMASTI: <span id="attempts"><%= initialAttempts %></span></p>
	</div>



</body>
</html>