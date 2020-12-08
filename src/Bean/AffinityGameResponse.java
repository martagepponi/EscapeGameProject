package Bean;

public class AffinityGameResponse {
	
	protected boolean outcome;
	protected String question2;
	protected int errorNumber;
	protected int attemptsRemained;
	protected String finalOutcome;
	protected int score;
	protected String correctWord;
	protected boolean sessionExpired;
	
	public AffinityGameResponse() {
		super();
		this.outcome= false;
		this.question2 = "";
		this.errorNumber = 0;
		this.score = 0;
		this.finalOutcome = "";
		this.correctWord = "";
		this.sessionExpired = false;
		this.attemptsRemained = Affinitygame.MAX_NUM_ERROR;
	}

	public boolean isOutcome() {
		return outcome;
	}

	public void setOutcome(boolean outcome) {
		this.outcome= outcome;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
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

	public String getCorrectWord() {
		return correctWord;
	}

	public void setCorrectWord(String correctWord) {
		this.correctWord = correctWord;
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
