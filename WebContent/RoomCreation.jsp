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
<title>Room Creation</title>
<%

User user= (User)session.getAttribute("user");
String name= user.getName();
String surname = user.getSurname();
String username = user.getUsername();
int idUser= user.getIduser();

%>
<script>
var check = function() {
	  if (document.getElementById('password').value ==
	    document.getElementById('confirm_password').value) {
	    document.getElementById('message').style.color = 'green';
	    document.getElementById('message').innerHTML = 'matching';
	  } else {
	    document.getElementById('message').style.color = 'red';
	    document.getElementById('message').innerHTML = 'not matching';
	  }
	}
	function caricaMaterie(){
		
			makeCall("GET", "RoomCreation?action=subjectList", populateSubject);
		
	}
	function populateSubject(req) {
		
		if (req.readyState == 4) {
			var message = req.responseText;
			if (req.status == 200) {
				var response = JSON.parse(req.responseText);
				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href="/Login.html";
				} else {
					if (response.outcome) {
						var subjectList = response.subjectList;
						var select = document.getElementById("subjectListCombo");
						
						for(i=0; i < subjectList.lenght; i++){
							//popolo combobox
							    var subject = subjectList[i];
							    var el = document.createElement("option");
							    el.textContent = subject.name+" "+subject.year;
							    el.value = subject.idSubject;
							    select.appendChild(el);
							    }
						}
	
					}
				}
			} else {
				// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
				// TODO: FABIO.
			}
		}		
	}
	
	function makeCall(method, url, cback) {

		var req = new XMLHttpRequest(); // visible dalla chiusura 
		req.onreadystatechange = function() { //associo un gestore dell'evento di cambio di stato di avanzamento della richiesta(0,1,2,3,4)
			cback(req)// chiusura, callback gestore evento di cambio stato
		};
		req.open(method, url);//una volta preparate la funzione di gestione evento  di risposta apro connessione http; true dafault asinc
		req.send();//invio richiesta http a server

	}
	//clear della combo box
	function clear(select){
		var length = select.options.length;
		for (i = length-1; i >= 0; i--) {
		  select.options[i] = null;
		}
		function minigameTypeSelection()
	}
</script>
</head>
<body onload = "caricaMaterie()">
<div id="RoomCreation">
			<form method="POST" name="CreationForm" onsubmit="" action="SubmitRoom">

				<p>Scegli o crea i minigiochi da inserire nella stanza</p>
				
	<select id ="subjectListCombo"></select>	
	<select id ="firstGameTypeCombo" onChange="minigameTypeSelection(this, 1)">
		<option value=""> </option>
		<option value="affinity">Affinity</option>
		<option value="hangmanGame">HangmanGame</option>
		<option value="quizGame">QuizGame</option>
	</select>
	<select id ="firstGameCombo"></select>
	<select id ="secondGameTypeCombo" onChange="minigameTypeSelection(this, 2)">
		<option value=""> </option>
	 	<option value="affinity">Affinity</option>
		<option value="hangmanGame">HangmanGame</option>
		<option value="quizGame">QuizGame</option>
	</select>
	<select id ="secondGameCombo"></select>
	<select id ="thirdGameTypeCombo" onChange="minigameTypeSelection(this, 3)">
		<option value=""> </option>
	 	<option value="affinity">Affinity</option>
		<option value="hangmanGame">HangmanGame</option>
		<option value="quizGame">QuizGame</option>
		
	</select>
	<select id ="thirdGameCombo"></select>
			
				
			
  <input name="password" id="password" type="password" onkeyup="check();" placeholder="Password" required />
  <input type="password" name="confirm_password" id="confirm_password"  onkeyup="check();" placeholder="Ripeti Password" required />
   
  <span id='message'></span>

				<input type="submit" class="submitRoom" name="button" value="submitRoom">
				
				
			</form>
</div>
</body>
</html>




