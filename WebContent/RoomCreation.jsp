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
	 <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>Room Creation</title>
<%

User user= (User)session.getAttribute("user");
String name= user.getName();
String surname = user.getSurname();
String username = user.getUsername();
int idUser= user.getIduser();

%>
<script>
function backHome(){
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
//check su inserimento password ripetuta
function check() {
	  if (document.getElementById('password').value ==
	    document.getElementById('confirm_password').value) {
	    document.getElementById('message').style.color = 'green';
	    document.getElementById('message').innerHTML = 'matching';
	  } else {
	    document.getElementById('message').style.color = 'red';
	    document.getElementById('message').innerHTML = 'not matching';
	  }
	}
	
	//riprendo materie con chiamata a controller
	function caricaMaterie(){
		var sendRoom = document.getElementById('roomSubmitOk');	
		
		sendRoom.addEventListener('click', createRoom, false);
		
			makeCall("GET", "RoomCreation?action=subjectList", populateSubject);
		
	}
	//popolo combo box con materie già presenti in DB
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
						
						for(i=0; i < subjectList.length; i++){
							//popolo combobox
							    var subject = subjectList[i];
							  
							    var el = document.createElement("option");
							   
							    el.textContent = subject.name+" "+subject.year;
							    el.value = subject.idsubject; 
							    select.appendChild(el);
							    }
						}
	
					}
				}
			} else {
				// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
			}
		}		
	// riprendo lista minigiochi per tipo selezionato e numero della combo in cui inserirli
	function minigameTypeSelection (selectType, num){
		var select;
		var type = selectType.value;
		
		if(num == "1"){
			 select = document.getElementById("firstGameCombo");
		}else if(num == "2"){
			 select = document.getElementById("secondGameCombo");	
		}else if(num == "3"){
			 select = document.getElementById("thirdGameCombo");	
			}
		clear(select);
		
		var select1 = document.getElementById("subjectListCombo");
		var idSubject = select1.value;
		
		
		makeCall("GET", "RoomCreation?action=minigameListByType&type="+type+"&num="+num+"&idSubject="+idSubject, populateMinigameCombos);
	}
	
function populateMinigameCombos(req) {
		
		if (req.readyState == 4) {
			var message = req.responseText;
			if (req.status == 200) {
				var response = JSON.parse(req.responseText);
				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href="/Login.html";
				} else {
					if (response.outcome) {
						var minigameByTypeList = response.minigameByTypeList;
						var numCombo = response.comboBoxSelected;
						
						if(numCombo=="1"){
							
							var select = document.getElementById("firstGameCombo");
							
							for(i=0; i < minigameByTypeList.length; i++){
								
								var minigame = minigameByTypeList[i];
										var el = document.createElement("option");
										if(minigame.type=="hangmangame"){
											el.textContent="Parola da indovinare: "+minigame.word;
											el.value= minigame.idHangman;
											
											select.appendChild(el);
										}else if(minigame.type=="quizgame"){
											el.textContent="Domanda del quiz: "+minigame.question;
											el.value= minigame.idQuiz;
											
											select.appendChild(el);
										}else if(minigame.type=="affinitygame"){
											el.textContent="Parola da indovinare: "+minigame.rightAnswer;
											el.value= minigame.idAffgame;
											
											select.appendChild(el);
									}
								
							}
						}else if(numCombo=="2"){
							var select = document.getElementById("secondGameCombo");
							for(i=0; i < minigameByTypeList.length; i++){
								var minigame = minigameByTypeList[i];
										var el = document.createElement("option");
										if(minigame.type=="hangmangame"){
											el.textContent="Parola da indovinare: "+minigame.word;
											el.value= minigame.idHangman;
											select.appendChild(el);
										}else if(minigame.type=="quizgame"){
											el.textContent="Domanda del quiz: "+minigame.question;
											el.value= minigame.idQuiz;
											select.appendChild(el);
										}else if(minigame.type=="affinitygame"){
											el.textContent="Parola da indovinare: "+minigame.rightAnswer;
											el.value= minigame.idAffgame;
											select.appendChild(el);
									}
								
							}
						}else if(numCombo=="3"){
							var select = document.getElementById("thirdGameCombo");
							for(i=0; i < minigameByTypeList.length; i++){
								var minigame = minigameByTypeList[i];
										var el = document.createElement("option");
										if(minigame.type=="hangmangame"){
											el.textContent="Parola da indovinare: "+minigame.word;
											el.value= minigame.idHangman;
											select.appendChild(el);
										}else if(minigame.type=="quizgame"){
											el.textContent="Domanda del quiz: "+minigame.question;
											el.value= minigame.idQuiz;
											select.appendChild(el);
										}else if(minigame.type=="affinitygame"){
											el.textContent="Parola da indovinare: "+minigame.rightAnswer;
											el.value= minigame.idAffgame;
											select.appendChild(el);
										}
								}
								
							}
						
						}
	
					}
				}
			} else {
			}
		}
		
