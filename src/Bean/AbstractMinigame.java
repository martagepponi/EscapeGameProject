package Bean;

public abstract class AbstractMinigame implements MiniGame{ 
	
	int idminigame;
	String type;
	int idsubject;
	Subject	materia;
	
	
	
	public AbstractMinigame(int idminigame, String type, int idsubject) {
		super();
		this.idminigame = idminigame;
		this.type = type;
		this.idsubject = idsubject;
	}

	
	


	public AbstractMinigame() {
		super();
		
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






	
	
	
	

}
