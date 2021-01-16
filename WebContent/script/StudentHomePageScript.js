/**
 * 
 */
    var id_room = localStorage.getItem("id_room");
	var room_password = localStorage.getItem("room_password");

	/*function load() {
		
		document.getElementById('block_3').style.display = "none";
		
	}*/
/*
	function show(number) {
		if (number == 2) {
			document.getElementById('block_2').style.display = "block";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "none";
		}
		if (number == 3) {
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "block";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "none";
		}
		if (number == 4) {
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "none";
			document.getElementById('block_5').style.display = "none";
			document.getElementById('block_4').style.display = "block";
		}
		
		if (number == 5){
			document.getElementById('block_2').style.display = "none";
			document.getElementById('block_3').style.display = "none";
			document.getElementById('block_4').style.display = "none";
			document.getElementById('block_5').style.display = "block";
		}

		
	}*/
	
	/*function show3(){
		document.getElementById('block_3').style.display = "block";
		
	}*/

	function movePassword(image) {
		var number_image = image.id.split('_')[1];
		id_room = document.getElementById('id_room_' + number_image).value;
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
		var password = document.getElementById('password').value;
		if (password != '') {
			
			if (password != room_password ) {
				alert('password non valida!');
				return false;
			}else{
				
			document.getElementById('id_room').value = localStorage.getItem("id_room");
			document.getElementById("block_3").submit();
		}
		}else{
			alert('password non valida!');
		}
		
	}