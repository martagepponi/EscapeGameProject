package Bean;

public class HangmanGameResponse {
	
	protected boolean outcome;
	protected String question2;
	protected String displayWord;
	protected int errorNumber;
	protected String finalOutcome;
	protected int score;
	protected String correctWord;
	protected boolean sessionExpired;
	protected String selectedLetter;
	
	public HangmanGameResponse() {
		super();
		this.outcome= false;
		this.errorNumber = 0;
		this.score = 0;
		this.finalOutcome = "";
		this.correctWord = "";
		this.sessionExpired = false;
		this.selectedLetter="";
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

	public String getSelectedLetter() {
		return selectedLetter;
	}

	public void setSelectedLetter(String selectedLetter) {
		this.selectedLetter = selectedLetter;
	}
	

}
