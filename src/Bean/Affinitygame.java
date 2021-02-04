package Bean;

public class Affinitygame extends AbstractMinigame {
	public static final int MAX_NUM_ERROR = 3;

	protected int idAffgame;
	protected String word1;
	protected String word2;
	protected String word3;
	protected String word4;
	protected String rightAnswer;
	protected String hint;
	protected int errorNumber;
	protected boolean hintSelected;
	
	
	
	//COSTRUTTORE 
//	public Affinitygame(int idMinigame, String type, int idSubject, int idAffgame, String word1, String word2, 
//			String word3, String word4, String rightAnswer, String hint, String prize) {
//		super(idMinigame, type, idSubject);
//		this.idAffgame= idAffgame;
//		this.word1= word1;
//		this.word2= word2;
//		this.word3= word3;
//		this.word4= word4;
//		this.rightAnswer= rightAnswer;
//		this.hint= hint;
//		this.errorNumber = 0;
//		this.hintSelected = false;
//	}

  //COSTRUTTORE2
	public Affinitygame() {
		super();
		this.errorNumber = 0;
		this.hintSelected = false;
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


	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public boolean isHintSelected() {
		return hintSelected;
	}

	public void setHintSelected(boolean hintSelected) {
		this.hintSelected = hintSelected;
	}

	
	
}
