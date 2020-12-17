<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Bean.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
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

<style>
#demo {
  text-align: center;
  font-size: 60px;
  
}



/* TITOLO   */



#title{
text-align: center;
margin-top:20px;
font-size: 100px;

}

/*  FINE TITOLO   */

/*  INTRODUZIONE   */


 
 


/*  FINE INTRODUZIONE   */



body { 
background-color: #2F4F4F; 
font-family: 'Anonymous Pro', monospace;
 } 
img {
	display: block;
	margin: 0px auto;
	text-align: center;
	height: 60%;
	width: 60%;
}

.arpa {
	height: 100px;
	width: 50px;
}

.fireEx {
	height: 100px;
	width: 50px;
}

.ciotola {
	height: 200px;
	width: 150px;
}

#paint, #mobile, #bird, #phone, #plants, #box, #fiammiferi, #chair,
	#flower, #horse, #pc, #dog, #photo, #lavagna {
	height: 150px;
	width: 150px;
}

/* comandi per scorrimento spiegazioni */
#image_start {
	cursor: pointer;
	position: absolute;
	top: 40%;
	left: 47%;
	height: 100px;
	width: 100px;
	z-index: 1;
}





#start2 {
	position: absolute;
	top: 70%;
	left: 47%;
	z-index: 1;
}

#wall {
	filter: grayscale(100%);
	-webkit-filter: grayscale(100%);
}

.arrow {
	cursor: pointer;
	position: absolute;
	height: 100px;
	width: 100px;
}

#right_arrow {
	top: 40%;
	right: 150px;
	z-index: 1;
}

#left_arrow {
	top: 40%;
	left: 150px;
	z-index: 1;
}

#top_right {
	/*Il posizionamento assoluto esula dal flusso di dati del documento, per questo risulta ultile per piazare div (o altro) in modo dinamico*/
	position: absolute;
	/*Grazie al posizionamento fluid (ovvero con percentuali) il nostro div si adattera' automaticamente allo schermo di tutte le risoluzioni*/
	right: 6%;
	top: 4%;
	border: black solid;
	/*Dimensioni e background*/
	width: 200px;
	height: 200px;
	background-color: #92a8d1;
	filter: grayscale(100%);
	-webkit-filter: grayscale(100%);
}

th.inventory {
	border-bottom: 3px solid #000;
	padding-bottom: 20px;
	margin-bottom: 20px;
}

#panel {
	position: absolute;
	border: black solid;
	left: 2%;
	top: 4%;
	width: 250px;
	height: 300px;
	background-color: #92a8d1;
	filter: grayscale(100%);
	-webkit-filter: grayscale(100%)
}

th.box {
	border-bottom: 3px solid #000;
	padding-bottom: 20px;
	margin-bottom: 20px;
}
</style>

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
   //  alert("non prima volta");
    
     var objectsJSON = localStorage.getItem("objectsList");
     objects = JSON.parse(objectsJSON);
     objects.shift();
     notFirstTime= true;
     var wallNumberIncreased = parseInt("<%=session.getAttribute("wall")%>");
   //  alert( "number wall: " + wallNumberIncreased);
    
     
     
<%}%>

var objectsAndWall = {"<%=object1%>":"<%=wall1%>", "<%=object2%>":"<%=wall2%>", "<%=object3%>":"<%=wall3%>", "<%=object4%>":"<%=wall4%>"};
//alert("objects"+ objects);
//localStorage.setItem("ordineClick", JSON.stringify(objectsAndWall));
 localStorage.setItem("objectsList", JSON.stringify(objects));
// var a = JSON.parse(localStorage.getItem("ordineClick"));

console.log(objects);


//FUNZIONE PER CONTROLLARE CORRISPONDENZA OGGETTO-wall

function checkObjectsAndWall(object, wallToCheck, clickOrder){
	var check = false;
	var wall = clickOrder[object];
	//alert("muro da controllare: " + wallToCheck + " oggetto: " + object + " corrisp. :" + wall);
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
		// document.getElementById("title").style.display = 'none';
	     music.play();
	}
}

