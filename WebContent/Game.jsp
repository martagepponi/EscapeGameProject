<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Bean.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
		<link rel="stylesheet" href="css/Style.css">
	 	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<%  
	Subject subject = null;
		    
    subject = (Subject) session.getAttribute("subject");
      System.out.println(subject.getWall1());
      String object1 = subject.getObject1();
      String object2 = subject.getObject2();
      String object3 = subject.getObject3();
      String object4 = subject.getObject4();
      String wall1  = subject.getWall1();
      String wall2  = subject.getWall2();
      String wall3  = subject.getWall3();
      String wall4  = subject.getWall4();
      String prize = "";
      String id_room = (String) session.getAttribute("id_room");
      String title = (String) session.getAttribute("title");
     System.out.println("titolo"+ title);
 %>
<meta charset="ISO-8859-1">
<title>EscapeGame</title>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

<script>
<%
String objects = "";
if("YES".equals(session.getAttribute("first_time"))) {
	objects = "[\"" + object1 + "\", \"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
} else {
	int minigameNumber = Integer.parseInt((String)session.getAttribute("minigameNumber"));
	if (minigameNumber == 1) {
		objects = "[\"" + object1 + "\", \"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
	} else if (minigameNumber == 2) {
		objects = "[\"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
	} else if (minigameNumber == 3) {
		objects = "[\"" + object3 + "\", \"" + object4 +"\"]";
	} else {
		objects = "[\"" + object4 +"\"]";
	}
}
%>
	var objects = <%= objects %>;
	var notFirstTime = false;
	localStorage.setItem("objectsList", JSON.stringify(objects));

<% 
    

    if(session.getAttribute("first_time").equals("NO")){
    	prize = (String) session.getAttribute("prize");    %>
     var objectsJSON = localStorage.getItem("objectsList");
     objects = JSON.parse(objectsJSON);
     objects.shift();
     notFirstTime= true;
     var wallNumberIncreased = parseInt("<%=session.getAttribute("wall")%>");
<%}%>


var objectsAndWall = {"<%=object1%>":"<%=wall1%>", "<%=object2%>":"<%=wall2%>", "<%=object3%>":"<%=wall3%>", "<%=object4%>":"<%=wall4%>"};
 localStorage.setItem("objectsList", JSON.stringify(objects));
console.log(objects);


//FUNZIONE PER CONTROLLARE CORRISPONDENZA OGGETTO-MURO

function checkObjectsAndWall(object, wallToCheck, clickOrder){ //es: asta, muro1, ["asta":"muro1","fuoco":"muro2"...]
	var check = false;
	var wall = clickOrder[object];
	if(wall == wallToCheck){
		check = true;
	}
	return check;
}



function load(){
     document.getElementById("panel").style.display = 'none';
     document.getElementById("top_right").style.display = 'none';
     document.getElementById("game").style.display = 'none';
	 document.getElementById("start2").style.display = 'none';
	 document.getElementById("arrows").style.display = 'none';
	 document.getElementById("musicButton").style.display = 'none';
	if(notFirstTime){
	    document.getElementById("titleDiv").style.display  = 'none';
		document.getElementById("arrows").style.display = 'block';
		document.getElementById("musicButton").style.display = 'block';
		 document.getElementById("game").style.display = 'block';
	     document.getElementById("panel").style.display = 'block';
	     document.getElementById("top_right").style.display = 'block';
		 var music = document.getElementById("myAudio"); 
	     music.play();
	}
}

function startGame1(){ //click "salta"
	
     document.getElementById("panel").style.display = 'block';
	 document.getElementById("top_right").style.display = 'block';
    document.getElementById("game").style.display = 'block';
     document.getElementById("titleDiv").style.display  = 'none';
    document.getElementById("start2").style.display = 'block';
    document.getElementById("arrows").style.display = 'none';
    document.getElementById("musicButton").style.display = 'none';
}


