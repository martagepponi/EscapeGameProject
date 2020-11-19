package Bean;

public class Quizgame extends AbstractMinigame {
	
	int idQuiz;
	String question;
	String rightAnswer;
	String wrong1;
	String wrong2;
	String prize;
	String pass;
	
	
	
	//COSTRUTTORE
	public Quizgame(int idMinigame, String type, int idSubject, int idQuiz, String question, String rightAnswer, String wrong1, String wrong2, 
			String prize, String pass) {
		super(idMinigame, type, idSubject);
		this.idQuiz= idQuiz;
		this.question=question;
		this.rightAnswer= rightAnswer;
		this.wrong1= wrong1;
		this.wrong2= wrong2;
		this.prize=prize;
		this.pass=pass;
		
	}
	
	
	



	public Quizgame() {
		super();
		// TODO Auto-generated constructor stub
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



	public String getPrize() {
		return prize;
	}



	public void setPrice(String prize) {
		this.prize = prize;
	}



	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

}
