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
	private static final int MAX_SCORE = 30;
	private static final int POINTS_ERROR = 6;
	private static final int POINTS_HINT = 12;

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
		String idRm = (String) session.getAttribute("id_room");
		int idRoom =  Integer.parseInt(idRm) ;
		User user =(User) session.getAttribute("user");
		int iduser = user.getIduser();		
		int minigameNumber = Integer.parseInt((String) session.getAttribute("minigameNumber"));

		AffinityGameResponse retval = new AffinityGameResponse();
		if(session.isNew() || user == null) {
			retval.setSessionExpired(true);
		}else {
			Affinitygame minigame = (Affinitygame)session.getAttribute("Minigame");
			String correctWord= minigame.getRightAnswer();
			int errorNumber = minigame.getErrorNumber();
			int attemptsRemained = Affinitygame.MAX_NUM_ERROR - errorNumber;
			String hint = minigame.getHint();

			String action = request.getParameter("action");
			if ("insertWord".equals(action)) {
				String word = request.getParameter("word");

				if(word.equalsIgnoreCase(correctWord)) {


					int score=  scoreEstimate(minigame.getErrorNumber(), minigame.isHintSelected());

					RankingDAO rankingDAO = new RankingDAO(connection);
					rankingDAO.InsertRank(minigameNumber, score, iduser, idRoom);

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
					if(errorNumber >= Affinitygame.MAX_NUM_ERROR) {

						int score=  scoreEstimate(minigame.getErrorNumber(), minigame.isHintSelected());	 				
						RankingDAO rankingDAO = new RankingDAO(connection);
						rankingDAO.InsertRank(minigameNumber, score, iduser, idRoom);

						retval.setFinalOutcome(AbstractMinigame.LOSE);
						retval.setScore(score);
						retval.setCorrectWord(correctWord);
					}
				}
			} else if ("hint".equals(action)) {
				minigame.setHintSelected(true);

				retval.setOutcome(true);
				retval.setErrorNumber(errorNumber);
				retval.setQuestion2(hint);

			} else {

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

	public void destroy() {
		try{
			if(this.connection!=null) {
				this.connection.close();
				System.out.println("Connessione db chiusa");
			}	
		}
		catch(SQLException e) {}
	}

	protected int scoreEstimate(int errorNumber, boolean requestedHint) {
		int retval = MAX_SCORE;
		retval -= (errorNumber * POINTS_ERROR);
		retval -= (requestedHint ? POINTS_HINT : 0);

		return retval;
	}

}
