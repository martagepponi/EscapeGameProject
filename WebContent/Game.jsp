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
      System.out.println(subject.getMuro1());
      String object1 = subject.getObject1();
      String object2 = subject.getObject2();
      String object3 = subject.getObject3();
      String object4 = subject.getObject4();
      String wall1  = subject.getMuro1();
      String wall2  = subject.getMuro2();
      String wall3  = subject.getMuro3();
      String wall4  = subject.getMuro4();
      String prize = "";
      String id_stanza = (String) session.getAttribute("id_stanza");
      System.out.println("ID STANZAAAAAAA: "+ id_stanza);
     
 %>
<meta charset="ISO-8859-1">
<title>EscapeGame</title>

<style>

body { 
background-color: grey; 
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
#immagine_start {
	cursor: pointer;
	position: absolute;
	top: 40%;
	left: 47%;
	height: 100px;
	width: 100px;
	z-index: 1;
}

.start {
	margin: 0px auto;
	text-align: center;
}

#rules {
	position: absolute;
	top: 40%;
	left: 47%;
	z-index: 1;
}

#inizia {
	position: absolute;
	top: 70%;
	left: 47%;
	z-index: 1;
}

#muro {
	filter: grayscale(100%);
	-webkit-filter: grayscale(100%);
}

.freccia {
	cursor: pointer;
	position: absolute;
	height: 100px;
	width: 100px;
}

#freccia_destra {
	top: 40%;
	right: 150px;
	z-index: 1;
}

#freccia_sinistra {
	top: 40%;
	left: 150px;
	z-index: 1;
}

#alto_dx {
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

th.inventario {
	border-bottom: 3px solid #000;
	padding-bottom: 20px;
	margin-bottom: 20px;
}

#riquadro {
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
String oggetti = "";
if("SI".equals(session.getAttribute("prima_volta"))) {
	oggetti = "[\"" + object1 + "\", \"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
} else {
	int numeroMinigame = (int)session.getAttribute("numeroMinigame");
	if (numeroMinigame == 1) {
		oggetti = "[\"" + object1 + "\", \"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
	} else if (numeroMinigame == 2) {
		oggetti = "[\"" + object2 + "\", \"" + object3 + "\", \"" + object4 +"\"]";
	} else if (numeroMinigame == 3) {
		oggetti = "[\"" + object3 + "\", \"" + object4 +"\"]";
	} else {
		oggetti = "[\"" + object4 +"\"]";
	}
}
%>
	var oggetti = <%= oggetti %>;
	var nonPrimaVolta = false;


<% 
    

    if(session.getAttribute("prima_volta").equals("NO")){
    	prize = (String) session.getAttribute("prize");    %>
   //  alert("non prima volta");
     var oggettiJSON = localStorage.getItem("listaOggetti");
     oggetti = JSON.parse(oggettiJSON);
     oggetti.shift();
     nonPrimaVolta= true;
     var numeroMuroAumentato = parseInt("<%=session.getAttribute("muro")%>");
   //  alert( "numero muro: " + numeroMuroAumentato);
    
     
     
<%}%>

var oggettiEMuro = {"<%=object1%>":"<%=wall1%>", "<%=object2%>":"<%=wall2%>", "<%=object3%>":"<%=wall3%>", "<%=object4%>":"<%=wall4%>"};
//alert("oggetti"+ oggetti);
//localStorage.setItem("ordineClick", JSON.stringify(oggettiEMuro));
 localStorage.setItem("listaOggetti", JSON.stringify(oggetti));
// var a = JSON.parse(localStorage.getItem("ordineClick"));

console.log(oggetti);


//FUNZIONE PER CONTROLLARE CORRISPONDENZA OGGETTO-MURO

function controllaOggettoEMuro(oggetto, muroDaControllare, ordineClick){
	var controllo = false;
	var muro = ordineClick[oggetto];
	if(muro == muroDaControllare){
		controllo = true;
	}
	return controllo;
}

function carica(){

	 document.getElementById("rules").style.display = 'none';
	 document.getElementById("inizia").style.display = 'none';
	 document.getElementById("frecce").style.display = 'none';
	 document.getElementById("musicButton").style.display = 'none';
	if(nonPrimaVolta){
		document.getElementById("frecce").style.display = 'block';
		document.getElementById("musicButton").style.display = 'block';
		 var music = document.getElementById("myAudio"); 
	     music.play();
	}
}

function startGame1(){ 
	
    document.getElementById("immagine_start").style.display = 'none';
    document.getElementById("rules").style.display  = 'block';
    document.getElementById("inizia").style.display = 'block';
    document.getElementById("frecce").style.display = 'none';
    document.getElementById("musicButton").style.display = 'none';
	
}


function startGame2(){ 
	
    document.getElementById("immagine_start").style.display = 'none';
    document.getElementById("rules").style.display  = 'none';
    document.getElementById("inizia").style.display = 'none';
    document.getElementById("frecce").style.display = 'block';
    document.getElementById("musicButton").style.display = 'block';
     // MODO PER ELIMINARE EFFETTO GRIGIO
    document.getElementById("alto_dx").style["filter"]      = "none";
    document.getElementById("muro").style["-webkit-filter"] = "none";
    document.getElementById("riquadro").style["filter"]      = "none";
   // document.getElementById("muro").style["-webkit-filter"] = "none";
    
}


