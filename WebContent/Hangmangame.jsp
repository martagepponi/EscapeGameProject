<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Bean.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Impiccato</title>
<script type="text/javascript">
	//FUNZIONI HANGMANGAME

	var can_play = true;
	var display_word = "";
	var used_letters = "";
	var wrong_guesses = 0;
	var correctWord = "";
	var hintRequested = 0;

	function selectLetter(l) {
		if (can_play == false) {
			return;
		}

		if (used_letters.indexOf(l) != -1) {
			return;
		}

		used_letters += l;
		document.game.usedLetters.value = used_letters;

		if (correctWord.indexOf(l) != -1) {
			// correct letter guess
			pos = 0;
			temp_mask = display_word;

			while (correctWord.indexOf(l, pos) != -1) {
				pos = correctWord.indexOf(l, pos);
				end = pos + 1;

				start_text = temp_mask.substring(0, pos);
				end_text = temp_mask.substring(end, temp_mask.length);

				temp_mask = start_text + l + end_text;
				pos = end;
			}

			display_word = temp_mask;
			document.game.displayWord.value = display_word;

			if (display_word.indexOf("#") == -1) {
				//won
				alert("Vinto!");
				var punti_errore = wrong_guesses + hintRequested; //DA PASSARE ALLA SERVLET PER IL RANKING
				alert("punti di errore: " + punti_errore);
				can_play = false;
			}
		} else {
			//incortect letter guess
			wrong_guesses += 1;
			eval("document.hm.src=\"images/hangmangame/hm" + wrong_guesses
					+ ".gif\"");

			if (wrong_guesses == 10) {
				//lost
				alert("Perso!");
				can_play = false;
			}
		}
	}

	function hint() {
		document.getElementById("question2").style.display = "block";
		hintRequested = hintRequested + 5;
	}
	function selectWord(word) {
		document.getElementById("question2").style.display = "none";

		can_play = true;
		correctWord = word;
		masked_word = createMask(word);
		document.game.displayWord.value = masked_word;
		display_word = masked_word;
	}

	function createMask(m) {
		mask = "";
		word_lenght = m.length;

		for (i = 0; i < word_lenght; i++) {
			mask += "#";
		}
		return mask;
	}

	//FINE SCRIPT HANGMANGAME
</script>
</head>
<body>


	<div id="Hangmangame" align="center">
		<h1>Questo è il gioco dell&apos;Impiccato</h1>


		<h2 id="question1"></h2>
		<h2 id="question2"></h2>
		<p>
			<img src="images/hangmangame/hmstart.gif" height="125" width="75"
				name="hm">
		</p>
		<form name="game">
			<p>
				Parola: <input id="word" type="text" name="displayWord"><br>
				Lettere: <input type="text" name="usedLetters">
			</p>
		</form>

		<p>
			<a href="javascript:selectLetter('A');">A</a> | <a
				href="javascript:selectLetter('B');">B</a> | <a
				href="javascript:selectLetter('C');">C</a> | <a
				href="javascript:selectLetter('D');">D</a> | <a
				href="javascript:selectLetter('E');">E</a> | <a
				href="javascript:selectLetter('F');">F</a> | <a
				href="javascript:selectLetter('G');">G</a> | <a
				href="javascript:selectLetter('H');">H</a> | <a
				href="javascript:selectLetter('I');">I</a> | <a
				href="javascript:selectLetter('J');">J</a> | <a
				href="javascript:selectLetter('K');">K</a> | <a
				href="javascript:selectLetter('L');">L</a> | <a
				href="javascript:selectLetter('M');">M</a><br> <a
				href="javascript:selectLetter('N');">N</a> | <a
				href="javascript:selectLetter('O');">O</a> | <a
				href="javascript:selectLetter('P');">P</a> | <a
				href="javascript:selectLetter('Q');">Q</a> | <a
				href="javascript:selectLetter('R');">R</a> | <a
				href="javascript:selectLetter('S');">S</a> | <a
				href="javascript:selectLetter('T');">T</a> | <a
				href="javascript:selectLetter('U');">U</a> | <a
				href="javascript:selectLetter('V');">V</a> | <a
				href="javascript:selectLetter('W');">W</a> | <a
				href="javascript:selectLetter('X');">X</a> | <a
				href="javascript:selectLetter('Y');">Y</a> | <a
				href="javascript:selectLetter('Z');">Z</a>
		</p>

		<p>
			<a href="javascript:hint()">Suggerimento</a>
		</p>



	</div>


	<!-- ------------------------------------------------------------------------ -->

</body>
</html>