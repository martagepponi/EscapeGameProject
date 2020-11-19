var serverPath = 'http://localhost:8080/EscapeGameProject';

//REGISTRAZIONE STUDENTE
function loadStudent(){ //altrimenti da errore visto che listener non legge nulla se clicchiamo il link del docente
	var registraStudente = document.getElementById("registra1");	
	registraStudente.addEventListener('click', registrazioneStudente, false);

}


function registrazioneStudente() {

	var name = document.FormStudente.name.value;
	var surname = document.FormStudente.surname.value;
	var username = document.FormStudente.username.value;
	var password = document.FormStudente.pwd1.value;
	var passwordRipetuta = document.FormStudente.pwd2.value;
	var type = "studente";



	if (password != passwordRipetuta) {
		error_pwd.style.display = "block";
		return false;
	}
	
	 if ( name == '' || surname == '' || username == '' ||  password == '' || passwordRipetuta == '') {
		return false;
	} 

	x = new XMLHttpRequest();
	x.onreadystatechange = getRegistrazioneStudente;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'name=' + name + '&surname=' + surname  +  '&username=' + username + '&password=' + password + '&type=' + type;
	x.send(params);
}

function getRegistrazioneStudente() {
	if (x.readyState == 4 && x.status == 200) {
		console.log(x.responseText);
		var response = JSON.parse(x.responseText);
		if (response[0].error == 1) {
			alert("Utente presente nel database!");
		} else {
			alert("Studente inserito");
			window.setTimeout(window.location.replace(serverPath + "/Login.html"), 2000);
		}
	}
}

//REGISTRAZIONE DOCENTE
var inserisciCodice = document.getElementById("ok");
inserisciCodice.addEventListener('click', inizializzazione, false);

//1. INSERIMENTO CODICE


function inizializzazione() {
	
	var code = document.Form.code.value;
	
	if( code == null || code ==''){
		return false;
	} 
	
	
x = new XMLHttpRequest();
	x.onreadystatechange = VerificaCodice;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'code=' + code ;
	x.send(params);
}

function VerificaCodice() {
	if (x.readyState == 4 && x.status == 200) {
		console.log(x.responseText);
		var response = JSON.parse(x.responseText);
		if (response[0].error == 1) {
			alert("Codice non valido!");
			return;
		} else {
			var form1= document.getElementById("DivForm");
			form1.style.display= "none";
			var form2 = document.getElementById("DivForm2");
			form2.style.display = "block";
			
		}
	}
		
	
}


//REGISTRAZIONE EFFETTIVA DOCENTE


var registraDocente = document.getElementById("registra2");	
	registraDocente.addEventListener('click', registrazioneDocente, false);
	
	function registrazioneDocente() {

	var name = document.Form2.name.value;
	var surname = document.Form2.surname.value;
	var username = document.Form2.username.value;
	var password = document.Form2.pwd1.value;
	var passwordRipetuta = document.Form2.pwd2.value;
	var type = "docente";


	if (password != passwordRipetuta) {
		error_pwd.style.display = "block";
		return false;
	}
	
	 if ( name == '' || surname == '' ||  username == '' ||  password == '' || passwordRipetuta == '') {
		return false;
	} 

	x = new XMLHttpRequest();
	x.onreadystatechange = getRegistrazioneDocente;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'name=' + name + '&surname=' + surname + '&username=' + username  + '&password=' + password + '&type='+ type;
	x.send(params);
}

function getRegistrazioneDocente() {
	if (x.readyState == 4 && x.status == 200) {
		console.log(x.responseText);
		var response = JSON.parse(x.responseText);
		if (response[0].error == 1) {
			alert("Utente già presente nel database!");
		} else {
			//reindirizzamento dopo 2000 millisecondi
			alert("Docente inserito")
			window.setTimeout(window.location.replace(serverPath + "/Login.html"), 2000);
		}
	}
}










