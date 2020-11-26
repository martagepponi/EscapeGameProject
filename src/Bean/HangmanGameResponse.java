package Bean;

public class HangmanGameResponse {
	
	protected boolean esito;
	protected String question2;
	protected String displayWord;
	protected int errorNumber;
	protected String esitoFinale;
	protected int punteggio;
	protected String correctWord;
	protected boolean sessionExpired;
	
	public HangmanGameResponse() {
		super();
		this.esito = false;
		this.errorNumber = 0;
		this.punteggio = 0;
		this.esitoFinale = "";
		this.correctWord = "";
		this.sessionExpired = false;
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
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

	public String getEsitoFinale() {
		return esitoFinale;
	}

	public void setEsitoFinale(String esitoFinale) {
		this.esitoFinale = esitoFinale;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
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

}
