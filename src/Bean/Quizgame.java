package Bean;

public class Quizgame extends AbstractMinigame {
	
	public static final int MAX_NUM_ERRORI =2;
	protected int idQuiz;
	protected String question;
	protected String rightAnswer;
	protected String wrong1;
	protected String wrong2;
	protected String pass;
	protected int errorNumber;
	protected boolean hintSelected;
	
	
	
	//COSTRUTTORE
	public Quizgame(int idMinigame, String type, int idSubject, int idQuiz, String question, String rightAnswer, String wrong1, String wrong2, 
			String prize, String pass) {
		super(idMinigame, type, idSubject);
		this.idQuiz= idQuiz;
		this.question=question;
		this.rightAnswer= rightAnswer;
		this.wrong1= wrong1;
		this.wrong2= wrong2;
		this.prize=prize;
		this.pass=pass;
		errorNumber= 0;
		hintSelected = false;
		
	}
	
	
	



	public Quizgame() {
		super();
		errorNumber= 0;
		hintSelected = false;
	}



	public int getIdQuiz() {
		return idQuiz;
	}



	public void setIdQuiz(int idQuiz) {
		this.idQuiz = idQuiz;
	}



	public String getQuestion() {
		return question;
	}



	public void setQuestion(String question) {
		this.question = question;
	}



	public String getRightAnswer() {
		return rightAnswer;
	}



	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}



	public String getWrong1() {
		return wrong1;
	}



	public void setWrong1(String wrong1) {
		this.wrong1 = wrong1;
	}



	public String getWrong2() {
		return wrong2;
	}



	public void setWrong2(String wrong2) {
		this.wrong2 = wrong2;
	}



	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
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
