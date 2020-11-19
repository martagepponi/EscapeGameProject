package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import Bean.User;
import DAO.RankingDAO;
import DAO.RoomDAO;
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
		System.out.println(user.getUsername());

		//SE LA SESSIONE è NUOVA O SE L'UTENTE è NULL TORNO ALLA PAG DI LOGIN
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else { //se sessione non è nuova e utente è in sessione
			if(user.getType().equals("studente")) { //se è uno studente....

				//RIPRENDO DAO 
				RoomDAO roomDAO = new RoomDAO(connection);
				//RIPRENDO LA LISTA DELLE STANZE 
				List<Room> Rooms = roomDAO.findAllRooms();
				//LA RICHIESTA INCAPSULA LA LISTA DELLE STANZE
				request.setAttribute("Rooms", Rooms);
				
				
				
				RankingDAO rankingDAO = new RankingDAO(connection);
				List<Ranking> Rankings = rankingDAO.findAllRanking(user.getIduser());
				request.setAttribute("Rankings", Rankings);
				
			

				request.getRequestDispatcher("/StudentHomePage.jsp").forward(request, response);

			}else { //se è un docente...
				
				RoomDAO roomDAO = new RoomDAO(connection);
				List<Room> createdRooms = roomDAO.findCreatedRooms(user.getIduser());
				RankingDAO rankingDAO = new RankingDAO(connection);
				List<Ranking> Rankings = rankingDAO.findRankingByProf(user.getIduser());
				
				request.setAttribute("rankings", Rankings);
				request.setAttribute("createdRooms", createdRooms);
				
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
