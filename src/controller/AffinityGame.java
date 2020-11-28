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

@WebServlet("/AffinityGame")
public class AffinityGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		User user =(User) session.getAttribute("user");
		System.out.println(user.getUsername());
		
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
					int punteggio = 0;
					// TODO: calcolo punteggio e scrivi classifica
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
						int punteggio = 0;
						// TODO: calcola punteggio
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

}
