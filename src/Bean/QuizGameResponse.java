package Bean;

public class QuizGameResponse {

	
	
	protected boolean esito;
	//protected String question2;  //suggerimento: la risposta è tra 1 e 2 (ne toglie una)
	protected int errorNumber;
	protected int tentativiRimasti;
	protected String esitoFinale;
	protected int punteggio;
	protected String correctAnswer;
	protected boolean sessionExpired;
	
	public QuizGameResponse() {
		super();
		this.esito = false;
		//this.question2 = "";
		this.errorNumber = 0;
		this.punteggio = 0;
		this.esitoFinale = "";
		this.correctAnswer = "";
		this.sessionExpired = false;
		this.tentativiRimasti = Quizgame.MAX_NUM_ERRORI;
		
	}

	public boolean isEsito() {
		return esito;
	}

	public void setEsito(boolean esito) {
		this.esito = esito;
	}

//	public String getQuestion2() {
//		return question2;
//	}
//
//	public void setQuestion2(String question2) {
//		this.question2 = question2;
//	}

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

	public int getTentativiRimasti() {
		return tentativiRimasti;
	}

	public void setTentativiRimasti(int tentativiRimasti) {
		this.tentativiRimasti = tentativiRimasti;
	}
	

}
