package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Subject;
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
		
		
	    session.getAttribute("subject");
		//System.out.println("MATERIAAAAA:" + session.getAttribute("subject"));
		String prima_volta = (String) session.getAttribute("prima_volta");
		
		
		
		request.getRequestDispatcher("/Game.jsp").forward(request, response);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String id_stanza = request.getParameter("id_stanza");
		System.out.println("id_stanza:" + id_stanza);
		int idstanza = Integer.parseInt(id_stanza);
		
		SubjectDAO subjectDAO = new SubjectDAO(connection);

		Subject subject =subjectDAO.findAllSubjectByIdRoom(idstanza);
		
		System.out.println(subject.getMuro1());
		if(subject != null) {
		//  request.setAttribute("subject", subject);
		  session.setAttribute("subject", subject);
		  
			
			session.setAttribute("id_stanza", "" + id_stanza);
			session.setAttribute("prima_volta", "SI");
			
			System.out.println("idstanza" + id_stanza);
		
		request.getRequestDispatcher("/Game.jsp").forward(request, response);
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
