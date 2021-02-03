    var serverPath = 'http://localhost:8080/EscapeGameProject';
    var id_room = localStorage.getItem("id_room");
	var room_password = localStorage.getItem("room_password");

   //per riprendere idstanza e password della stanza che viene cliccata, sulla base del contatore delle miniature
	function movePassword(image) {//al click su thumbnail
		var number_image = image.id.split('_')[1]; //es: image_1---> ottengo 1 perchè prendo secondo valore dell'array[image,1]'
		//riprendo il vero valore di idroom e password e li assegno a id_room e room_password
		id_room = document.getElementById('id_room_' + number_image).value;
		room_password = document.getElementById('room_password_'+ number_image).value;
		localStorage.setItem("id_room", id_room);
		localStorage.setItem("room_password", room_password);

	}

	
	
	function passwordCheck() {
		//riprendo password inserita dall'utente
		var password = document.getElementById('password').value;
		if (password != '') {
			
			if (password != room_password ) {
				alert('password non valida!');
				return false;
			}else{
				
			document.getElementById('id_room').value = localStorage.getItem("id_room");
			document.getElementById("block_3").submit(); //a Game.java
		}
		}else{
			alert('password non valida!');
		}
		
	}
	
	
	
    function logout() {

    x = new XMLHttpRequest();
	x.onreadystatechange = getLogout;
	x.open('POST', serverPath + '/Logout');
	//Send the proper header information along with the request
	x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	x.send();
		
	}
	
	
	function getLogout() {
	if (x.readyState == 4 && x.status == 200) {
//		alert("logout");
		localStorage.removeItem("id_room");
		localStorage.removeItem("room_password");
		window.location.href = "./Login.html";
		
	}
}
