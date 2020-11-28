<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.*"%>
<%
	Affinitygame minigame = (Affinitygame)session.getAttribute("Minigame");
    String word1 = minigame.getWord1();
    String word2 = minigame.getWord2();
    String word3 = minigame.getWord3();
    String word4 = minigame.getWord4();
    
    String hint = minigame.getHint();

     
     if(session.getAttribute("tentativi") == null){
    	 int tentativi = 3;
        session.setAttribute("tentativi", "" + tentativi);
     }else{
    //	 int tentativi=  Integer.parseInt((String)session.getAttribute("tentativi"));
     }
    	
     
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Affinity</title>

<style>
/* /* Google Fonts */
* /
	/* @import url(https://fonts.googleapis.com/css?family=Anonymous+Pro); */
	/* Global */ 
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
	overflow: hidden;
}

/* Animation */
.anim-typewriter {
	animation: typewriter 4s steps(44) 1s 1 normal both, blinkTextCursor
		500ms steps(44) infinite normal;
}

@
keyframes typewriter {
	from {width: 0;
}

to {
	width: 24em;
}

}
@
keyframes blinkTextCursor {
	from {border-right-color: rgba(255, 255, 255, .75);
}

to {
	border-right-color: transparent;
}
}
</style>

<script>



function Word(wordToCheck){
	if(wordToCheck==""){
		return false;
	}else{
	makeCall("GET", "AffinityGame?word=" + wordToCheck.value, wordResponse);
	}
}

function wordResponse(){
	 if (req.readyState == 4 && req.status == 200) {
		 var esitoParolaInserita = JSON.parse(x.responseText);
		 if(esitoParolaInserita[0].response == "OK"){
			 document.getElementById("arpa").style.display="block";
			 document.getElementById("divMain").style.display="none";
			 document.getElementById("div2").style.display="none";
		 }else if(esitoParolaInserita[0].response == "KO"){
			 alert("numero tentativi rimasti" + tentativi);
			 
		 }else if(esitoParolaInserita[0].response == "P"){
			 alert("Hai perso");
		 }
		 
	 }
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
</script>

</head>



<body>
	<div id="divMain" class="line-1 anim-typewriter">
		<p><%=word1 %>,
			<%=word2%>,
			<%=word3%>,
			<%=word4%></p>
	</div>

	<div id="div2" class="correctWord">
		<input type="text" placeholder="Inserisci parola" value="">
		<input type="button" onclick="Word(this)">

	</div>



	<div id="arpa" align="center" style="display: none;">

		<img src="images/<%=minigame.getPrize()%>.png" height="500"
			width="300">

		<p align="center">Hai trovato un oggetto. Troverai questo oggetto
			nell'inventario, ritorna alla stanza e clicca nel punto in cui
			l'oggetto trovato può risultarti utile!</p>

		<a href="./Game"> <input type="button" value="Torna alla stanza">
		</a>


	</div>

	<div id="tentativi" align="center">
	
<%-- <%int tentativi=  Integer.parseInt((String)session.getAttribute("tentativi")); %> --%>

<%-- 		<p>NUMERO TENTATIVI: <%=tentativi %></p> --%>
	</div>



</body>
</html>