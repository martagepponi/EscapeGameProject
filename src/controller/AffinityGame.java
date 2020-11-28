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

import com.google.gson.Gson;

import Bean.Affinitygame;
import Bean.User;

@WebServlet("/AffinityGame")
public class AffinityGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection; 
       
    
    public AffinityGame() {
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
		//RIPRENDO SESSIONE PRECEDENTE E OGGETTO UTENTE
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		System.out.println(user.getUsername());
		
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			response.sendRedirect(getServletContext().getContextPath()+"/Login.html");
		}else {
			String word = request.getParameter("word");
			
			Affinitygame minigame = (Affinitygame)session.getAttribute("Minigame");
			String correctWord= minigame.getRightAnswer();
			PrintWriter out = response.getWriter();
			if(word.equalsIgnoreCase(correctWord)) {
				
				out.println("[{\"response\": \"OK\"}]");
				session.removeAttribute("tentativi");
			}else {
				out.println("[{\"response\": \"KO\"}]");
				int tentativi= Integer.parseInt((String) session.getAttribute("tentativi"));
				-- tentativi;
				
				
				if(tentativi > 0) {
				session.setAttribute("tentativi", tentativi);
				
				}else {
					out.println("[{\"response\": \"P\"}]");
				
					
				}
			}
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
		
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
