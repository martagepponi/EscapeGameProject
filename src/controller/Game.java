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

	    session.getAttribute("subject");
		//System.out.println("MATERIAAAAA:" + session.getAttribute("subject"));
		String prima_volta = (String) session.getAttribute("prima_volta");
		
		
		
		request.getRequestDispatcher("/Game.jsp").forward(request, response);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		System.out.println(user.getUsername());

		//SE LA SESSIONE � NUOVA O SE L'UTENTE � NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { //se sessione non � nuova e utente � in sessione
		
			session.removeAttribute("Minigame");
			String id_stanza = request.getParameter("id_stanza");
			System.out.println("id_stanza:" + id_stanza);
			int idstanza = Integer.parseInt(id_stanza);
			
			SubjectDAO subjectDAO = new SubjectDAO(connection);
	
			Subject subject =subjectDAO.findAllSubjectByIdRoom(idstanza);
			
			System.out.println(subject.getMuro1());
			if(subject != null) {
				session.setAttribute("subject", subject);
				session.setAttribute("id_stanza", "" + id_stanza);
				//controllo se ho gi� fatto accesso ad almeno un minigioco
				RankingDAO rankingDAO = new RankingDAO(connection);
				Ranking ranking = rankingDAO.findRankingByRoomAndUser(user.getIduser() , idstanza);
				if (ranking == null) {
					session.setAttribute("prima_volta", "SI");
					request.getRequestDispatcher("/Game.jsp").forward(request, response);
				} else {
					if (ranking.getTotalrank() == 0) {
						session.setAttribute("prima_volta", "NO");
						RoomDAO roomDAO = new RoomDAO(connection);
						Room room = roomDAO.selectById(idstanza, connection);
						if (room != null) {
							MiniGameDAO minigameDAO = new MiniGameDAO(connection);
							if (ranking.getRank4() == 0) {
								if (ranking.getRank3()  == 0) {
									if (ranking.getRank2() == 0) {
										if (ranking.getRank1() == 0) {
											session.setAttribute("prima_volta", "SI");
											session.removeAttribute("numeroMinigame");
											session.removeAttribute("muro");
										} else {
											session.setAttribute("numeroMinigame", "1");
											session.setAttribute("muro", "2");
											AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame1());
											if (minigame != null) {
												session.setAttribute("prize", minigame.getPrize());
											}
										}
									} else {
										session.setAttribute("numeroMinigame", "2");
										session.setAttribute("muro", "3");
										AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame2());
										if (minigame != null) {
											session.setAttribute("prize", minigame.getPrize());
										}
									}
								} else {
									session.setAttribute("numeroMinigame", "3");
									session.setAttribute("muro", "4");
									AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(room.getMinigame3());
									if (minigame != null) {
										session.setAttribute("prize", minigame.getPrize());
									}
								}
							} else {
								//TODO registrare total rank
								//mostrare punbteggio totale raggiunto
								//torna alla Home
	
							}
							request.getRequestDispatcher("/Game.jsp").forward(request, response);
						} else {
							request.setAttribute("msgErrore", "Problemi nel recupero della stanza");
							request.getRequestDispatcher("/HomePage").forward(request,response);
						}
					} else {
						request.setAttribute("msgErrore", "Tentativo di accesso ad una stanza gi� giocata.");
						request.getRequestDispatcher("/HomePage").forward(request,response);
					}
				}
				
			} else {
				//TODO gestire caso se subject non trovata e costruita....
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
