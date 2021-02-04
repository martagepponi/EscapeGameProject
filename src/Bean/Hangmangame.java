package Bean;

public class Hangmangame extends AbstractMinigame {
	public static final int MAX_NUM_ERROR = 10;
	
	protected int idHangman;
	protected String word;
	protected String question1;
	protected String question2;
	protected String displayWord;
	protected int errorNumber;
	protected boolean hintSelected;
	protected String selectedLetter;
	
	//COSTRUTTORE
//	public Hangmangame(int idMinigame, String type, int idSubject, int idHangman, String word, 
//			String question1, String question2, String prize) {
//		super(idMinigame, type, idSubject);
//		this.idHangman= idHangman;
//		this.word= word;
//		this.question1=question1;
//		this.question2=question2;
//		this.prize=prize;
//		displayWord = this.buildDisplay(word);
//		errorNumber = 0;
//		hintSelected = false;
//		selectedLetter = "";
//		
//	}

	

	public Hangmangame() {
		super();
		// TODO Auto-generated constructor stub
		errorNumber = 0;
		hintSelected = false;
		selectedLetter = "";
		
	}

	protected String buildDisplay(String word) {
		String retval = "";
		for(int i = 0; i< word.length(); i++) {
			retval += "#";
		}
		return retval;
	}


	public int getIdHangman() {
		return idHangman;
	}



	public void setIdHangman(int idHangman) {
		this.idHangman = idHangman;
	}



	public String getWord() {
		return word;
	}



	public void setWord(String word) {
		this.word = word;
		this.displayWord = this.buildDisplay(word);		
	}



	public String getQuestion1() {
		return question1;
	}



	public void setQuestion1(String question1) {
		this.question1 = question1;
	}



	public String getQuestion2() {
		return question2;
	}



	public void setQuestion2(String question2) {
		this.question2 = question2;
	}


	public String getDisplayWord() {
		return displayWord;
	}

	public void setDisplayWord(String displayWord) {
		this.displayWord = displayWord;
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
	
	public String getSelectedLetter() {
		return selectedLetter;
	}

	public void setSelectedLetter(String selectedLetter) {
		this.selectedLetter = selectedLetter;	
	}
	
	

}
