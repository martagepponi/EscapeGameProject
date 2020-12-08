var serverPath = 'http://localhost:8080/EscapeGameProject';

//REGISTRAZIONE STUDENTE
function loadStudent(){ //altrimenti da errore visto che listener non legge nulla se clicchiamo il link del docente
	var registerStudent = document.getElementById("register1");	
	registerStudent.addEventListener('click', studentRegistration, false);

}


function studentRegistration() {

	var name = document.studentForm.name.value;
	var surname = document.studentForm.surname.value;
	var username = document.studentForm.username.value;
	var password = document.studentForm.pwd1.value;
	var repetedPassword = document.studentForm.pwd2.value;
	var type = "studente";



	if (password != repetedPassword) {
		error_pwd.style.display = "block";
		return false;
	}
	
	 if ( name == '' || surname == '' || username == '' ||  password == '' || repetedPassword == '') {
		return false;
	} 

	x = new XMLHttpRequest();
	x.onreadystatechange = getstudentRegistration;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'name=' + name + '&surname=' + surname  +  '&username=' + username + '&password=' + password + '&type=' + type;
	x.send(params);
}

function getstudentRegistration() {
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
var insertCode = document.getElementById("ok");
insertCode.addEventListener('click', initialization, false);

//1. INSERIMENTO CODICE


function initialization() {
	
	var code = document.Form.code.value;
	
	if( code == null || code ==''){
		return false;
	} 
	
	
x = new XMLHttpRequest();
	x.onreadystatechange = codeVerify;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'code=' + code ;
	x.send(params);
}

function codeVerify() {
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


var registerProf = document.getElementById("register2");	
	registerProf.addEventListener('click', profRegistration, false);
	
	function profRegistration() {

	var name = document.Form2.name.value;
	var surname = document.Form2.surname.value;
	var username = document.Form2.username.value;
	var password = document.Form2.pwd1.value;
	var repetedPassword = document.Form2.pwd2.value;
	var type = "docente";


	if (password != repetedPassword) {
		error_pwd.style.display = "block";
		return false;
	}
	
	 if ( name == '' || surname == '' ||  username == '' ||  password == '' || repetedPassword == '') {
		return false;
	} 

	x = new XMLHttpRequest();
	x.onreadystatechange = getprofRegistration;
	x.open('POST', serverPath + '/Registration');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var params = 'name=' + name + '&surname=' + surname + '&username=' + username  + '&password=' + password + '&type='+ type;
	x.send(params);
}

function getprofRegistration() {
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










