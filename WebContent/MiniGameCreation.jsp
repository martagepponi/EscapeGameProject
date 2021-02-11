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
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<meta charset="ISO-8859-1">



<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>Minigame Creation</title>
<%
	User user = (User) session.getAttribute("user");
String name = user.getName();
String surname = user.getSurname();
String username = user.getUsername();
int idUser = user.getIduser();
%>
<script type="text/javascript">
	function backHome() {
		window.location.href = "./HomePage";
	}

	function makeCall(method, url, cback) {

		var req = new XMLHttpRequest(); // visible dalla chiusura 
		req.onreadystatechange = function() { //associo un gestore dell'evento di cambio di stato di avanzamento della richiesta(0,1,2,3,4)
			cback(req)// chiusura, callback gestore evento di cambio stato
		};
		req.open(method, url);//una volta preparate la funzione di gestione evento  di risposta apro connessione http; true dafault asinc
		req.send();//invio richiesta http a server

	}

	function loadSubmitForm() {
		var createMg1 = document.getElementById('createMg1');
		createMg1.addEventListener('click', createMinigameH, false);
		var createMg2 = document.getElementById('createMg2');
		createMg2.addEventListener('click', createMinigameA, false);
		var createMg3 = document.getElementById('createMg3');
		createMg3.addEventListener('click', createMinigameQ, false);

		makeCall("GET", "RoomCreation?action=subjectList", populateSubject);
	}
	function populateSubject(req) {

		if (req.readyState == 4) {
			var message = req.responseText;
			if (req.status == 200) {
				var response = JSON.parse(req.responseText);

				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href = "/Login.html";
				} else {
					if (response.outcome) {
						var subjectList = response.subjectList;
						var select1 = document
								.getElementById("subjectListCombo1");
						clear(select1);
						for (i = 0; i < subjectList.length; i++) {
							//popolo combobox
							var subject = subjectList[i];

							var el = document.createElement("option");

							el.textContent = subject.name + " " + subject.year;
							el.value = subject.idsubject;
							select1.appendChild(el);
						}
						var select2 = document
								.getElementById("subjectListCombo2");
						clear(select2);
						for (i = 0; i < subjectList.length; i++) {
							//popolo combobox
							var subject = subjectList[i];

							var el = document.createElement("option");

							el.textContent = subject.name + " " + subject.year;
							el.value = subject.idsubject;
							select2.appendChild(el);
						}
						var select3 = document
								.getElementById("subjectListCombo3");
						clear(select3);
						for (i = 0; i < subjectList.length; i++) {
							//popolo combobox
							var subject = subjectList[i];

							var el = document.createElement("option");

							el.textContent = subject.name + " " + subject.year;
							el.value = subject.idsubject;
							select3.appendChild(el);
						}
					}

				}
			}
		} else {
			// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
			// TODO: FABIO.
		}

	}
	function createMinigameH() {
		var type = "hangmangame";
		var hangmanWord = document.getElementById("HangmanWord").value;
		var questionH = document.getElementById("questionH").value;
		var hintH = document.getElementById("hintH").value;
		var select1 = document.getElementById("subjectListCombo1");
		var idSubject = select1.value;
		if (hangmanWord == '' || questionH == '' || hintH == '') {
			empty_field1.style.display = "block";
			return false;
		}
		makeCall("GET", "RoomCreation?action=createMinigame&type=" + type
				+ "&hangmanWord=" + hangmanWord + "&questionH=" + questionH
				+ "&hintH=" + hintH + "&idSubject=" + idSubject,
				confirmMinigameCreation);
	}
	function createMinigameA() {
		var type = "affinitygame"
		var affinityWord = document.getElementById("AffinityWord").value;
		var word1A = document.getElementById("Word1A").value;
		var word2A = document.getElementById("Word2A").value;
		var word3A = document.getElementById("Word3A").value;
		var word4A = document.getElementById("Word4A").value;
		var hintA = document.getElementById("hintA").value;
		var select2 = document.getElementById("subjectListCombo2");
		var idSubject = select2.value;
		if (affinityWord == '' || word1A == '' || word2A == '' || word3A == ''
				|| word4A == '' || hintA == '') {
			empty_field2.style.display = "block";
			return false;
		}
		makeCall("GET", "RoomCreation?action=createMinigame&type=" + type
				+ "&affinityWord=" + affinityWord + "&word1A=" + word1A
				+ "&word2A=" + word2A + "&word3A=" + word3A + "&word4A="
				+ word4A + "&hintA=" + hintA + "&idSubject=" + idSubject,
				confirmMinigameCreation);
	}
	function createMinigameQ() {
		var type = "quizgame";
		var questionQ = document.getElementById("questionQ").value;
		var quizWord = document.getElementById("quizWord").value;
		var wrong1Q = document.getElementById("wrong1Q").value;
		var wrong2Q = document.getElementById("wrong2Q").value;
		var select3 = document.getElementById("subjectListCombo3");
		var idSubject = select3.value;
		if (questionQ == '' || quizWord == '' || wrong1Q == '' || wrong2Q == '') {
			empty_field3.style.display = "block";
			return false;
		}
		makeCall("GET", "RoomCreation?action=createMinigame&type=" + type
				+ "&questionQ=" + questionQ + "&quizWord=" + quizWord
				+ "&wrong1Q=" + wrong1Q + "&wrong2Q=" + wrong2Q + "&idSubject="
				+ idSubject, confirmMinigameCreation);
	}
	function confirmMinigameCreation(req) {

		if (req.readyState == 4) {
			var message = req.responseText;
			if (req.status == 200) {
				var response = JSON.parse(req.responseText);
				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href = "/Login.html";
				} else {
					if (response.outcome) {
						alert("minigoco creato!");
						window.location.href = "./HomePage";
					}
				}
			}
		}

	}

	function clear(select) {

		var length = select.options.length;
		for (i = length - 1; i >= 0; i--) {
			select.options[i] = null;
		}
	}
