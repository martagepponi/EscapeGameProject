package Bean;

public class QuizGameResponse {

	
	
	protected boolean outcome;
	//protected String question2;  //suggerimento: la risposta è tra 1 e 2 (ne toglie una)
	protected int errorNumber;
	protected int attemptsRemained;
	protected String finalOutcome;
	protected int score;
	protected String correctAnswer;
	protected boolean sessionExpired;
	
	public QuizGameResponse() {
		super();
		this.outcome= false;
		//this.question2 = "";
		this.errorNumber = 0;
		this.score = 0;
		this.finalOutcome = "";
		this.correctAnswer = "";
		this.sessionExpired = false;
		this.attemptsRemained = Quizgame.MAX_NUM_ERROR;
		
	}

	public boolean isOutcome() {
		return outcome;
	}

	public void setOutcome(boolean outcome) {
		this.outcome= outcome;
	}



	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public String getFinalOutcome() {
		return finalOutcome;
	}

	public void setFinalOutcome(String finalOutcome) {
		this.finalOutcome = finalOutcome;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public boolean isSessionExpired() {
		return sessionExpired;
	}

	public void setSessionExpired(boolean sessionExpired) {
		this.sessionExpired = sessionExpired;
	}

	public int getAttemptsRemained() {
		return attemptsRemained;
	}

	public void setAttemptsRemained(int attemptsRemained) {
		this.attemptsRemained = attemptsRemained;
	}
	

}
