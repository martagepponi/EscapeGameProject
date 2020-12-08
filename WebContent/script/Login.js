var serverPath = 'http://localhost:8080/EscapeGameProject';
var login = document.getElementById("login_button");


// Click su ACCEDI

login.addEventListener('click', authentication, false);
 
function authentication() {

	var username = document.Form1.username.value;
	var password = document.Form1.pwd.value;
	if (username != "" && password != "" && username!= null && password!= null) {	
		x = new XMLHttpRequest();
		x.onreadystatechange = getLogin;
		x.open('POST', serverPath + '/Login');
		//Send the proper header information along with the request
		x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		//var params = stringa
		var params = 'username=' + username + '&password=' + password;
		x.send(params);
	}
}

function getLogin() {
	//se la richiesta è avvenuta e ho ricevuto i dati
	if (x.readyState == 4 && x.status == 200) {
		console.log(x.responseText);
		//parso risposta da json a array di un oggetto
		var response = JSON.parse(x.responseText);
		if (response[0].error == 1) {
			alert("Username e password non corretti!");
		} else {
			//salvo name nella sessione client
			sessionStorage.username = response[0].username;
			//reindirizzamento dopo 2000 millisecondi
			window.setTimeout(window.location.replace(serverPath + "/HomePage"), 2000); //HomePage controller per smistare alla giusta HomePage
		}
	}
	}