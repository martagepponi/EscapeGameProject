<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EscapeGame</title>
<style>
body, html {
  height: 100%;
  margin: 0;
}

#gioco {
}
img {
    max-width: 100%;
    max-height: 100%;
}

.freccia{
cursor:pointer;
position:absolute;
height:100px;
width:100px;
}
#freccia_destra{
    top: 200px;
    right: 20px;
}

#freccia_sinistra{
    top: 200px;
    left: 20px;
}
</style>

<script>
function avanti(){
// 	if(muro==2)
// 	document.getElementById("muro").src="images/muro2.jpg";
var numeroMuro = document.getElementById("muro").src;
var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1 , numeroMuro.indexOf(".")));

	if((numero +1) > 4)
		return;
	
	document.getElementById("muro").src = "images/muro" + (numero + 1) + ".jpg";

}

function indietro(){
	var numeroMuro = document.getElementById("muro").src;
	var numero = parseInt(numeroMuro.substring(numeroMuro.indexOf(".") - 1 , numeroMuro.indexOf(".")));
		if((numero -1) == 0)
			return;
		document.getElementById("muro").src = "images/muro" +(numero -1) + ".jpg";

}
</script>
</head>
<body>


<div id="gioco">
<img id="muro" src="images/muro1.jpg">
<div id="frecce">
<img class="freccia" id="freccia_sinistra" src="images/left-arrow.png" onclick="indietro()">
<img class="freccia" id="freccia_destra"src="images/right-arrow.png" onclick="avanti()">
</div>
</div>
<div id="inventario"></div>


</body>
</html>