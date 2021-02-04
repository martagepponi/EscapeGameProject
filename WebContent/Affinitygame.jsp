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
        <link rel="stylesheet" href="css/Style.css">
	 	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
<title>Affinity</title>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>


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
			document.location.href="/Login.html";
		}
	}		
}

function Word(wordToCheck){
	var wordToCheck = document.getElementById("wordToCheck").value;
	if(wordToCheck == ""){
		alert("Devi riempire il campo vuoto!");
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
				//alert("punti di errore: " + response.errorNumber);
				//alert("Punteggio ottenuto: " + response.score );
				document.getElementById("title").style.display="none";
				document.getElementById("prize").style.display="block";
				document.getElementById("divMain").style.display="none";
				document.getElementById("div2").style.display="none";
				document.getElementById("score").innerHTML = response.score;
				document.getElementById("divAttempts").style.display="none";
				document.getElementById("question2").style.display="none";
			} else  {
				if (response.finalOutcome == "L") {
					alert("Perso!");
				
					document.getElementById("title").style.display="none";
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
					//alert("number attempts rimasti" + attempts);
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



<body class= "affinityGame" onload="load()">
<div id="title">
       <h2>Il gioco delle affinità</h2>
       <h3> Trova una parola che leghi le quattro parole scritte qui sotto:</h3>
     </div>  
	<div id="divMain" class="line-1 anim-typewriter">	     
		<p id="affinityWord"><%=word1 %>, <%=word2%>, <%=word3%>, <%=word4%></p>
	</div>
	<p id="question2" align="center" style="display: <%= (minigame.isHintSelected() ? "block" : "none") %>;"><%=(minigame.isHintSelected() ? minigame.getHint() : "") %></p>

	<div id="div2" class="correctWord">
		<input id="wordToCheck" class=" text2" type="text" placeholder="inserisci la parola più adatta" value="">
		<input type="button" class="btnA send" value="Invia" onclick="Word()">
		
		<p>
			<a href="javascript:hint()">Suggerimento</a>
				
		</p>
		
		
<!--  <audio id="myAudio">
		<source src="music/askingquestions.mp3" type="audio/mpeg">

	</audio>


	
	<div id="musicButton" align="center">
		<img src="images/audioOff.png" onclick="pauseMusic()" >

		<img src="images/audioOn.png" onclick="startMusic()" >
	</div> -->

	</div>
	



	<div id="prize" align="center" style="display: none;">
	
		<p id="showWord" style="display: none;">La parola corretta era: <span id="correctWord"></span></p> 		

		<p>Punteggio ottenuto: <span id="score"></span></p> 		

		<img src="images/<%=minigame.getPrize()%>.png" height="250"width="150">					
        
		<p align="center">Hai trovato un oggetto.<br> Troverai questo oggetto
			nell'inventario, ritorna alla stanza e clicca nel punto in cui
			l'oggetto trovato può risultarti utile!</p><br>
		<a href="./Game"> <input type="button" class="btnA backR " value="Torna alla stanza"></a> 

	</div>
	
	
	




	<div id="divAttempts" align="center">

 		<p> TENTATIVI RIMASTI: <span id="attempts"><%= initialAttempts %></span></p>
	</div>

<audio id="myAudio">
		<source src="music/askingquestions.mp3" type="audio/mpeg">

	</audio>


	
	<div id="musicButton" align="center">
		<img src="images/audioOff.png" onclick="pauseMusic()" >

		<img src="images/audioOn.png" onclick="startMusic()" >
	</div>

</body>
</html>