//SCORRIMENTO IMMAGINI MURI 

	function scorriImmagini(elemento) {

		var muro = document.getElementById("muro");
		var numeroMuro = document.getElementById("muro").src;
		var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1, numeroMuro.indexOf(".")));

		
		if(elemento.id === "freccia_sinistra"){
			-- numero;
		}
		
		if(elemento.id === "freccia_destra"){
			++ numero;
		}
		
		if (numero == 0){
			numero = 4;
		}
		
		if (numero == 5){
			numero = 1;
		}
			//scorrimento muri
		muro.src = "images/<%=subject.getName()%>/muro" +numero+ ".jpg";
		
		//COntrollare ordine di cliccabilità
		var nomeOggetto = oggetti[0];
		var muroDiRiferimento = "muro" + numero;
		var muroGiusto = oggettiEMuro[oggetti[0]];
		if(muroGiusto != muroDiRiferimento){   //se muro non è quello giusto inserisco mappa vuota
			muro.setAttribute("usemap", "");
		}
		
		if(!controllaOggettoEMuro(nomeOggetto, muroDiRiferimento, oggettiEMuro)){
			//
			var mappa ="#obj" + numero;
		    muro.setAttribute("usemap", mappa);
			return false;
		}
		//alert(nomeOggetto + " presente nel " + muroDiRiferimento);
		//aggiungo mappa
		
		var mappa ="#point" + numero;
		muro.setAttribute("usemap", mappa);
	}
	
	
	

	//------------------------PERCORSI :--------------------------------
	function caricaOggettiCliccabili(id_stanza){
	
		var numeroMuro = document.getElementById("muro").src;
		var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1, numeroMuro.indexOf(".")));
	
			if(nonPrimaVolta){
			   numero = numeroMuroAumentato;
			}
			var muroDiRiferimento = "muro" + numero;
			
			var nomeOggetto = oggetti[0];
			if(!controllaOggettoEMuro(nomeOggetto, muroDiRiferimento, oggettiEMuro))
				return false;
			
		//	alert(nomeOggetto + " presente nel " + muroDiRiferimento);
		
			document.getElementById("area" + numero).coords= getObject(oggetti[0]);	
		//	alert(document.getElementById("area" + numero).coords);
			//conto numero di minigame
			<% 
			int numeroMinigame = 1;
			if(session.getAttribute("prima_volta").equals("NO")){
			  numeroMinigame = Integer.parseInt((String)session.getAttribute("numeroMinigame"));
			  ++ numeroMinigame;
			  session.setAttribute("numeroMinigame", "" + numeroMinigame);
		
			}else{
				//è la prima volta
				System.out.println("metto muro prima volta");
				session.setAttribute("numeroMinigame", "1");
				session.setAttribute("muro", "1");
			}
			%>
			numeroMinigame = "<%= numeroMinigame%>";
			//
			document.getElementById("area"+ numero).href="./Minigame";
			//alert("#point" + numero);
			
			if(muroDiRiferimento == "<%=subject.getMuro1()%>")
			  document.getElementById("muro").setAttribute("usemap", "#point" + numero);
	
	}
	



