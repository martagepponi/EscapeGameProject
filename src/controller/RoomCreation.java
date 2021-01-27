package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Bean.RoomCreationResponse;
import Bean.Subject;
import Bean.User;
import DAO.SubjectDAO;

/**
 * Servlet implementation class InitRoomCreation
 */
@WebServlet("/InitRoomCreation")
public class RoomCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
    
    public RoomCreation() {
        super();
       
    }

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
		RoomCreationResponse retval = new RoomCreationResponse();
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			retval.setSessionExpired(true);
		}else {
			int iduser = user.getIduser();	
			String action = request.getParameter("action");
			if("subjectList".equals(action)) {
				
				
				SubjectDAO subjectDao = new SubjectDAO(connection);
				List<Subject> subjectList = subjectDao.findAllSubject(); 
				if(subjectList.isEmpty()) {
					retval.setOutcome(false);
				}else {
					retval.setOutcome(true);
					retval.setSubjectList(subjectList);
				}
				
			} else {
		 		// TODO: gestione risposta su azione sconosciuta
		 		retval.setOutcome(false);
	 			
		 	}
		 	PrintWriter out = response.getWriter();
		 	Gson gson = new Gson();
			String json = gson.toJson(retval);
			System.out.println("response: "+ json );
			out.println(json);
			
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
