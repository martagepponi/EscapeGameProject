package Bean;

public class Hangmangame extends AbstractMinigame {
	
	int idHangman;
	String word;
	String question1;
	String question2;
	String price;
	String pass;

	
	
	//COSTRUTTORE
	public Hangmangame(int idMinigame, String type, int idSubject, int idHangman, String word, 
			String question1, String question2, String price, String pass) {
		super(idMinigame, type, idSubject);
		this.idHangman= idHangman;
		this.word= word;
		this.question1=question1;
		this.question2=question2;
		this.price=price;
		this.pass= pass;
		
	}



	public Hangmangame() {
		super();
		// TODO Auto-generated constructor stub
	}



	


	public int getIdHangman() {
		return idHangman;
	}



	public void setIdHangman(int idHangman) {
		this.idHangman = idHangman;
	}



	public String getWord() {
		return word;
	}



	public void setWord(String word) {
		this.word = word;
	}



	public String getQuestion1() {
		return question1;
	}



	public void setQuestion1(String question1) {
		this.question1 = question1;
	}



	public String getQuestion2() {
		return question2;
	}



	public void setQuestion2(String question2) {
		this.question2 = question2;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

}