//FUNZIONE PER CARICARE IMMAGINI OGGETTI NELL'INFO BOX	
function viewObject(object){

	var cella = document.getElementById("cella");
	removeChild(cella);
	
	
	// MURO1--------------
	if(object.id === "paint"){
	 	var img = document.createElement('img');
	    img.src = "images/paint.png";
	    img.id ="paint";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un vecchio quadro, non c'è niente dietro...");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "mobile"){
	 	var img = document.createElement('img');
	    img.src = "images/mobile.png";
	   img.id ="mobile";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un mobile vintage, non puoi aprire i cassetti...");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "bird"){
	 	var img = document.createElement('img');
	    img.src = "images/bird.png";
	 img.id ="bird";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un piccione, non ti è d'aiuto!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	if(object.id === "plants"){
	 	var img = document.createElement('img');
	    img.src = "images/plants.png";
	   img.id ="plants";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Carine queste piante, peccato che non ci sia niente di utile!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "box"){
	 	var img = document.createElement('img');
	    img.src = "images/box.png";
	   img.id ="box";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Questo baule non si apre...Chissà cosa ci sarà dentro!?");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
// MURO2--------------
	if(object.id === "fiammiferi"){
	 	var img = document.createElement('img');
	    img.src = "images/fiammiferi.png";
	    img.id ="fiammiferi";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Mmmmh... dei fiammiferi! Peccato che il fuoco sia già acceso!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "chair"){
	 	var img = document.createElement('img');
	    img.src = "images/chair.png";
	    img.id ="chair";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Mmmmmh non credo sia il momento giusto per mettersi comodi!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "flower"){
	 	var img = document.createElement('img');
	    img.src = "images/flower.png";
	    img.id ="flower";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che lampadario bizzarro! Chissà come fanno quei fiori a non essere ancora appassiti!?");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "horse"){
	 	var img = document.createElement('img');
	    img.src = "images/horse.png";
	    img.id ="horse";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che bel cavallino...Mmmh non c'è tempo da perdere, al lavoro!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
// MURO3--------------
	
	if(object.id === "pc"){
	 	var img = document.createElement('img');
	    img.src = "images/pc.png";
	    img.id ="pc";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Un laptop...scarico! Che sfortuna!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}


	if(object.id === "dog"){
	 	var img = document.createElement('img');
	    img.src = "images/dog.png";
	    img.id ="dog";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Questo cane non sembra amichevole... Sembra molto geloso delle sue cose...");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "photo"){
	 	var img = document.createElement('img');
	    img.src = "images/photo.png";
	    img.id ="photo";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Che bei quadri... Purtoppo però non c'è nessun indizio!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
// MURO4--------------

	if(object.id === "phone"){
	 	var img = document.createElement('img');
	    img.src = "images/phone.png";
	    img.id ="phone";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("Purtroppo questo telefono non funziona!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
	if(object.id === "lavagna"){
	 	var img = document.createElement('img');
	    img.src = "images/lavagna.png";
	    img.id ="lavagna";
	    cella.appendChild(img);
		var p =document.createElement("p");
		var a = document.createTextNode("A cosa serve una lavagna senza un gesso?!");			
		p.appendChild(a);	
		cella.appendChild(p);
	
	}
	
}







function removeChild(cella){					//funzione che elimina i figli di un elemento
	for(j=0; j<cella.childElementCount; j=j){			//j non ha bisognio di aumentare visto che il numero dei figli diminuirà
		cella.removeChild(cella.childNodes[0]); 	//elimino sempre il primo elemento della pila di figli
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
	
	

	function getObject(nome){
		//alert(nome);
		var coordinate;
		if(nome == "asse")
			coordinate = "572,535,679,655";
		if(nome == "fuoco")
			coordinate = "225,404,284,530";
		if(nome == "cane")
			coordinate = "67,556,156,612";
		if(nome == "archibugio")
			coordinate = "455,338,515,609";
		//aggiungi altri oggetti
		return coordinate;
		
	}
	


</script>



</head>
<body onload="carica()">

	<%if(subject != null){ %>


	<div id="start">
		<img id="immagine_start" src="images/start.png" onclick="startGame1()">
		<p id="rules">
			<b> Benvenuto in questa escape room didattica! Cerca di osservare
				tutta la stanza e gli oggetti che la compongono, se noti che c'è
				qualcosa di strano o se qualcosa attira la tua attenzione clicca
				quell'area. Se la tua intuizione è giusta sarai rimandato ad un
				minigioco, risolvilo e ottiene un oggetto in cambio. Infine usa
				quell'oggetto dove ti sembra più utile e sblocca altri minigiochi.
				Attento però, durante i minigiochi cerca di fare meno errori
				possibili per ottenere un alto punteggio a fine partita! Che il
				gioco abbia inizio...</b>
		</p>
	</div>



	<div id="inizia">
		<input id="bottoneInizia" type="button" value="Inizia il gioco"
			onclick="startGame2();caricaOggettiCliccabili(<%=id_stanza%>); startMusic() ">
	</div>



	<audio id="myAudio">
		<source src="music/findthem.mp3" type="audio/mpeg">

	</audio>


	<div id="musicButton" align="center">
		<button onclick="pauseMusic()" type="button">Pause Audio</button>

		<button onclick="startMusic()" type="button">Play Audio</button>
	</div>

	<div id="gioco">

		<img id="muro"
			src="images/<%=subject.getName()%>/<%=subject.getMuro1()%>.jpg">
		<div id="frecce">
			<img class="freccia" id="freccia_sinistra"
				src="images/left-arrow.png" onclick="scorriImmagini(this)"> <img
				class="freccia" id="freccia_destra" src="images/right-arrow.png"
				onclick="scorriImmagini(this)">
		</div>

	</div>


	<div id="alto_dx">
		<table>
			<tr>
				<th class="inventario">INVENTARIO</th>
			</tr>
			<% if(session.getAttribute("prima_volta").equals("NO")){%>
			<tr>
				<td><img src="images/<%=prize%>.png" class="<%=prize%>"></td>
			</tr>
			<%} %>
		</table>
	</div>

	<%} %>


	<!-- RIQUADRO INFO BOX -->

	<div id="riquadro">
		<table>
			<tr>
				<th class="box">INFO BOX</th>
			</tr>
			<tr>
				<td id="cella"></td>
			</tr>
		</table>
	</div>





	<!-- CREAZIONE MAPPA CON AREA MINIGIOCO SENZA COORDINATE E AREE ALTRI OGGETTI CON COORDINATE -->

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
if(nonPrimaVolta){
	
	startGame2();
	caricaOggettiCliccabili(<%=id_stanza%>);
}
	</script>
</body>
</html>