function startGame1(){ 
	
//     document.getElementById("image_start").style.display = 'none';
     document.getElementById("panel").style.display = 'block';
	 document.getElementById("top_right").style.display = 'block';
    document.getElementById("game").style.display = 'block';
     document.getElementById("titleDiv").style.display  = 'none';
    document.getElementById("start2").style.display = 'block';
    document.getElementById("arrows").style.display = 'none';
    document.getElementById("musicButton").style.display = 'none';
	
}


function startGame2(){ 
	   
    document.getElementById("start2").style.display = 'none';
    document.getElementById("arrows").style.display = 'block';
    document.getElementById("musicButton").style.display = 'block';
     // MODO PER ELIMINARE EFFETTO GRIGIO
    document.getElementById("top_right").style["filter"] = "none";
    document.getElementById("wall").style["-webkit-filter"] = "none";
    document.getElementById("panel").style["filter"]      = "none";
    
}


//SCORRIMENTO IMMAGINI MURI 

	function imageScroll(element) {

		var wall = document.getElementById("wall");
		var wallNumber = document.getElementById("wall").src;
		var number = parseInt(wallNumber.substring(wallNumber.indexOf(".") - 1, wallNumber.indexOf(".")));

		
		if(element.id === "left_arrow"){
			-- number;
		}
		
		if(element.id === "right_arrow"){
			++ number;
		}
		
		if (number == 0){
			number = 4;
		}
		
		if (number == 5){
			number = 1;
		}
			//scorrimento muri
		wall.src = "images/<%=subject.getName()%>/muro" +number+ ".jpg";
		
		//COntrollare ordine di cliccabilità
		var objectName = objects[0];
		var referenceWall = "muro" + number;
		var correctWall = objectsAndWall[[0]];
		if(correctWall != referenceWall){   //se wall non è quello giusto inserisco mappa vuota
			wall.setAttribute("usemap", "");
		}
		
		if(!checkObjectsAndWall(objectName, referenceWall, objectsAndWall)){
			//
			var map ="#obj" + number;
		    wall.setAttribute("usemap", map);
			return false;
		}
		//alert(objectName + " presente nel " + referenceWall);
		//aggiungo mappa
		
		var map ="#point" + number;
		wall.setAttribute("usemap", map);
	}
	
	
	

	//------------------------PERCORSI :--------------------------------
	function loadClickableObjects(id_room){
		var wallNumber = document.getElementById("wall").src;
		var number = parseInt(wallNumber.substring(wallNumber.indexOf(".") - 1, wallNumber.indexOf(".")));
	
			if(notFirstTime){
			   number = wallNumberIncreased
			}
			var referenceWall = "muro" + number;
			var objectName = objects[0];
			if(!checkObjectsAndWall(objectName, referenceWall, objectsAndWall)){
				return false;
			}
			//alert(objectName + " presente nel " + referenceWall);
		
			document.getElementById("area" + number).coords= getObject(objects[0]);	
			//alert(document.getElementById("area" + number).coords);
			//conto number di minigame
			<% 
			int minigameNumber = 1;
			if(session.getAttribute("first_time").equals("NO")){
			  minigameNumber = Integer.parseInt((String)session.getAttribute("minigameNumber"));
			  ++ minigameNumber;
			  if(minigameNumber == 4){%>
			  alert("funzione timer");
				 timerStart();
				  <%   }
			  session.setAttribute("minigameNumber", "" + minigameNumber);
		
			}else{
				//è la prima volta
				System.out.println("metto wall prima volta");
				session.setAttribute("minigameNumber", "1");
				session.setAttribute("wall", "1");
			}
			%>
			minigameNumber = "<%= minigameNumber%>";
			//
			document.getElementById("area"+ number).href="./Minigame";
			//alert("#point" + number);
			
			if(referenceWall == "<%=subject.getWall1()%>")
			  document.getElementById("wall").setAttribute("usemap", "#point" + number);
	
	}
	
function timerStart(){
	  var now = new Date().getTime();
	// Set the date we're counting down to
	var countDownDate = now + 30000;

	// Update the count down every 1 second
	var x = setInterval(function() {

	  // Get today's date and time
	  var now = new Date().getTime();
	    
	  // Find the distance between now and the count down date
	  var distance = countDownDate - now;
	    
	  // Time calculations for days, hours, minutes and seconds

	  var seconds = Math.floor((distance % (1000 * 60)) / 1000);
	    
	  // Output the result in an element with id="demo"
	  document.getElementById("demo").innerHTML = seconds + "s ";
	    
	  // If the count down is over, write some text 
	  if (distance < 0) {
	    clearInterval(x);
	    document.getElementById("demo").innerHTML = "TEMPO SCADUTO";
	    window.location = '/EscapeGameProject/finalPage.jsp';
	  }
	}, 1000);
}