function createRoom(){
	var select1 = document.getElementById("firstGameCombo");
	var idMinigame1 = select1.value;
	
	var select2  = document.getElementById("secondGameCombo");
	var idMinigame2 = select2.value;	
	var select3 = document.getElementById("thirdGameCombo");
	var idMinigame3 = select3.value;
	var idprof = <%=idUser%>;
	var select4 = document.getElementById("subjectListCombo");
	var idSubject = select4.value;
	var password = document.getElementById("password").value;
	var repetedPassword = document.getElementById("confirm_password").value;
	var error_pwd = document.getElementById("error_pwd");
	var empty_minigames = document.getElementById("empty_minigames");
	
	if (password==""){
		return false;
	}
	
	if (password != repetedPassword) {
		error_pwd.style.display = "block";
		return false;
	}
	
	 if ( idMinigame1 == '' || idMinigame2 == '' || idMinigame3 == '' ||  idSubject == '' ) {
		empty_minigames.style.display = "block";
		return false;
	} 
	
	makeCall("GET", "RoomCreation?action=createRoom&idMinigame1="+idMinigame1+"&idMinigame2="+idMinigame2+"&idMinigame3="+idMinigame3+"&idprof="+idprof+"&idsubject="+idSubject+"&password="+password, confirmRedirect);
	
}
	
	function confirmRedirect(req){
		if (req.readyState == 4) {
			var message = req.responseText;
			if (req.status == 200) {
				var response = JSON.parse(req.responseText);
				if (response.sessionExpired) {
					alert("Sessione scaduta!");
					document.location.href="/Login.html";
				} else {
					if (response.outcome){
						alert("stanza creata!");
						window.location.href = "./HomePage";
							} 
						}
				}
			}
		
	}
	
	
	//clear combo box
	function clear(select){
		var length = select.options.length;
		for (i = length-1; i >= 0; i--) {
		  select.options[i] = null;
		}}
		
</script>
</head>
<body class= "roomCreationUpdate" onload="caricaMaterie()">
<div class="HomeHead">
<button class= "btnLogout" id= "logout" type="button" name="esci" value="Indietro" onClick="backHome()" >Indietro</button>
</div>
<div id="RoomCreation">
			<form  name="CreationForm" onsubmit="return false" >

				<p class="introCreatUpd">Scegli i minigiochi da inserire nella stanza</p>
	<Label for="subjectListCombo">Materia della stanza</Label><br>			
	<select class="form-select1" id ="subjectListCombo" ></select><br>
	
	<Label for="firstGameTypeCombo">Tipo primo minigioco</Label><br>	
	<select class="form-select1" id ="firstGameTypeCombo" onChange="minigameTypeSelection(this, 1)">
		<option value=""> </option>
		<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
	</select><br>
	<Label for="firstGameCombo">Primo minigioco</Label><br>
	<select class="form-select1" id ="firstGameCombo"></select><br>
	
	<Label for="secondGameTypeCombo">Tipo secondo minigioco</Label><br>
	<select class="form-select1" id ="secondGameTypeCombo" onChange="minigameTypeSelection(this, 2)">
		<option value=""> </option>
	 	<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
	</select><br>
	<Label for="secondGameCombo">Secondo minigioco</Label><br>
	<select class="form-select1" id ="secondGameCombo"></select><br>
	
	<Label for="thirdGameTypeCombo">Tipo terzo minigioco</Label><br>
	<select class="form-select1" id ="thirdGameTypeCombo" onChange="minigameTypeSelection(this, 3)">
		<option value=""> </option>
	 	<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
	</select><br>
	<Label for="thirdGameCombo">Terzo minigioco</Label><br>
	<select class="form-select1" id ="thirdGameCombo"></select><br>
			
				
  <div class="error" id="error_pwd" style="display:none;">La password non corrisponde!</div>
  <div class="error" id="empty_minigames" style="display:none;">scegli tutti e 3 i minigiochi!</div>
  <div id="passwords">
  <input  id="password" type="password" onkeyup="check();" placeholder="Password" class="form-control1 text1" required />
  <input type="password"  id="confirm_password"  onkeyup="check();" placeholder="Ripeti Password" class="form-control1 text1" required />
  <span id='message'></span>
  </div>
<input type="submit" class="submitRoom" name="button" value="Crea stanza" id="roomSubmitOk">
				
				
</form>
</div>

</body>
</html>