function startGame2(){ //click "inizia"
	   
    document.getElementById("start2").style.display = 'none';
    document.getElementById("arrows").style.display = 'block';
    document.getElementById("musicButton").style.display = 'block';
     // nascondo effetto grigio
    document.getElementById("top_right").style["filter"] = "none";
    document.getElementById("wall").style["-webkit-filter"] = "none";
    document.getElementById("panel").style["filter"]      = "none";
    
}


    //FUNZIONE PER GESTIRE SCORRIMENTO IMMAGINI MURI 

	function imageScroll(element) {

		var wall = document.getElementById("wall");
		var wallNumber = document.getElementById("wall").src;
		var number = parseInt(wallNumber.substring(wallNumber.indexOf(".") - 1, wallNumber.indexOf("."))); 
		//es: wallNumber= images/matematica/muro1.jpg
		// images/matematica/muro|1|.jpg-----> number=1

		if(element.id === "left_arrow"){
			-- number;
		}
		
		if(element.id === "right_arrow"){
			++ number;
		}
		
		if (number == 0){ //se sono nel muro1 e clicco freccia sinistra devo tornare a muro4
			number = 4;
		}
		
		if (number == 5){ //se sono nel muro4 e clicco freccia destra devo tornare a muro1
			number = 1;
		}
		//aggiorno immagine muro
		wall.src = "images/<%=subject.getName()%>/muro" +number+ ".jpg";
		
		//controllo ordine di cliccabilità
		
		//riprendo primo oggetto presente nell'array
		var objectName = objects[0];
		var referenceWall = "muro" + number;
		var correctWall = objectsAndWall[[0]];
		//alert("correctWall"+ correctWall);
		if(correctWall != referenceWall){   //se wall non è quello giusto inserisco mappa vuota
			wall.setAttribute("usemap", ""); //riempirò la mappa con le giuste coord solo quando wall sarà quello "giusto"
		}
		
		//se muro in cui mi trovo NON è il primo muro presente nella tabella associativa
		if(!checkObjectsAndWall(objectName, referenceWall, objectsAndWall)){
			//attivo mappe degli oggetti cliccabili ma che non rimandano ad un minigame
			var map ="#obj" + number;
		    wall.setAttribute("usemap", map);
			return false;
		}
		//se muro in cui mi trovo è il primo muro presente nella tabella associativa
		//attivo la mappa che rimanda al minigioco
		var map ="#point" + number;
		wall.setAttribute("usemap", map);
	}

	

	//GESTIONE OGGETTI CLICCABILI
	
	function loadClickableObjects(id_room){
		//riprendo percorso dell' elemento html wall 
		var wallNumber = document.getElementById("wall").src;
		var number = parseInt(wallNumber.substring(wallNumber.indexOf(".") - 1, wallNumber.indexOf(".")));
			if(notFirstTime){
			   number = wallNumberIncreased;//wallNumberIncreased= muro ripreso dalla sessione, passato da Game.java
			}
			var referenceWall = "muro" + number;//muro che utente sta visualizzando
			var objectName = objects[0];
			
			if(!checkObjectsAndWall(objectName, referenceWall, objectsAndWall)){//se muro visualizzato!= primo muro nella tabella
				return false;
			}
			//richiamo funzione getObject() contenente mappe degli oggetti che riportano ad un minigame
			document.getElementById("area" + number).coords= getObject(objects[0]);	
			
			
			
			
//Gestione numero minigame		
<%int minigameNumber = 1;

if (session.getAttribute("first_time").equals("NO")) {
	minigameNumber = Integer.parseInt((String) session.getAttribute("minigameNumber"));
	++minigameNumber;
	if (minigameNumber == 4) {%>
			  alert("E' partito un timer!");
				 timerStart();
				  <%}
	session.setAttribute("minigameNumber", "" + minigameNumber);

} else {//è la prima volta
	
	session.setAttribute("minigameNumber", "1");
	session.setAttribute("wall", "1");
}%>
			minigameNumber = "<%=minigameNumber%>";
			//aggiungo alla mappa riferimento a Minigame.java
			document.getElementById("area"+ number).href="./Minigame";
			
			if(referenceWall == "<%=subject.getWall1()%>" )  
			{//attivo nel muro1 oggetti cliccabili 
			 document.getElementById("wall").setAttribute("usemap", "#point" + number);
			} 
			//al ritorno da un minigame attivo mappe cliccabili sul muro1 (primo muro ad essere visualizzato)
			else{
				var number = 1;
				wall.src = "images/<%=subject.getName()%>/muro"+number+".jpg";
				document.getElementById("wall").setAttribute("usemap", "#point" + number);
			}
	        }

	
