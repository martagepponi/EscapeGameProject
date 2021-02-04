package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Bean.AbstractMinigame;
import Bean.Affinitygame;
import Bean.QuizGameResponse;
import Bean.Quizgame;
import Bean.User;
import DAO.RankingDAO;


@WebServlet("/QuizGame")
public class QuizGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_NUM_ERROR = 2;
	private static final int MAX_SCORE = 30;
	private static final int POINTS_ERROR = 15;
	
	private Connection connection = null;
    
    public QuizGame() {
        super();
    }

    
  //METODO INIT
  	public void init() throws ServletException {
  		try {
  			ServletContext context = getServletContext();
  			String driver = context.getInitParameter("dbDriver");
  			String url = "jdbc:mysql://localhost:3306/escapegame?useTimezone=true&serverTimezone=UTC";
  			String user = context.getInitParameter("dbUser");
  			String password = context.getInitParameter("dbPassword");
  			Class.forName(driver);
  			this.connection = DriverManager.getConnection(url, user, password);
  			System.out.println("Connessione al db effettuata correttamente!");

  		} catch (ClassNotFoundException e) {
  			throw new UnavailableException("Can't load database driver");
  		} catch (SQLException e) {
  			throw new UnavailableException("Couldn't get db connection");
  		}
  	}
	
  	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
				HttpSession session = request.getSession();
				String idRm = (String) session.getAttribute("id_room");
				int idroom =  Integer.parseInt(idRm) ;
				User user =(User) session.getAttribute("user");
				int iduser = user.getIduser();		
				int minigameNumber = Integer.parseInt((String) session.getAttribute("minigameNumber"));
				
				
		QuizGameResponse retval = new QuizGameResponse();
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			retval.setSessionExpired(true);
		}else {
			
			
			
			
			Quizgame minigame = (Quizgame)session.getAttribute("Minigame");
			String correctAnswer= minigame.getRightAnswer();
			int errorNumber = minigame.getErrorNumber();
			int attemptsRemained = Quizgame.MAX_NUM_ERROR - errorNumber;
			

		 	String action = request.getParameter("action");
		 	if ("insertAnswer".equals(action)) {
		 		String answer = request.getParameter("answer");
			
				if(answer.equalsIgnoreCase(correctAnswer)) {
					
					
					minigame.setOutcome(AbstractMinigame.WON);	
					int score=  scoreEstimate(minigame.getErrorNumber());
					RankingDAO rankingDAO = new RankingDAO(connection);
		 			rankingDAO.InsertRank(minigameNumber, score, iduser, idroom);
				
					retval.setOutcome(true);
					retval.setFinalOutcome(AbstractMinigame.WON);
					retval.setScore(score);
					retval.setErrorNumber(errorNumber);
					retval.setAttemptsRemained(attemptsRemained);
				}else {
					errorNumber++;
					attemptsRemained--;
					minigame.setErrorNumber(errorNumber);
					retval.setOutcome(false);
					retval.setErrorNumber(errorNumber);
				     retval.setAttemptsRemained(attemptsRemained);
				     
					if(errorNumber >= Quizgame.MAX_NUM_ERROR) {
						
						minigame.setOutcome(AbstractMinigame.LOSE);
						int score=  scoreEstimate(minigame.getErrorNumber());	 				
		 				RankingDAO rankingDAO = new RankingDAO(connection);
			 			rankingDAO.InsertRank(minigameNumber, score, iduser, idroom);
												
						retval.setFinalOutcome(AbstractMinigame.LOSE);
						retval.setScore(score);
						retval.setAttemptsRemained(attemptsRemained);
					}
				}

		 		
		 	} else {
		 		// TODO: gestione risposta su azione sconosciuta
		 		retval.setOutcome(false);
	 			retval.setErrorNumber(errorNumber);
		 	}
		}
	 	PrintWriter out = response.getWriter();
	 	Gson gson = new Gson();
		String json = gson.toJson(retval);
		System.out.println("response: "+ json );
		out.println(json);
	}



	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	protected int scoreEstimate(int errorNumber ) {
		int retval = MAX_SCORE;
		retval -= (errorNumber * POINTS_ERROR);
		
		
		return retval;
	}

}
