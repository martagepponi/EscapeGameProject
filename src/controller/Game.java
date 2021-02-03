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
import Bean.Subject;
import Bean.User;
import DAO.MiniGameDAO;
import DAO.RankingDAO;
import DAO.RoomDAO;
import DAO.SubjectDAO;


@WebServlet("/Game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public Game() {
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
		
 HttpSession session = request.getSession(true);
		
		session.removeAttribute("Minigame");
		request.getRequestDispatcher("/Game.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		
		//SE LA SESSIONE è NUOVA O SE L'UTENTE è NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { //se sessione non è nuova e utente è in sessione
		
			session.removeAttribute("Minigame");
			//viene da studentHomePageScript.js
			String id_room = request.getParameter("id_room");
			System.out.println("id_room:" + id_room);
			int idroom = Integer.parseInt(id_room);
			
			SubjectDAO subjectDAO = new SubjectDAO(connection);
			Subject subject =subjectDAO.findSubjectByIdRoom(idroom);
			
			if(subject != null) {
				session.setAttribute("subject", subject);
				session.setAttribute("id_room", "" + id_room);
				RoomDAO roomDAO = new RoomDAO(connection);
				Room room1 = roomDAO.selectById(idroom, connection);
				session.setAttribute("title", room1.getTitle());
				
				//controllo se ho già fatto accesso ad almeno un minigioco
				RankingDAO rankingDAO = new RankingDAO(connection);
				Ranking ranking = rankingDAO.findRankingByRoomAndUser(user.getIduser() , idroom);
				if (ranking == null) {
					session.setAttribute("first_time", "YES");
					request.getRequestDispatcher("/Game.jsp").forward(request, response);
				} else {
					if (ranking.getTotalrank() == 0) {
						session.setAttribute("first_time", "NO");
						
						Room room = roomDAO.selectById(idroom, connection);
						if (room != null) {
							MiniGameDAO minigameDAO = new MiniGameDAO(connection);
							if (ranking.getRank4() == 0) {
								if (ranking.getRank3()  == 0) {
									if (ranking.getRank2() == 0) {
										if (ranking.getRank1() == 0) {
											session.setAttribute("first_time", "YES");
											session.removeAttribute("minigameNumber");
											session.removeAttribute("wall");
										} else {
											session.setAttribute("minigameNumber", "1");
											session.setAttribute("wall", "2");
											AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame1(), room.getIdRoom());
											if (minigame != null) {
												session.setAttribute("prize", minigame.getPrize());
											}
										}
									} else {
										session.setAttribute("minigameNumber", "2");
										session.setAttribute("wall", "3");
										AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame2(), room.getIdRoom());
										if (minigame != null) {
											session.setAttribute("prize", minigame.getPrize());
										}
									}
								} else {
									session.setAttribute("minigameNumber", "3");
									session.setAttribute("wall", "4");
									AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame3(), room.getIdRoom());
									if (minigame != null) {
										session.setAttribute("prize", minigame.getPrize());
									}
								}
							} else {
								//TODO registrare total rank
								//showre punbteggio totale raggiunto
								//torna alla Home
	
							}
							request.getRequestDispatcher("/Game.jsp").forward(request, response);
						} else {
							request.setAttribute("msgErrore", "Problemi nel recupero della stanza");
							request.getRequestDispatcher("/HomePage").forward(request,response);
						}
					} else {
						request.setAttribute("msgErrore", "Tentativo di accesso ad una stanza già giocata.");
						request.getRequestDispatcher("/HomePage").forward(request,response);
					}
				}
				
			} else {
				
				request.getRequestDispatcher("/ErrorPage.html").forward(request,response);
			}
		}
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