function timerStart(){
	
	 document.getElementById("time").innerHTML = "Occhio al tempo!";
	 //now= ora attuale
	  var now = new Date().getTime();
	// countDownDate= ora + 30 s
	var countDownDate = now + 30000;
	// Update sul count down ogni secondo
	var x = setInterval(function() {
	  // data e ora di oggi
	  var now = new Date().getTime(); 
	  // distanza tra now e count down
	  var distance = countDownDate - now;
	  // tempo calcolato in giorni, ore, minuti e secondi
	  var seconds = Math.floor((distance % (1000 * 60)) / 1000);	    
	  //metto il risultato nell'elemento di id=demo	 
	  document.getElementById("demo").innerHTML = seconds + "s ";  
	  // allo scadere del count down...
	  if (distance < 0) {
		  document.getElementById("time").innerHTML = "";
		  document.getElementById("demo").innerHTML = "";
	    clearInterval(x);	    
	    document.getElementById("timeEx").innerHTML = "TEMPO SCADUTO";
	    setTimeout(function(){window.location.href = "./Minigame";}, 2000); 
	  }
	}, 1000);
}

//FUNZIONE PER CARICARE LE IMMAGINI DEGLI OGGETTI CLICCATI DALL'UTENTE NELL'INFO BOX	
function viewObject(object){

	var cell = document.getElementById("cell");
	removeChild(cell); //richiamo la funzione remoChild(cell): rimuove la l'imm attualmente "appesa" nell'inventario 
	
	
	// wall1--------------
	if(object.id === "paint"){
	 	var img = document.createElement('img');
	    img.src = "images/paint.png";
	    img.id ="paint";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un vecchio quadro, non c'è niente dietro...");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "mobile"){
	 	var img = document.createElement('img');
	    img.src = "images/mobile.png";
	   img.id ="mobile";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un mobile vintage, non puoi aprire i cassetti...");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "bird"){
	 	var img = document.createElement('img');
	    img.src = "images/bird.png";
	    img.id ="bird";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un piccione, non ti è d'aiuto!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	if(object.id === "plants"){
	 	var img = document.createElement('img');
	    img.src = "images/plants.png";
	   img.id ="plants";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Carine queste piante, peccato che non ci sia niente di utile!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "box"){
	 	var img = document.createElement('img');
	    img.src = "images/box.png";
	    img.id ="box";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Questo baule non si apre...Chissà cosa ci sarà dentro!?");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
// wall2--------------
	if(object.id === "fiammiferi"){
	 	var img = document.createElement('img');
	    img.src = "images/fiammiferi.png";
	    img.id ="fiammiferi";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Mmmmh... dei fiammiferi! Peccato che il fuoco sia già acceso!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "chair"){
	 	var img = document.createElement('img');
	    img.src = "images/chair.png";
	    img.id ="chair";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Mmmmmh non credo sia il momento giusto per mettersi comodi!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "flower"){
	 	var img = document.createElement('img');
	    img.src = "images/flower.png";
	    img.id ="flower";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che lampadario bizzarro! Chissà come fanno quei fiori a non essere ancora appassiti!?");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "horse"){
	 	var img = document.createElement('img');
	    img.src = "images/horse.png";
	    img.id ="horse";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che bel cavallino...Mmmh non c'è tempo da perdere, al lavoro!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
// wall3--------------
	
	if(object.id === "pc"){
	 	var img = document.createElement('img');
	    img.src = "images/pc.png";
	    img.id ="pc";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un laptop...scarico! Che sfortuna!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}


	if(object.id === "dog"){
	 	var img = document.createElement('img');
	    img.src = "images/dog.png";
	    img.id ="dog";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Questo cane non sembra amichevole... Sembra molto geloso delle sue cose...");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "photo"){
	 	var img = document.createElement('img');
	    img.src = "images/photo.png";
	    img.id ="photo";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che bei quadri... Purtoppo però non c'è nessun indizio!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
// wall4--------------

	if(object.id === "phone"){
	 	var img = document.createElement('img');
	    img.src = "images/phone.png";
	    img.id ="phone";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Purtroppo questo telefono non funziona!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
	if(object.id === "lavagna"){
	 	var img = document.createElement('img');
	    img.src = "images/lavagna.png";
	    img.id ="lavagna";
	    cell.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("A cosa serve una lavagna senza un gesso?!");			
		p.appendChild(a);	
		cell.appendChild(p);
	
	}
	
}



//FUNZIONE CHE ELIMINA I FIGLI DI UN ELEMENTO
function removeChild(cell){				
	for(j=0; j<cell.childElementCount; j=j){//j non ha bisognio di aumentare visto che il number dei figli diminuirà
		cell.removeChild(cell.childNodes[0]); //elimino sempre il primo elemento della pila di figli
	}										  //il secondo elemento automaticamente diventerà il primo e verrà eliminato al ciclo successivo
}	
	
	
	
	function startMusic(){ //attiva al click su "INIZIA" e al click sull'icona dell'altoparlante
		var music = document.getElementById("myAudio"); 
		music.play();
		}
	
	function pauseMusic(){//attiva al click sull'icona dell'altoparlante sbarrato
		var music = document.getElementById("myAudio"); 
		music.pause();
	}
	
	

	function getObject(name){ // funzione richiamata da loadClickableObjects
		var coordinates;
		if(name == "asse")
			coordinates = "572,535,679,655";
		if(name == "fuoco")
			coordinates = "225,404,284,530";
		if(name == "cane")
			coordinates = "67,556,156,612";
		if(name == "archibugio")
			coordinates = "455,338,515,609";
		return coordinates;
		
	}
	
</script>

<!-- PAGINA -->

</head>

<body class= "game" onload="load(); ">

	<%if(subject != null){ %>

	<div id="titleDiv">
		<div id="introduction" class="d-flex flex-column align-items-center">
			<%
			if (title.equalsIgnoreCase("Un brutto risveglio")) {
		%>
		<h1 id="title">
			"<%=title %>"
		</h1>
			<p>
			Sei un giovane investigatore e sei stato rapito <br>
			prima di poter rivelare il nome di un famigerato assassino.<br>
			ESCI DALLA STANZA E SVELA AL MONDO LA SUA IDENTITA'<br>
		    PRIMA CHE COLPISCA ANCORA!</p>
		    
		    <%
			}
		%>
			<%
			if (title.equalsIgnoreCase("Il codice da Vinci")) {
		%>
			<h1 id="title">
			"<%=title %>"
		   </h1>
			<p>
			Sei un giovane ricercatore... Pochi passi ti separano dallo
			 svelare il segreto di Leonardo da Vinci!</p>
		
		       <%
			}
		%>
            <button class="btn-game" id="avanti" value="Salta" onclick="startGame1()">Salta</button>
		</div>

		
	</div>


	<div id="start2">
		<input id="startButton" class="btn-game" type="button" value="Inizia"
			onclick="startGame2();loadClickableObjects(<%=id_room%>); startMusic() ">
	</div>


	<audio id="myAudio">
		<source src="music/findthem.mp3" type="audio/mpeg">
	</audio>


	
	<div id="game">
<div class="wall_cont d-flex flex-column align-items-center pt-2">
		<img id="wall"
			src="images/<%=subject.getName()%>/<%=subject.getWall1()%>.jpg">
			</div>
		<div id="arrows">		
			<img class="arrow" id="left_arrow"
				src="images/leftArrow.png" onclick="imageScroll(this)"> <img
				class="arrow" id="right_arrow" src="images/rightArrow.png"
				onclick="imageScroll(this)">
		</div>
		
		<div id= counter>
		<p id = timeEx></p>
		<p id = "time"></p>
		<p id = "demo"></p>
		</div>

	</div>
	
	<div id="musicButton" align="center">
		<img src="images/audioOff.png" onclick="pauseMusic()" >

		<img src="images/audioOn.png" onclick="startMusic()" >
	</div>
	


	<div id="top_right">
		<p>INVENTARIO</p>
		<!--  <table>
			<tr>
				<th class="inventory"></th>
			</tr>
			<% if(session.getAttribute("first_time").equals("NO")){%>
			<tr>
			
				<td><img id ="inventoryImage" src="images/<%=prize%>.png" class="<%=prize%>"></td>
			</tr>
			<%} %>
		</table>-->
		<div class="inventory">
		<% if(session.getAttribute("first_time").equals("NO")){%>
		<img id ="inventoryImage" src="images/<%=prize%>.png" class="<%=prize%>">
		<%} %>
		</div>
	</div>

	<%} %>


	<!-- PANEL INFO BOX -->

	<div id="panel">
	<p>NOTE</p>
		
				<div class="box"></div>
			
				<div id="cell"></div>
			
	</div>




    <!-- MAPPE OGGETTI CLICCABILI -->
	<!-- CREAZIONE MAPPA CON AREA MINIGIOCO SENZA COORDINATE E AREE DEGLI ALTRO OGGETTI CLICCABILI CON COORDINATE -->

	<map id="point1" name="point1">
		<area id="area1" shape="rect" coords="" href="">
		<area id="paint" shape="rect" coords="553,69,702,184"
			onclick="viewObject(this)">
		<area id="mobile" shape="rect" coords="111,156,331,470"
			onclick="viewObject(this)">
		<area id="plants" shape="rect" coords="114,124,331,158"
			onclick="viewObject(this)">
		<area id="box" shape="rect" coords="353,477,544,570"
			onclick="viewObject(this)">
		<area id="bird" shape="poly"
			coords="246,51,259,57,238,120,172,121,183,84"
			onclick="viewObject(this)">
	</map>

	<map id="point2" name="point2">
		<area id="area2" shape="rect" coords="" href="">
		<area id="fiammiferi" shape="poly"
			coords="290,555,325,541,340,551,309,571" onclick="viewObject(this)">
		<area id="chair" shape="poly"
			coords="101,405,146,410,204,409,208,597,159,576,124,606,106,555,86,582,79,485,106,419"
			onclick="viewObject(this)">
		<area id="flower" shape="rect" coords="349,13,415,247"
			onclick="viewObject(this)">
		<area id="horse" shape="poly"
			coords="436,394,474,347,503,407,550,460,626,516,403,507,478,457"
			onclick="viewObject(this)">
	</map>

	<map id="point3" name="point3">
		<area id="area3" shape="rect" coords="" href="">
		<area id="pc" shape="rect" coords="522,366,624,457"
			onclick="viewObject(this)">
		<area id="dog" shape="poly"
			coords="169,370,234,365,259,442,337,460,396,606,338,540,343,600,338,540,343,600,291,508,255,531,252,613,225,523,204,604,181,428"
			onclick="viewObject(this)">
		<area id="photo" shape="rect" coords="123,124,343,191"
			onclick="viewObject(this)">
	</map>

	<map id="point4" name="point4">
		<area id="area4" shape="rect" coords="" href="">
		<area id="phone" shape="rect" coords="183,218,272,283"
			onclick="viewObject(this)">
		<area id="lavagna" shape="rect" coords="559,268,691,533"
			onclick="viewObject(this)">
	</map>




	<!-- MAPPE DEGLI OGGETTI CHE NON ATTIVANO MINIGAME-->


	<map id="obj1" name="obj1">
		<area id="paint" shape="rect" coords="553,69,702,184"
			onclick="viewObject(this)">
		<area id="mobile" shape="rect" coords="111,156,331,470"
			onclick="viewObject(this)">
		<area id="plants" shape="rect" coords="114,124,331,158"
			onclick="viewObject(this)">
		<area id="box" shape="rect" coords="353,477,544,570"
			onclick="viewObject(this)">
		<area id="bird" shape="poly"
			coords="246,51,259,57,238,120,172,121,183,84"
			onclick="viewObject(this)">
	</map>


	<map id="obj2" name="obj2">
		<area id="fiammiferi" shape="poly"
			coords="290,555,325,541,340,551,309,571" onclick="viewObject(this)">
		<area id="chair" shape="poly"
			coords="101,405,146,410,204,409,208,597,159,576,124,606,106,555,86,582,79,485,106,419"
			onclick="viewObject(this)">
		<area id="flower" shape="rect" coords="349,13,415,247"
			onclick="viewObject(this)">
		<area id="horse" shape="poly"
			coords="436,394,474,347,503,407,550,460,626,516,403,507,478,457"
			onclick="viewObject(this)">
	</map>


	<map id="obj3" name="obj3">
		<area id="pc" shape="rect" coords="522,366,624,457"
			onclick="viewObject(this)">
		<area id="dog" shape="poly"
			coords="169,370,234,365,259,442,337,460,396,606,338,540,343,600,338,540,343,600,291,508,255,531,252,613,225,523,204,604,181,428"
			onclick="viewObject(this)">
		<area id="photo" shape="rect" coords="123,124,343,191"
			onclick="viewObject(this)">
	</map>


	<map id="obj4" name="obj4">
		<area id="phone" shape="rect" coords="183,218,272,283"
			onclick="viewObject(this)">
		<area id="lavagna" shape="rect" coords="559,268,691,533"
			onclick="viewObject(this)">
	</map>

	<script>
if(notFirstTime){
	
	startGame2();
	loadClickableObjects(<%=id_room%>);
}
	</script>
</body>
</html>