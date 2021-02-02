<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@page import="Bean.Room"%>
<%@page import="Bean.User"%>
<%@page import="Bean.Ranking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.Subject"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/Style.css">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<meta charset="ISO-8859-1">
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>Subject Insertion</title>
<%
List<Subject> subjectList = new ArrayList<>();
subjectList = (List<Subject>) session.getAttribute("SubjectList");
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
function initializeListener(){
	var createSubject = document.getElementById('createSubject');	
	
	createSubject.addEventListener('click', createSubjects, false);
}
function createSubjects(){
	var name = document.getElementById("subjectName").value;
	var year = document.getElementById("subjectYear").value;
	alert("name: "+name+" year: "+year);
	if ( name == '' || year == ''  ) {
		empty_field.style.display = "block";
		return false;
	} 
	if(isNaN(year)){
		year_NaN.style.display = "block";
		return false;
	}
	makeCall("GET", "RoomCreation?action=addSubject&subjectName="+name+"&subjectYear="+year, confirmInsertion);
	
}
function confirmInsertion(req){
	if (req.readyState == 4) {
		var message = req.responseText;
		if (req.status == 200) {
			var response = JSON.parse(req.responseText);
			if (response.sessionExpired) {
				alert("Sessione scaduta!");
				document.location.href="/Login.html";
			} else {
				if (response.outcome){
					alert("materia aggiunta!");
					window.location.href = "./HomePage";
						} 
					}
			}
		}
	
	
	
	
	
}

</script>
</head>
<body >
<div class="introDue" >
<div class="HomeHead">
<button class= "btnLogout" id= "logout" type="button" name="esci" value="Indietro" onClick="backHome()" >Indietro</button>
</div>
<div class="dbSubject">
<%if(subjectList.isEmpty()){%>
		<p> Non sono presenti materie!</p>
		<%
		}else{
		%>
		<p><b> Le materie già presenti sono: </b></p>
		<table>
		<tr><td>Nome</td><td>Anno</td></tr>
		<%
		for (Subject subject : subjectList) {
		%>
		<tr><td>-  <%=subject.getName() %></td><td><%=subject.getYear() %></td></tr>
		
		
		<%
			}
		}
		%>
</table>
<div class="AddSubject">

<button class="btnHome mb-3 " data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" onclick="initializeListener()">Aggiungi una nuova materia</button>
		

</div>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="LogSignForm modal-dialog d-flex flex-column align-items-center ">
    
    
      <form  name="FormSubjectAdd" onsubmit="return false;"class= "quote-container" >
			<img src="images/logo.png">

			<input type= "text" class="form-control text1" placeholder="Nome Materia" name="subjectName" id="subjectName" required>
			<input type="text" class="form-control text1" placeholder="Anno della Materia" name="subjectYear" id="subjectYear" required>
			<div class="error" id="empty_field" style="display:none;">Completa tutti i campi</div>
			<div class="error" id="year_NaN" style="display:none;">Il campo anno deve essere un numero (1, 2, 3)</div>
			<input type="submit" class="btnA" value="Aggiungi" id="createSubject"> 	
			
		</form>
       
   
    </div>
  </div>

</div>
</div>
</body>
</html>