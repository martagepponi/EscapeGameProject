package Bean;

public class Affinitygame extends AbstractMinigame {
	int idAffgame;
	String word1;
	String word2;
	String word3;
	String word4;
	String rightAnswer;
	String hint;
	String price;
	String pass;
	
	
	
	//COSTRUTTORE 
	public Affinitygame(int idMinigame, String type, int idSubject, int idAffgame, String word1, String word2, 
			String word3, String word4, String rightAnswer, String hint, String price, String pass ) {
		super(idMinigame, type, idSubject);
		this.idAffgame= idAffgame;
		this.word1= word1;
		this.word2= word2;
		this.word3= word3;
		this.word4= word4;
		this.rightAnswer= rightAnswer;
		this.hint= hint;
		this.price= price;
		this.pass= pass;
		
	}

  //COSTRUTTORE2
	public Affinitygame() {
		super();
		
	}


	public int getIdAffgame() {
		return idAffgame;
	}



	public void setIdAffgame(int idAffgame) {
		this.idAffgame = idAffgame;
	}



	public String getWord1() {
		return word1;
	}



	public void setWord1(String word1) {
		this.word1 = word1;
	}



	public String getWord2() {
		return word2;
	}



	public void setWord2(String word2) {
		this.word2 = word2;
	}



	public String getWord3() {
		return word3;
	}



	public void setWord3(String word3) {
		this.word3 = word3;
	}



	public String getWord4() {
		return word4;
	}



	public void setWord4(String word4) {
		this.word4 = word4;
	}



	public String getRightAnswer() {
		return rightAnswer;
	}



	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}



	public String getHint() {
		return hint;
	}



	public void setHint(String hint) {
		this.hint = hint;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}

	
	
}
