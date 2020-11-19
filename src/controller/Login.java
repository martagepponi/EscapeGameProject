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

import Bean.User;
import DAO.UserDAO;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
  
    public Login() {
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
		String username = request.getParameter("username");
		String psw = request.getParameter("password");
		System.out.println("username: " +username + "" + "password: " + psw);
		PrintWriter out = response.getWriter();


			UserDAO usr = new UserDAO(connection);
			User user = usr.check(username, psw);
		

			// SE L'OGGETTO UTENTE è NULL
			if (user == null) {
				System.out.println("Login errato!");
				out.println("[{\"error\":1}]");
				
				// SE L'UTENTE ESISTE LO SALVO NELLA SESSIONE
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user); //INSERISCO OGGETTO UTENTE IN SESSIONE
				System.out.println("Login effettuato con successo!");
				out.println("[{\"error\":0, \"username\":\"" + user.getName() + "\"}]");
			}
		}
	
	
	public void destroy() {
		try {
			if (this.connection != null) {
				System.out.println("Connessione db chiusa");
				this.connection.close();
			}
		} catch (SQLException e) {
		}
	}

}
