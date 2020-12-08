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

import Bean.User;
import DAO.RankingDAO;
import Bean.AbstractMinigame;
import Bean.HangmanGameResponse;
import Bean.Hangmangame;

 
@WebServlet("/HangmanGame")
public class HangmanGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_NUM_ERROR = 10;
	private static final int MAX_SCORE = 30;
	private static final int POINTS_ERROR = 2;
	private static final int POINTS_HINT = 10;
	private Connection connection = null;
  
    public HangmanGame() {
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
		
		System.out.println(user.getUsername());

		//SE LA SESSIONE è NUOVA O SE L'UTENTE è NULL TORNO ALLA PAG DI LOGIN
	 	HangmanGameResponse retval = new HangmanGameResponse();
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
//			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
			retval.setSessionExpired(true);
		}else {
			Hangmangame minigame = (Hangmangame)session.getAttribute("Minigame");
		 	String word = minigame.getWord();
		 	String displayWord = minigame.getDisplayWord();
		 	String question1 = minigame.getQuestion1();
		 	String question2 = minigame.getQuestion2();
		 	int errorNumber = minigame.getErrorNumber();
		 	String prize = minigame.getPrize();
		 	System.out.println("question2:" + question2);
		 	System.out.println("prize:" + prize);
		 	
		 	
		 	
		 	String action = request.getParameter("action");
		 	if ("selectLetter".equals(action)) {
		 		String letter = request.getParameter("letterSelected");
		 		if (letter.length() == 1) {
			 		if (word.indexOf(letter) != -1)
			 		{
			 			int pos = 0;
			 			String temp_mask = displayWord;
			 			while (word.indexOf(letter, pos) != -1) {
				 			pos = word.indexOf(letter, pos);
				 			temp_mask = temp_mask.substring(0, pos) + letter + temp_mask.substring(pos + 1);
				 			pos++;
			 			}
			 			displayWord = temp_mask;
			 			minigame.setDisplayWord(temp_mask);
			 			retval.setOutcome(true);
			 			retval.setDisplayWord(displayWord);
			 			retval.setErrorNumber(minigame.getErrorNumber());
			 			if (temp_mask.indexOf("#") == -1) {
			 				minigame.setOutcome(AbstractMinigame.WON);		 				
			 				int score=  scoreEstimate(minigame.getErrorNumber(), minigame.isHintSelected());
			 				
			 				RankingDAO rankingDAO = new RankingDAO(connection);
				 			rankingDAO.InsertRank(minigameNumber, score, iduser, idroom);
				 			
			 				retval.setCorrectWord(word);
			 				retval.setFinalOutcome(AbstractMinigame.WON);
			 				retval.setScore(score);
			 			}
			 		} else {
			 			errorNumber++;
			 			minigame.setErrorNumber(errorNumber);
			 			retval.setOutcome(false);
			 			retval.setDisplayWord(displayWord);
			 			retval.setErrorNumber(minigame.getErrorNumber());
			 			if (MAX_NUM_ERROR <= errorNumber) {
			 				minigame.setOutcome(AbstractMinigame.LOSE);
                            int score=  scoreEstimate(minigame.getErrorNumber(), minigame.isHintSelected());
			 				
			 				RankingDAO rankingDAO = new RankingDAO(connection);
				 			rankingDAO.InsertRank(minigameNumber, score, iduser, idroom);

			 				retval.setCorrectWord(word);
			 				retval.setFinalOutcome(AbstractMinigame.LOSE);
			 				retval.setScore(score);
			 			}
			 		}
		 		} else {
		 			retval.setOutcome(false);
		 			retval.setDisplayWord(displayWord);
		 			retval.setErrorNumber(minigame.getErrorNumber());
		 		}
		 	} else if ("hint".equals(action)) {
	 			minigame.setHintSelected(true);
	 			
	 			
	 			
	 			
		 		retval.setOutcome(true);
	 			retval.setDisplayWord(displayWord);
	 			retval.setErrorNumber(minigame.getErrorNumber());
	 			retval.setQuestion2(question2);
	 			
		 	} else {
		 		// TODO: gestione risposta su azione sconosciuta
		 		retval.setOutcome(false);
	 			retval.setDisplayWord(displayWord);
	 			retval.setErrorNumber(minigame.getErrorNumber());
		 	}
		 	PrintWriter out = response.getWriter();
		 	Gson gson = new Gson();
			String json = gson.toJson(retval);
			System.out.println("response: "+ json );
			out.println(json);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected int scoreEstimate(int errorNumber, boolean requestedHint) {
		int retval = MAX_SCORE;
		retval -= (errorNumber * POINTS_ERROR);
		retval -= (requestedHint ? POINTS_HINT : 0);
		
		return retval;
	}
	
	
	
}
	

