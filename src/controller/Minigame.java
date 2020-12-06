package controller;

import java.io.IOException;
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

import Bean.AbstractMinigame;
import Bean.Ranking;
import Bean.Room;
import Bean.User;
import DAO.MiniGameDAO;
import DAO.RankingDAO;
import DAO.RoomDAO;

@WebServlet("/Minigame")
public class Minigame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public Minigame() {
		super();

	}

	// METODO INIT
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		System.out.println(user.getUsername());

		//SE LA SESSIONE � NUOVA O SE L'UTENTE � NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { //se sessione non � nuova e utente � in sessione

			String id_stanzaString = (String) session.getAttribute("id_stanza");
			int id_stanza = Integer.parseInt(id_stanzaString);
	
			RoomDAO roomDAO = new RoomDAO(connection);
			Room room = roomDAO.selectById(id_stanza, connection);
			if ( room != null) {
				String prima_volta = (String) session.getAttribute("prima_volta");
				session.setAttribute("prima_volta", "NO"); // COS� GAME.JSP SA CHE DEVE ATTIVARE LA SECONDA MAP
				System.out.println("primavolta:" + prima_volta);
	
				int numeroMinigame = Integer.parseInt((String) session.getAttribute("numeroMinigame"));
				System.out.println("numeroMinigame: " + numeroMinigame);
		
				int numeroMuro = Integer.parseInt((String) session.getAttribute("muro"));
				++ numeroMuro;
				session.setAttribute("muro", "" + numeroMuro);
				System.out.println("*************************************numeroMuro: " + numeroMuro);
	
				
				int Id_minigame = 0;
				RankingDAO rankingDAO = new RankingDAO(connection);
				rankingDAO.InsertRank(numeroMinigame, -1, user.getIduser() , id_stanza);
				if (numeroMinigame == 1) {
	
					Id_minigame = room.getMinigame1();
	
				} else if (numeroMinigame == 2) {
					Id_minigame = room.getMinigame2();
	
				} else if (numeroMinigame == 3) {
	
					Id_minigame = room.getMinigame3();
	
				} else if (numeroMinigame == 4) {
					
					int id_user = user.getIduser();
				
					System.out.println("iduser"+ id_user);
					Ranking ranking = rankingDAO.findRankingByRoomAndUser(id_user, id_stanza);
					
					session.setAttribute("ranking", ranking);
					
					
					request.getRequestDispatcher("/finalPage.jsp").forward(request, response);
					
					
					
				}
				System.out.println("minigame1 qui" + Id_minigame);
				if (Id_minigame != 0) {
					MiniGameDAO minigameDAO = new MiniGameDAO(connection);
					AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(Id_minigame);
					//AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(6);
					System.out.println("MINIGAME TIPO:" + minigame.getType());
	
					session.setAttribute("Minigame", minigame);
	               //minigame.setType("affinitygame");
					if (minigame.getType().equalsIgnoreCase("hangmangame")) {
						// request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
						request.getRequestDispatcher("/Hangmangame.jsp").forward(request, response);
					} else if (minigame.getType().equalsIgnoreCase("quizgame")) {
						request.getRequestDispatcher("/Quizgame.jsp").forward(request, response);
						// request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
					} else if (minigame.getType().equalsIgnoreCase("affinitygame")) {
						request.getRequestDispatcher("/Affinitygame.jsp").forward(request, response);
						// request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
	
					} else {
	
						// TODO errore
					}
	
				} else {
					// TODO: errore
				}
	
			} else {
				//TODO : ERRORE
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
