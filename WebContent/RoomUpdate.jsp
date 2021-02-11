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
<title>Insert title here</title>
<%

User user = (User) session.getAttribute("user");
int idUser= user.getIduser();
String idRoom = (String)request.getParameter("idRoom");

%>
<script>
function makeCall(method, url, cback) {

	var req = new XMLHttpRequest(); // visible dalla chiusura 
	req.onreadystatechange = function() { //associo un gestore dell'evento di cambio di stato di avanzamento della richiesta(0,1,2,3,4)
		cback(req)// chiusura, callback gestore evento di cambio stato
	};
	req.open(method, url);//una volta preparate la funzione di gestione evento  di risposta apro connessione http; true dafault asinc
	req.send();//invio richiesta http a server

}
function populateTypeCombo(select, type){
	
	    for (var i = 0; i < select.options.length; i++) {
	        if (select.options[i].value == type) {
	            select.options[i].selected = true;
	            return;
	        }
	    }
	
}

function populateMinigameCombo(select, minigameList, idRoomMinigame){
	for(i=0; i < minigameList.length; i++){
		
		        var minigame = minigameList[i];
				var el = document.createElement("option");
				if(minigame.type=="hangmangame"){
					if(minigame.idHangman==idRoomMinigame){
						el.selected=true;
					}
					el.textContent="Parola da indovinare: "+minigame.word;
					el.value= minigame.idHangman;					
					select.appendChild(el);
				}else if(minigame.type=="quizgame"){
					if(minigame.idQuiz==idRoomMinigame){
						el.selected=true;
					}						
					el.textContent="Domanda del quiz: "+minigame.question;
					el.value= minigame.idQuiz;				
					select.appendChild(el);
				}else if(minigame.type=="affinitygame"){
					if(minigame.idAffgame==idRoomMinigame){
						el.selected=true;
					}					
					el.textContent="Parola da indovinare: "+minigame.rightAnswer;
					el.value= minigame.idAffgame;					
					select.appendChild(el);
		        }
	
	}
}


function fillForm(){
	var sendRoom = document.getElementById('roomSubmitOk');		
	sendRoom.addEventListener('click', updateRoom, false);
	
	var idRoom = <%=idRoom %>
	var action = "fillForm";
	makeCall("GET", "RoomUpdate?action="+action+"&idRoom="+idRoom,populateForm);
}
function populateForm(req){
	if (req.readyState == 4) {
		var message = req.responseText;
		if (req.status == 200) {
			var response = JSON.parse(req.responseText);
			if (response.sessionExpired) {
				alert("Sessione scaduta!");
				document.location.href="/Login.html";
			} else {
				if (response.outcome) {
					var subject = response.subject;
					document.getElementById("sub").value= subject;
					var idRoomMinigame = response.idMinigame1;
					var minigameByTypeList = response.minigameByTypeList1;
					var select = document.getElementById("firstGameCombo");
					populateMinigameCombo(select,minigameByTypeList,idRoomMinigame);
					idRoomMinigame = response.idMinigame2;
					minigameByTypeList = response.minigameByTypeList2;
					select = document.getElementById("secondGameCombo");
					populateMinigameCombo(select,minigameByTypeList,idRoomMinigame);
					idRoomMinigame = response.idMinigame3;
					minigameByTypeList = response.minigameByTypeList3;
					select = document.getElementById("thirdGameCombo");
					populateMinigameCombo(select,minigameByTypeList,idRoomMinigame);
					var type1 = response.type1;
					var selectType1 = document.getElementById("firstGameTypeCombo");
					populateTypeCombo(selectType1,type1);
					var type2 = response.type2;
					var selectType2 = document.getElementById("secondGameTypeCombo");
					populateTypeCombo(selectType2,type2);
					var type3 = response.type3;
					var selectType3 = document.getElementById("thirdGameTypeCombo");
					populateTypeCombo(selectType3,type3);
					
					
				}
			}
		}
	}
}

