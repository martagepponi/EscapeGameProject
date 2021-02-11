package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Ranking;
import Bean.Room;
import Bean.Subject;
import Bean.User;
import DAO.RankingDAO;
import DAO.RoomDAO;
import DAO.SubjectDAO;
import DAO.UserDAO;




@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	public HomePage() {
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
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		//SE LA SESSIONE è NUOVA O SE L'UTENTE è NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { //SE SESSIONE NON E' NUOVA E UTENTE E' IN SESSIONE


			//SE UTENTE=STUDENTE

			if(user.getType().equals("studente")) { 
				//RIPRENDO ELENCO DI TUTTE LE STANZE
				RoomDAO roomDAO = new RoomDAO(connection);
				List<Room> Rooms1 = roomDAO.findAllRooms();
				List<Integer> allIdRooms = new ArrayList();
				//PER OGNI STANZA MI RICAVO L'ID
				for(Room room : Rooms1) { 
					int idroom = room.getIdRoom();
					//AGGIUNGO ALLA LISTA GLI ID DI TUTTE LE STANZE ESISTENTI
					allIdRooms.add(idroom);
				}
				//RIPRENDO TUTTE LE STANZE GIA' GIOCATE DALL'UTENTE
				RankingDAO rankingDAO = new RankingDAO(connection);
				List<Integer> userIdRooms= rankingDAO.idRoomList(user.getIduser());

				//SOTTRAZIONE TRA TUTTE LE STANZE E QUELLE GIA' GIOCATE DA UTENTE (TOTALRANK!=0)
				//OTTENGO STANZE NON ANCORA GIOCATE
				allIdRooms.removeAll(userIdRooms);
				List<Room> Rooms = new ArrayList();
				//RIPRENDO STANZA IN BASE A ID
				for(Integer idroom : allIdRooms) { 
					Room room = roomDAO.selectById(idroom, connection);
					Rooms.add(room); //STANZE NON ANCORA GIOCATE
				}
				session.setAttribute("Rooms", Rooms );

				//RIPRENDO TUTTI I PUNTEGGI OTTENUTI DALL'UTENTE
				List<Ranking> Rankings = rankingDAO.findAllRanking(user.getIduser());
				session.setAttribute("Rankings", Rankings);
				request.getRequestDispatcher("/StudentHomePage.jsp").forward(request, response);


				//SE UTENTE=DOCENTE

			}else {

				RoomDAO roomDAO = new RoomDAO(connection);
				List<Room> createtedRooms = roomDAO.findCreatedRooms(user.getIduser());
				RankingDAO rankingDAO = new RankingDAO(connection);
				List<Ranking> Rankings = rankingDAO.findRankingByProf(user.getIduser());

				SubjectDAO subjectDao = new SubjectDAO(connection);
				List<Subject> subjectList = subjectDao.findAllSubject();
				System.out.println(subjectList);
				session.setAttribute("SubjectList", subjectList);

				request.setAttribute("rankings", Rankings);
				request.setAttribute("createtedRooms", createtedRooms);

				request.getRequestDispatcher("/ProfHomePage.jsp").forward(request, response);

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
