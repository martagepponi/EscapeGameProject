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
				String idst = (String) session.getAttribute("id_stanza");
				int idStanza =  Integer.parseInt(idst) ;
				User user =(User) session.getAttribute("user");
				int iduser = user.getIduser();		
				int numeroMinigame = Integer.parseInt((String) session.getAttribute("numeroMinigame"));
				
				
		QuizGameResponse retval = new QuizGameResponse();
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
//			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
			retval.setSessionExpired(true);
		}else {
			
			
			
			
			Quizgame minigame = (Quizgame)session.getAttribute("Minigame");
			String correctAnswer= minigame.getRightAnswer();
			int errorNumber = minigame.getErrorNumber();
			int tentativiRimasti = Quizgame.MAX_NUM_ERRORI - errorNumber;
			//String hint = minigame.getHint();
			//System.out.println("SUGGERIMENTO:"+ hint);

		 	String action = request.getParameter("action");
		 	if ("insertAnswer".equals(action)) {
		 		String answer = request.getParameter("answer");
			
				if(answer.equalsIgnoreCase(correctAnswer)) {

					
					//CALCOLA PUNTEGGIO
				
					retval.setEsito(true);
					retval.setEsitoFinale(AbstractMinigame.VINTO);
					//retval.setPunteggio(punteggio);
					retval.setErrorNumber(errorNumber);
					retval.setTentativiRimasti(tentativiRimasti);
				}else {
					errorNumber++;
					tentativiRimasti--;
					minigame.setErrorNumber(errorNumber);
					retval.setEsito(false);
					retval.setErrorNumber(errorNumber);
				     retval.setTentativiRimasti(tentativiRimasti);
					if(errorNumber >= Quizgame.MAX_NUM_ERRORI) {
						
						//int punteggio=  calcolaPunteggio(minigame.getErrorNumber(), minigame.isHintSelected());	 				
		 				//RankingDAO rankingDAO = new RankingDAO(connection);
			 			//rankingDAO.InsertRank(numeroMinigame, punteggio, iduser, idStanza);
												
						retval.setEsitoFinale(AbstractMinigame.PERSO);
					//	retval.setPunteggio(punteggio);
						retval.setCorrectAnswer(answer);
					}
				}
//		 	} else if ("hint".equals(action)) {
//	 			minigame.setHintSelected(true);
//	 			
//		 		retval.setEsito(true);
//	 			retval.setErrorNumber(errorNumber);
//	 			retval.setQuestion2(hint);
//	 			System.out.println("SUGGERIMENTO:"+ hint);
		 		
		 	} else {
		 		// TODO: gestione risposta su azione sconosciuta
		 		retval.setEsito(false);
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

}
