package Bean;

public abstract class AbstractMinigame implements MiniGame{
	public static final String VINTO = "V";
	public static final String PERSO = "P";
	
	protected int idminigame;
	protected String type;
	protected int idsubject;
	protected Subject materia;
	protected String esito;
	
	
	
	public AbstractMinigame(int idminigame, String type, int idsubject) {
		super();
		this.idminigame = idminigame;
		this.type = type;
		this.idsubject = idsubject;
		this.esito = "";
	}

	public AbstractMinigame() {
		super();
		this.esito = "";
		
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

	public Subject getMateria() {
		return materia;
	}

	public void setMateria(Subject materia) {
		this.materia = materia;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}
}
