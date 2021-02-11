package Bean;

public class Quizgame extends AbstractMinigame {
	
	public static final int MAX_NUM_ERROR =2;
	protected int idQuiz;
	protected String question;
	protected String rightAnswer;
	protected String wrong1;
	protected String wrong2;
	protected int errorNumber;
	protected boolean hintSelected;
	

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
