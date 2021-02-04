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
import DAO.RegistrationDAO;


@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;


	public Registration() {
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


		//VALIDAZIONE CODICE INIZIALE (DOCENTE)
		//riprendo codice inserito da docente
		String Stringcode = request.getParameter("code");
		if (Stringcode != null) {
			//int code = Integer.parseInt(Stringcode);
			System.out.println("codice:   " + Stringcode);
			PrintWriter out2 = response.getWriter();

            //verifico se codice è corretto 
			if("102030".equalsIgnoreCase(Stringcode)) {
				System.out.println("Codice corretto!");
				out2.println("[{\"error\":0}]");
			} else { 
				System.out.println("Codice errato!");
				out2.println("[{\"error\":1}]");
			} 
		} 


		//REGISTRAZIONE STUDENTE e REGISTRAZIONE DOCENTE

		//riprendo parametri inseriti dall'utente
		String name= request.getParameter("name");
		String surname= request.getParameter("surname");
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		String type = request.getParameter("type");
		if (type!= null) {  
			System.out.println("type:   "+ type);

			RegistrationDAO registrationDAO = new RegistrationDAO(connection);
			boolean registrationError = registrationDAO.register(username); //per controllare se username inserito già esiste in db
			PrintWriter out = response.getWriter();
			System.out.println("risultato query:  "+ registrationError);
            //se username già esiste in db
			if (!registrationError) {
				out.println("[{\"error\":1}]");

			//se username non esiste in db procedo con inserimento utente 
			}else if(registrationError){
				registrationDAO.addUser(name, surname, username, password, type);
				out.println("[{\"error\":0}]");
			} }else {
				//errore campi non riempiti
				System.out.println("Campi non riempiti");

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

