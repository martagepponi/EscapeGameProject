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
import Bean.AffinityGameResponse;
import Bean.Affinitygame;
import Bean.User;
import DAO.RankingDAO;

@WebServlet("/AffinityGame")
public class AffinityGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_PUNTEGGIO = 30;
	private static final int PUNTI_ERRORE = 6;
	private static final int PUNTI_HINT = 12;
	
	private Connection connection; 
       
    
    public AffinityGame() {
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
		
		AffinityGameResponse retval = new AffinityGameResponse();
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
//			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
			retval.setSessionExpired(true);
		}else {
			Affinitygame minigame = (Affinitygame)session.getAttribute("Minigame");
			String correctWord= minigame.getRightAnswer();
			int errorNumber = minigame.getErrorNumber();
			int tentativiRimasti = Affinitygame.MAX_NUM_ERRORI - errorNumber;
			String hint = minigame.getHint();

		 	String action = request.getParameter("action");
		 	if ("insertWord".equals(action)) {
		 		String word = request.getParameter("word");
			
				if(word.equalsIgnoreCase(correctWord)) {
					
					
					int punteggio=  calcolaPunteggio(minigame.getErrorNumber(), minigame.isHintSelected());
	 				
	 				RankingDAO rankingDAO = new RankingDAO(connection);
		 			rankingDAO.InsertRank(numeroMinigame, punteggio, iduser, idStanza);
				
					retval.setEsito(true);
					retval.setEsitoFinale(AbstractMinigame.VINTO);
					retval.setPunteggio(punteggio);
					retval.setErrorNumber(errorNumber);
					retval.setTentativiRimasti(tentativiRimasti);
				}else {
					errorNumber++;
					tentativiRimasti--;
					minigame.setErrorNumber(errorNumber);
					retval.setEsito(false);
					retval.setErrorNumber(errorNumber);
					retval.setTentativiRimasti(tentativiRimasti);
					if(errorNumber >= Affinitygame.MAX_NUM_ERRORI) {
						
						int punteggio=  calcolaPunteggio(minigame.getErrorNumber(), minigame.isHintSelected());	 				
		 				RankingDAO rankingDAO = new RankingDAO(connection);
			 			rankingDAO.InsertRank(numeroMinigame, punteggio, iduser, idStanza);
												
						retval.setEsitoFinale(AbstractMinigame.PERSO);
						retval.setPunteggio(punteggio);
						retval.setCorrectWord(correctWord);
					}
				}
		 	} else if ("hint".equals(action)) {
	 			minigame.setHintSelected(true);
	 			
		 		retval.setEsito(true);
	 			retval.setErrorNumber(errorNumber);
	 			retval.setQuestion2(hint);
		 		
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

	public void destroy() {
		try{
			if(this.connection!=null) {
				this.connection.close();
				System.out.println("Connessione db chiusa");
			}	
		}
		catch(SQLException e) {}
	}
	
	protected int calcolaPunteggio(int numeroErrori, boolean requestedHint) {
		int retval = MAX_PUNTEGGIO;
		retval -= (numeroErrori * PUNTI_ERRORE);
		retval -= (requestedHint ? PUNTI_HINT : 0);
		
		return retval;
	}

}