</script>
</head>
<body>
	<div class="introDue">
		<div class="HomeHead">
			<button class="btnLogout" id="logout" type="button" name="esci"
				value="Indietro" onClick="backHome()">Indietro</button>
		</div>
		<div class="boxStHome">
			<h1 id="welcome">Seleziona il tipo di minigioco da creare</h1>
			<button class="btnHome mb-3 " data-bs-toggle="modal"
				data-bs-target="#exampleModal" data-bs-whatever="@mdo"
				onclick="loadSubmitForm()">Gioco dell'impiccato</button>
			<button class="btnHome mb-3" data-bs-toggle="modal"
				data-bs-target="#exampleModal2" data-bs-whatever="@fat"
				onclick="loadSubmitForm()">Gioco delle affinità</button>
			<button class="btnHome mb-3" data-bs-toggle="modal"
				data-bs-target="#exampleModal3" data-bs-whatever="@fat"
				onclick="loadSubmitForm()">Quiz</button>


		</div>
		<div class="modal fade" id="exampleModal" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div
				class="LogSignForm modal-dialog d-flex flex-column align-items-center ">


				<form name="FormHangman" onsubmit="return false;"
					class="quote-container">
					<select id="subjectListCombo1"></select> <input type="text"
						class="form-control text1" placeholder="Parola da indovinare"
						name="HangmanWord" id="HangmanWord" required> <input
						type="text" class="form-control text1" placeholder="Domanda"
						name="questionH" id="questionH" required> <input
						type="text" class="form-control text1" placeholder="Suggerimento"
						name="hintH" id="hintH" required>
					<div class="error" id="empty_field1" style="display: none;">Completa
						tutti i campi</div>
					<input type="submit" class="btnA" value="Crea" id="createMg1">
				</form>



			</div>
		</div>
		<div class="modal fade" id="exampleModal2" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div
				class="LogSignForm modal-dialog d-flex flex-column align-items-center ">


				<form name="FormAffinity" onsubmit="return false;"
					class="quote-container">
					<select id="subjectListCombo2"></select> <input type="text"
						class="form-control text1" placeholder="Parola da indovinare"
						name="AffinityWord" id="AffinityWord" required> <input
						type="text" class="form-control text1" placeholder="Parola affine"
						name="Word1A" id="Word1A" required> <input type="text"
						class="form-control text1" placeholder="Parola affine"
						name="Word2A" id="Word2A" required> <input type="text"
						class="form-control text1" placeholder="Parola affine"
						name="Word3A" id="Word3A" required> <input type="text"
						class="form-control text1" placeholder="Parola affine"
						name="Word4A" id="Word4A" required> <input type="text"
						class="form-control text1" placeholder="Suggerimento" name="hintA"
						id="hintA" required>
					<div class="error" id="empty_field2" style="display: none;">Completa
						tutti i campi</div>
					<input type="submit" class="btnA" value="Crea" id="createMg2">
				</form>



			</div>
		</div>
		<div class="modal fade" id="exampleModal3" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div
				class="LogSignForm modal-dialog d-flex flex-column align-items-center ">


				<form name="FormQuiz" onsubmit="return false;"
					class="quote-container">
					<select id="subjectListCombo3"></select> <input type="text"
						class="form-control text1" placeholder="Domanda" name="questionQ"
						id="questionQ" required> <input type="text"
						class="form-control text1" placeholder="Risposta" name="quizWord"
						id="quizWord" required> <input type="text"
						class="form-control text1" placeholder="Risposta errata"
						name="wrong1Q" id="wrong1Q" required> <input type="text"
						class="form-control text1" placeholder="Risposta errata"
						name="wrong2Q" id="wrong2Q" required>
					<div class="error" id="empty_field3" style="display: none;">Completa
						tutti i campi</div>
					<input type="submit" class="btnA" value="Crea" id="createMg3">
				</form>



			</div>
		</div>
	</div>
</body>
</html>