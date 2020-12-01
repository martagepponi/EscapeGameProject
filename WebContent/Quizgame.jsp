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

//   String question2 = minigame.getHint();
int tentativiIniziali = Quizgame.MAX_NUM_ERRORI;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz</title>

<style>
.button{
 background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;

}

</style>

<script>
	// var hintRequested = 0;

	// function hintResponse(req) {

	// 	if (req.readyState == 4) {
	// 		var message = req.responseText;
	// 		//alert(message);
	// 		if (req.status == 200) {
	// 			var response = JSON.parse(req.responseText);
	// 			if (response.sessionExpired) {
	// 				alert("Sessione scaduta!");
	// 				document.location.href="/Login.html";
	// 			} else {
	// 				if (response.esito) {

	// 					document.getElementById("question2").innerHTML = response.question2;
	// 					document.getElementById("question2").style.display = "block";
	// 				}
	// 			}
	// 		} else {
	// 			// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
	// 		}
	// 	}		
	// }

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
				if (response.esito) {
					alert("Vinto!");
					//	alert("punti di errore: " + response.errorNumber);
					//	alert("Punteggio ottenuto: " + response.punteggio );
					//	document.getElementById("arpa").style.display="block";
					document.getElementById("closechest").style.display = "none";
					document.getElementById("openchest").style.display = "block";
					// 				document.getElementById("div2").style.display="none";
					// 				document.getElementById("punteggio").innerHTML = response.punteggio;
					// 				document.getElementById("divTentativi").style.display="none";
					// 				document.getElementById("question2").style.display="none";
				} else {
					if (response.esitoFinale == "P") {
						alert("Perso!");
						// 					alert("la parola corretta era: " + response.correctWord)
						// 					alert("punti di errore: " + response.errorNumber);
						// 					alert("Punteggio ottenuto: " + response.punteggio );
						// 					document.getElementById("arpa").style.display="block";
						// 					document.getElementById("divMain").style.display="none";
						// 					document.getElementById("div2").style.display="none";
						// 					document.getElementById("correctWord").innerHTML = response.correctWord;
						// 					document.getElementById("showWord").style.display="block";
						// 					document.getElementById("punteggio").innerHTML = response.punteggio;
						// 					document.getElementById("divTentativi").style.display="none";
						// 					document.getElementById("question2").style.display="none";
					} else {
						var tentativi = response.tentativiRimasti;
						alert("numero tentativi rimasti" + tentativi);
						document.getElementById("tentativi").innerHTML = tentativi;

					}
				}
			}
		}
	}

	function prize() {

		document.getElementById("Div").style.display = "none";
		document.getElementById("prize").style.display = "block";
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

	// function hint() {
	// 	// chiamata al controller per visionareil suggerimento
	// 	// esito : suggerimento
	// 	makeCall("GET", "QuizGame?action=hint", hintResponse);

	// }
</script>
</head>
<body>

	<div id="Div">

		<div id="chiavi" align="center">

			<table cellspacing="100" cellpadding="2" width="560" border="0">
				<tbody>
					<tr>
						<td valign="top" width="186"><img
							src="images/quizgame/chiave.png" height="100" width="100"><input
							type="button" value="<%=answer1%>" id="key1" onclick="Answer1()"></td>

						<td valign="top" width="186"><img
							src="images/quizgame/chiave.png" height="100" width="100"><input
							type="button" value="<%=answer2%>" id="key2" onclick="Answer2()"></td>

						<td valign="top" width="186"><img
							src="images/quizgame/chiave.png" height="100" width="100"><input
							type="button" value="<%=answer3%>" id="key3" onclick="Answer3()"></td>

					</tr>
				</tbody>
			</table>
		</div>



		<div id="divTentativi" align="center">

			<p>
				NUMERO TENTATIVI RIMASTI: <span id="tentativi"><%=tentativiIniziali%></span>
			</p>
		</div>


		<div id="chest" align="center">
			<p>
				Domanda:
				<%=question%>
			</p>
			<!-- <h2 id="question2" align="center" style="display: none;"></h2> -->
			<!-- <p><a href="javascript:hint()">Suggerimento</a></p> -->

			<img id="closechest" src="images/quizgame/closechest.jpg"
				height="600" width="700">
		</div>



		<div  id="openchest" align="center" style="display: none;">

			<img src="images/quizgame/openchest.jpg" height="600"
				width="700" > <input type="button"
				value="Ritira premio" onclick="prize()">

		</div>
	</div>


	<div id="prize" align="center" style="display: none;">
	
<!-- 		<p id="showWord" style="display: none;">La parola corretta era: <span id="correctWord"></span></p> 		 -->

<!-- 		<p>Punteggio ottenuto: <span id="punteggio"></span></p> 		 -->

		<img src="images/<%=minigame.getPrize()%>.png" height="500"
			width="300">

		<p align="center">Hai trovato un oggetto. Troverai questo oggetto
			nell'inventario, ritorna alla stanza e clicca nel punto in cui
			l'oggetto trovato può risultarti utile!</p>

		<a href="./Game"> <input type="button" class="button" value="Torna alla stanza">
		</a>




</div>







</body>
</html>