function backHome(){
	window.location.href = "./HomePage";
}
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
	
	var idRoom = <%=idRoom %>
	
	
	makeCall("GET", "RoomUpdate?action=minigameListByType&type="+type+"&num="+num+"&idRoom="+idRoom , populateMinigameCombos);
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
							//}
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
							//}
						}
					}else if(numCombo=="3"){
						var select = document.getElementById("thirdGameCombo");
						for(i=0; i < minigameByTypeList.length; i++){
							var minigame = minigameByTypeList[i];
							//if(minigame.idsubject == document.getElementById("subjectListCombo").value){
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
								//}
							}
							
						}
					
					}

				}
			}
		} else {
			// SE LA RISPOSTA è UN ERRORE(400, 401, 500)
			// TODO: FABIO.
		}
	}
	function clear(select){
		var length = select.options.length;
		for (i = length-1; i >= 0; i--) {
		  select.options[i] = null;
		}}
	
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
	function updateRoom(){
		var select1 = document.getElementById("firstGameCombo");
		var idMinigame1 = select1.value;
		
		var select2  = document.getElementById("secondGameCombo");
		var idMinigame2 = select2.value;	
		var select3 = document.getElementById("thirdGameCombo");
		var idMinigame3 = select3.value;
		var idprof = <%=idUser%>;
		
		var password = document.getElementById("password").value;
		var repetedPassword = document.getElementById("confirm_password").value;
		var error_pwd = document.getElementById("error_pwd");
		
		var idRoom=<%=idRoom %>;
		
		
		if (password != repetedPassword) {
			error_pwd.style.display = "block";
			return false;
		}
		
		  
		
		makeCall("GET", "RoomUpdate?action=updateRoom&idMinigame1="+idMinigame1+"&idMinigame2="+idMinigame2+"&idMinigame3="+idMinigame3+"&idRoom="+idRoom+"&password="+password, confirmRedirect);
		
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
							alert("stanza aggiornata!");
							window.location.href = "./HomePage";
								} 
							}
					}
				}
			
			
			
			
			
		}
</script>
</head>
<body class= "roomCreationUpdate" onLoad="fillForm()">
<div class="HomeHead">
<button class= "btnLogout" id= "logout" type="button" name="esci" value="Indietro" onClick="backHome()" >Indietro</button>
</div>
<div id="RoomCreation">
			<form  name="CreationForm" onsubmit="return false" >

			<p class="introCreatUpd">Scegli i minigiochi da inserire nella stanza</p>
		
		<div id="subj">		
		<Label for="sub">Materia della stanza</Label><br>
		<input class="textSub" type= "text"  name="sub" id="sub" readonly> 
		</div><br>
				
		<Label for="firstGameTypeCombo">Tipo primo minigioco</Label><br>
	<select class="form-select1" id ="firstGameTypeCombo" onChange="minigameTypeSelection(this, 1)">		
		<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
	</select><br>
	<Label for="firstGameCombo">Primo minigioco</Label><br>
	<select class="form-select1" id ="firstGameCombo"></select><br>
	
	<Label for="secondGameTypeCombo">Tipo secondo minigioco</Label><br>
	<select class="form-select1" id ="secondGameTypeCombo" onChange="minigameTypeSelection(this, 2)">		
		<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
	</select><br>
	<Label for="secondGameCombo">Secondo minigioco</Label><br>	
	<select class="form-select1" id ="secondGameCombo"></select><br>
	
	<Label for="thirdGameTypeCombo">Tipo terzo minigioco</Label><br>
	<select class="form-select1" id ="thirdGameTypeCombo" onChange="minigameTypeSelection(this, 3)">    
		<option value="affinitygame">Affinity</option>
		<option value="hangmangame">HangmanGame</option>
		<option value="quizgame">QuizGame</option>
		</select><br>	
		<Label for="thirdGameCombo">Terzo minigioco</Label><br>
	<select class="form-select1" id ="thirdGameCombo"></select><br>
			
				
  <div class="error" id="error_pwd" style="display:none;">La password non corrisponde!</div>
  <div id="passwords">
  <input  id="password" type="password" onkeyup="check();" placeholder="Password" class="form-control1 text1"  />
  <input type="password"  id="confirm_password"  onkeyup="check();" placeholder="Ripeti Password" class="form-control1 text1"   />
  <span id='message'></span>
  </div>
<input type="submit" class="upRoom" name="button" value="Aggiorna stanza" id="roomSubmitOk">
				
				
</form>
</div>
</body>
</html>