<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Bean.Room"%>
<%@page import="Bean.Subject"%>
<%@page import="Bean.User"%>
<%@page import="Bean.Ranking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<%  Subject subject = null;
		    
    subject = (Subject) request.getAttribute("subject");
      System.out.println(subject.getMuro1());
      String object1 = subject.getObject1();
      String object2 = subject.getObject2();
      String object3 = subject.getObject3();
      String object4 = subject.getObject4();
      String wall1  = subject.getMuro1();
      String wall2  = subject.getMuro2();
      String wall3  = subject.getMuro3();
      String wall4  = subject.getMuro4();
      session = request.getSession(true); 
      String id_stanza = (String) session.getAttribute("id_stanza");
     
 %>
<meta charset="ISO-8859-1">
<title>EscapeGame</title>

<style>
img {
	display: block;
	margin: 0px auto;
	text-align: center;
 	height: 60%; 
 	width: 60%; 
	
}

#immagine_start{
 cursor: pointer;
position:absolute;
top:40%;
left:47%;
height: 100px;
width: 100px;
z-index: 1;
}

.start{
   
	margin: 0px auto;
	text-align: center;
}

#rules{
position:absolute;
top:40%;
left:47%;
z-index: 1;

}

#inizia{ 
position:absolute;
top:70%;
left:47%;
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
	right: 7%;
	top: 1%;
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
</style>

<script>

var oggetti = ["<%=object1%>", "<%=object2%>", "<%=object3%>", "<%=object4%>"];
var oggettiEMuro = {"<%=object1%>":"<%=wall1%>", "<%=object2%>":"<%=wall2%>", "<%=object3%>":"<%=wall3%>", "<%=object4%>":"<%=wall4%>"};

localStorage.setItem("listaOggetti", JSON.stringify(oggetti));
localStorage.setItem("ordineClick", JSON.stringify(oggettiEMuro));

// var a = JSON.parse(localStorage.getItem("ordineClick"));

console.log(oggetti);




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
}

function startGame1(){ 
    document.getElementById("immagine_start").style.display= ' none ';
    document.getElementById("rules").style.display= ' block ';
    document.getElementById("inizia").style.display = 'block';
    document.getElementById("frecce").style.display = 'none';
	
}


function startGame2(){ 
	
    document.getElementById("immagine_start").style.display= ' none ';
    document.getElementById("rules").style.display= ' none ';
    document.getElementById("inizia").style.display = 'none';
    document.getElementById("frecce").style.display = 'block';
     // MODO PER ELIMINARE EFFETTO GRIGIO
    document.getElementById("alto_dx").style["filter"] = "none";
    document.getElementById("muro").style["-webkit-filter"] = "none";
    
}

	function scorriImmagini(elemento) {

		var muro = document.getElementById("muro");
		var numeroMuro = document.getElementById("muro").src;
		var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1, numeroMuro.indexOf(".")));

		//
		
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
		muro.src = "images/matematica/muro" + numero + ".jpg";
		
		//COntrollare ordine di cliccabilità
		var nomeOggetto = oggetti[0];
		var muroDiRiferimento = "muro" + numero;
		var muroGiusto = oggettiEMuro[oggetti[0]];
		if(muroGiusto != muroDiRiferimento){
			muro.setAttribute("usemap", "");
		}
		
		if(!controllaOggettoEMuro(nomeOggetto, muroDiRiferimento, oggettiEMuro))
			return false;
		//alert(nomeOggetto + " presente nel " + muroDiRiferimento);
		//aggiungo mappa
		
		var mappa ="#point" + numero;
		muro.setAttribute("usemap", mappa);
	}
	
	
	

	//------------------------PERCORSO1 :--------------------------------
	function caricaOggettiCliccabili(id_stanza){
	
		var numeroMuro = document.getElementById("muro").src;
		var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1, numeroMuro.indexOf(".")));
		//document.getElementById("muro").src = "images/muro" + numero + ".jpg";
// 		switch (id_stanza) {
// 		case 1:
			
			var muroDiRiferimento = "muro" + numero;
			var nomeOggetto = oggetti[0];
			if(!controllaOggettoEMuro(nomeOggetto, muroDiRiferimento, oggettiEMuro))
				return false;
			
		//	alert(nomeOggetto + " presente nel " + muroDiRiferimento);
			document.getElementById("area").coords= getObject(oggetti[0]);	
			//conto numero di minigame
			var numeroMinigame;
			<% 
			int numeroMinigame = 1;
			if(!session.getAttribute("prima_volta").equals("SI")){
			  numeroMinigame = Integer.parseInt((String)session.getAttribute("prima_volta"));
			  ++ numeroMinigame;
			}%>
			numeroMinigame = "<%= numeroMinigame%>";
			//
			document.getElementById("area").href="./Minigame"+ "?numeroMinigame=" + numeroMinigame ;
			document.getElementById("muro").setAttribute("usemap", "#point" + numero);
			//break;
			
			
//         case 2:
			
// 			break;
// 		default:
// 			break;
// 		}
	
		
	}
	
	
	
	
	

	function getObject(nome){
		//alert(nome);
		var coordinate;
		if(nome == "asse")
			coordinate = "572,535,679,655";
		if(nome == "fuoco")
			coordinate = "1,2,3,4";
		if(nome == "cane")
			coordinate = "1,2,3,4";
		if(nome == "archibugio")
			coordinate = "1,2,3,4";
		//aggiungi altri oggetti
		return coordinate;
		
	}
	


</script>



</head>
<body onload="carica()">

      <%if(subject != null){ %>
      
      
      <div id="start"> 
      <img  id="immagine_start" src="images/start.png"  onclick= "startGame1()" >
      <p id="rules"><b> Spiegazioni gioco.....</b></p>
      </div>
       
       
       
       <div id="inizia"> 
       <input type="button" value="Inizia il gioco" onclick= "startGame2();caricaOggettiCliccabili(<%=id_stanza%>); ">
       </div>
       
       
	<div id="gioco">

		<img id="muro" src="images/matematica/<%=subject.getMuro1()%>.jpg" >  
		<div id="frecce">
			<img class="freccia" id="freccia_sinistra" src="images/left-arrow.png" onclick="scorriImmagini(this)">
		    <img class="freccia" id="freccia_destra" src="images/right-arrow.png"  onclick="scorriImmagini(this)">
		</div>
		
	</div>


	<div id="alto_dx">
		<table>
			<tr>
				<th class="inventario">INVENTARIO</th>
			</tr>
		</table>
	</div>
	
<%} %>

<!-- CREAZIONE MAPPA VUOTA -->

<map id="point1" name="point1">
<area id="area" shape="rect" coords="" href="">
</map>

<map id="point2" name="point2">
<area id="area" shape="circle" coords="" href="">
</map>







</body>
</html>