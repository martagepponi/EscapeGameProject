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
		int idUser = user.getIduser();
		//SE LA SESSIONE è NUOVA O SE L'UTENTE è NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { 
            //riprendo "Minigame", inserito in sessione dalla stessa Minigame.java
			AbstractMinigame minigame = (AbstractMinigame) session.getAttribute("Minigame");
			if(minigame==null) { //Se minigame non è ancora stato messo in sessione
				String id_roomString = (String) session.getAttribute("id_room");
				
				if(id_roomString == null) {
					request.getRequestDispatcher("/ErrorPage.html").forward(request, response);
				}
				
				
				
				int id_room = Integer.parseInt(id_roomString);
				RoomDAO roomDAO = new RoomDAO(connection);
				Room room = roomDAO.selectById(id_room, connection); 
				if ( room != null) { 
					//String first_time = (String) session.getAttribute("first_time"); 
					session.setAttribute("first_time", "NO"); 
					int minigameNumber = Integer.parseInt((String) session.getAttribute("minigameNumber"));
					int wallNumber = Integer.parseInt((String) session.getAttribute("wall"));
					++ wallNumber;
					session.setAttribute("wall", "" + wallNumber);
					int Id_minigame = 0;
					
					
					//Inserimento punteggio iniziale
					RankingDAO rankingDAO = new RankingDAO(connection);
					rankingDAO.InsertRank(minigameNumber, -1, user.getIduser() , id_room);
					
					//Controllo numero minigame che utente deve giocare
					if (minigameNumber == 1) {

						Id_minigame = room.getMinigame1();

					} else if (minigameNumber == 2) {
						Id_minigame = room.getMinigame2();

					} else if (minigameNumber == 3) {

						Id_minigame = room.getMinigame3();

					} else if (minigameNumber == 4) {
						Ranking ranking = rankingDAO.findRankingByRoomAndUser(idUser, id_room);
						int totalRank=  ranking.getRank1() + ranking.getRank2() + ranking.getRank3() + ranking.getRank4();
						rankingDAO.insertTotalRank(totalRank, idUser, id_room);
						Ranking ranking2 = rankingDAO.findRankingByRoomAndUser(idUser, id_room);
						session.setAttribute("ranking", ranking2);
						request.getRequestDispatcher("/FinalPage.jsp").forward(request, response);
                        return;
					}
					System.out.println("minigame1 qui" + Id_minigame);
					if (Id_minigame != 0) {
						MiniGameDAO minigameDAO = new MiniGameDAO(connection);
						minigame = (AbstractMinigame) minigameDAO.findById(Id_minigame, id_room);
						session.setAttribute("Minigame", minigame);
					
						if (minigame.getType().equalsIgnoreCase("hangmangame")) {						
							request.getRequestDispatcher("/Hangmangame.jsp").forward(request, response);
						} else if (minigame.getType().equalsIgnoreCase("quizgame")) {
							request.getRequestDispatcher("/Quizgame.jsp").forward(request, response);
						} else if (minigame.getType().equalsIgnoreCase("affinitygame")) {
							request.getRequestDispatcher("/Affinitygame.jsp").forward(request, response);
							
						} else {

							request.getRequestDispatcher("/ErrorPage.html").forward(request,response);
						}

					} else {
						request.getRequestDispatcher("/ErrorPage.html").forward(request,response);
					}

				} else {
					request.getRequestDispatcher("/ErrorPage.html").forward(request,response);
				}
			}else { //Se minigame è già in sessione
				if (minigame.getType().equalsIgnoreCase("hangmangame")) {
					request.getRequestDispatcher("/Hangmangame.jsp").forward(request, response);
				} else if (minigame.getType().equalsIgnoreCase("quizgame")) {
					request.getRequestDispatcher("/Quizgame.jsp").forward(request, response);
				} else if (minigame.getType().equalsIgnoreCase("affinitygame")) {
					request.getRequestDispatcher("/Affinitygame.jsp").forward(request, response);

				} else {

					request.getRequestDispatcher("/ErrorPage.html").forward(request,response);
				}

				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
