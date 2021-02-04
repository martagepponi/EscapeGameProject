package Bean;

public abstract class AbstractMinigame{
	public static final String WON = "W";
	public static final String LOSE = "L";
	
	protected int idminigame;
	protected String type;
	protected int idsubject;
	protected Subject subject;
	protected String outcome;
	protected String prize;
	
	
	
//	public AbstractMinigame(int idminigame, String type, int idsubject) {
//		super();
//		this.idminigame = idminigame;
//		this.type = type;
//		this.idsubject = idsubject;
//		this.outcome= "";
//	}

	public AbstractMinigame() {
		super();
		this.outcome= "";
		
	}

	public int getIdminigame() {
		return idminigame;
	}

	public void setIdminigame(int idminigame) {
		this.idminigame = idminigame;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIdsubject() {
		return idsubject;
	}

	public void setIdsubject(int idsubject) {
		this.idsubject = idsubject;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome= outcome;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

}
