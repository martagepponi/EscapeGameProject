
    var id_room = localStorage.getItem("id_room");
	var room_password = localStorage.getItem("room_password");


	function movePassword(image) {
		var number_image = image.id.split('_')[1];
		//assegno a id_room valore id_room_1
		id_room = document.getElementById('id_room_' + number_image).value;
		//assegno a room_password valore room_password_1
		room_password = document.getElementById('room_password_'
				+ number_image).value;
		localStorage.setItem("id_room", id_room);
		localStorage.setItem("room_password", room_password);

	}

	function logout() {

		localStorage.removeItem("id_room");
		localStorage.removeItem("room_password");
		window.location.href = "./Login.html";
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