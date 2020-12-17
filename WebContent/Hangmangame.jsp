<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.*"%>
<%
	Hangmangame minigame = (Hangmangame)session.getAttribute("Minigame");
session.setAttribute("prize", minigame.getPrize());
	//int lunghezzaParola = minigame.getWord().length();
	String word = minigame.getWord();
	String displayWord = minigame.getDisplayWord();
	String question1 = minigame.getQuestion1();
	String question2 = minigame.getQuestion2();
	System.out.println("question2:" + question2);
	String errorNumber =""+minigame.getErrorNumber();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Impiccato</title>
<script type="text/javascript">
	//FUNZIONI HANGMANGAME

	var can_play = true;
	var display_word = "<%=displayWord %>";
	var used_letters = "";
	var wrong_guesses = 0;
	var correctWord = "";
	var hintRequested = 0;
	var finalOutcome = "";
	var score = 0;

	
	
	
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
				// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
				// location errorPage.jsp con tasto di ritorna a login e invalida sessione
			}
		}		
	}

	function selectLetterResponse(req) {
		
		if (req.readyState == 4) {
			var message = req.responseText;
			//alert(message);
			if (req.status == 200) {
				// SE LA RISPOSTA è OK
				var response = JSON.parse(req.responseText);
				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href="/Login.html";
				} else {
					display_word = response.displayWord;					
					wrong_guesses = response.errorNumber;
					if (response.outcome) {
						if (response.finalOutcome == "W") {
							finalOutcome = "W";
							score = response.score;
						}
					} else {
						if (response.finalOutcome == "L") {
							finalOutcome = "L";
							score = response.score;
							correctWord = response.correctWord;
						}
					}
				}
			
				
				document.game.displayWord.value = display_word;
				eval("document.hm.src=\"images/hangmangame/hm" + wrong_guesses + ".gif\"");
				if (finalOutcome == "W") {
					alert("Vinto!");
					alert("punti di errore: " + wrong_guesses);
					alert("Punteggio ottenuto: " + score );
					can_play = false;
					
					document.getElementById("fireExtinguisher").style.display="block";
					
					document.getElementById("Hangmangame").style.display = "none";
					
					
					
				} else if (finalOutcome == "L") {
					alert("Perso!");
					alert("la parola corretta era: " + correctWord)
					alert("punti di errore: " + wrong_guesses);
					alert("Punteggio ottenuto: " + score );
					can_play = false;
					
					document.getElementById("fireExtinguisher").style.display="block";
					
					document.getElementById("Hangmangame").style.display = "none";
				}
				
			} else {
				// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
			}
		}		
	}
	
	function selectLetter(l) {
		document.getElementById("fireExtinguisher").style.display="none";
		if (can_play == false) {
			return;
		}

		if (used_letters.indexOf(l) != -1) {
			return;
		}

		used_letters += l;
		document.game.usedLetters.value = used_letters;

		makeCall("GET", "HangmanGame?action=selectLetter&letterSelected=" + l, selectLetterResponse);

	}

	function hint() {
		// chiamata al controller per visionareil suggerimento
		// outcome: suggerimento
		makeCall("GET", "HangmanGame?action=hint", hintResponse);

	}
	
	
	
	function startMusic(){
		var music = document.getElementById("myAudio"); 
		music.play();
		}
	
	function pauseMusic(){
		var music = document.getElementById("myAudio"); 
		music.pause();
	}

	//FINE SCRIPT HANGMANGAME
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
</script>
</head>
<body onload="load()">


	<div id="Hangmangame" align="center">
		<h1>Questo è il gioco dell&apos;Impiccato</h1>


		<h2 id="question1"><%=question1 %></h2>
		<h2 id="question2" style="display: none;"></h2>
		<p>
			<img src="images/hangmangame/hm<%= errorNumber %>.gif" height="125" width="75"
				name="hm">
		</p>
		<form name="game">
			<p>
				Parola: <input id="word" type="text" name="displayWord" value="<%=displayWord %>" readonly="true" /><br>
				Lettere: <input type="text" name="usedLetters" readonly="true">
			</p>
		</form>

		<p>
			<a href="javascript:selectLetter('A');">A</a> | <a
				href="javascript:selectLetter('B');">B</a> | <a
				href="javascript:selectLetter('C');">C</a> | <a
				href="javascript:selectLetter('D');">D</a> | <a
				href="javascript:selectLetter('E');">E</a> | <a
				href="javascript:selectLetter('F');">F</a> | <a
				href="javascript:selectLetter('G');">G</a> | <a
				href="javascript:selectLetter('H');">H</a> | <a
				href="javascript:selectLetter('I');">I</a> | <a
				href="javascript:selectLetter('J');">J</a> | <a
				href="javascript:selectLetter('K');">K</a> | <a
				href="javascript:selectLetter('L');">L</a> | <a
				href="javascript:selectLetter('M');">M</a><br> <a
				href="javascript:selectLetter('N');">N</a> | <a
				href="javascript:selectLetter('O');">O</a> | <a
				href="javascript:selectLetter('P');">P</a> | <a
				href="javascript:selectLetter('Q');">Q</a> | <a
				href="javascript:selectLetter('R');">R</a> | <a
				href="javascript:selectLetter('S');">S</a> | <a
				href="javascript:selectLetter('T');">T</a> | <a
				href="javascript:selectLetter('U');">U</a> | <a
				href="javascript:selectLetter('V');">V</a> | <a
				href="javascript:selectLetter('W');">W</a> | <a
				href="javascript:selectLetter('X');">X</a> | <a
				href="javascript:selectLetter('Y');">Y</a> | <a
				href="javascript:selectLetter('Z');">Z</a>
		</p>

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


	<!-- ------------------------------------------------------------------------ -->
	
<!-- 		DIV CON IMMAGINE ESTINTORE CHE APPARE A FINE MINIGIOCO -->


<div id="fireExtinguisher" align="center" style="display: none;">

<img src="images/<%=minigame.getPrize()%>.png" height="500" width="300">

<p align="center">  Hai trovato un oggetto.
                    Troverai questo oggetto nell'inventario, 
                    ritorna alla stanza e clicca nel punto in cui l'oggetto
                     trovato può risultarti utile! </p>
                     
<a href="./Game"> <input type="button" value="Torna alla stanza" > </a>


</div>








	

</body>
</html>