//FUNZIONE PER CARICARE IMMAGINI objects NELL'INFO BOX	
function viewObject(object){

	var cell = document.getElementById("cell");
	removeChild(cell);
	
	
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







function removeChild(cell){					//funzione che elimina i figli di un elemento
	for(j=0; j<cell.childElementCount; j=j){			//j non ha bisognio di aumentare visto che il number dei figli diminuirà
		cell.removeChild(cell.childNodes[0]); 	//elimino sempre il primo elemento della pila di figli
	}														//il secondo elemento automaticamente diventerà il prime e verrà eliminato al ciclo successivo
}	
	
	function startMusic(){
		var music = document.getElementById("myAudio"); 
		music.play();
		}
	
	function pauseMusic(){
		var music = document.getElementById("myAudio"); 
		music.pause();
	}
	
	

	function getObject(name){
		//alert(name);
		var coordinates;
		if(name == "asse")
			coordinates = "572,535,679,655";
		if(name == "fuoco")
			coordinates = "225,404,284,530";
		if(name == "cane")
			coordinates = "67,556,156,612";
		if(name == "archibugio")
			coordinates = "455,338,515,609";
		//aggiungi altri objects
		return coordinates;
		
	}
	

	

</script>



</head>
<body onload="load()">

	<%if(subject != null){ %>




	<div id="titleDiv">
		<br>
		<%
			if (title.equalsIgnoreCase("Un brutto risveglio")) {
		%>
		<h1 id="title">
			"<%=title %>"
		</h1>



		<div id="introduction" class="wrapper">
			<p id="p">
			Sei un giovane investigatore e sei stato rapito <br>
			prima di poter rivelare il nome di un famigerato assassino.<br>
			ESCI DALLA STANZAE SVELA AL MONDO LA SUA IDENTITA'<br>
		    PRIMA CHE COLPISCA ANCORA!</p>
            <input type="button"  id="avanti" value="Salta" onclick="startGame1()">
		</div>
		

		<%
			}
		%>
	</div>



	<div id="start2">
		<input id="startButton" type="button" value="Inizia"
			onclick="startGame2();loadClickableObjects(<%=id_room%>); startMusic() ">
	</div>



	<audio id="myAudio">
		<source src="music/findthem.mp3" type="audio/mpeg">

	</audio>


	<div id="musicButton" align="center">
		<button onclick="pauseMusic()" type="button">Pause Audio</button>

		<button onclick="startMusic()" type="button">Play Audio</button>
	</div>

	<div id="game">

		<img id="wall"
			src="images/<%=subject.getName()%>/<%=subject.getWall1()%>.jpg">
		<div id="arrows">
		<div id="demo"></div>
			<img class="arrow" id="left_arrow"
				src="images/left-arrow.png" onclick="imageScroll(this)"> <img
				class="arrow" id="right_arrow" src="images/right-arrow.png"
				onclick="imageScroll(this)">
		</div>

	</div>


	<div id="top_right">
		<table>
			<tr>
				<th class="inventory">INVENTARIO</th>
			</tr>
			<% if(session.getAttribute("first_time").equals("NO")){%>
			<tr>
			
				<td><img src="images/<%=prize%>.png" class="<%=prize%>"></td>
			</tr>
			<%} %>
		</table>
	</div>

	<%} %>


	<!-- PANEL INFO BOX -->

	<div id="panel">
		<table>
			<tr>
				<th class="box">INFO BOX</th>
			</tr>
			<tr>
				<td id="cell"></td>
			</tr>
		</table>
	</div>





	<!-- CREAZIONE MAPPA CON AREA MINIGIOCO SENZA COORDINATE E AREE ALTRI objects CON COORDINATE -->

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




	<!-- MAPPE CHE SI ATTIVANO NEI MURI IN CUI NON C'è MINIGIOCO ATTIVO -->




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