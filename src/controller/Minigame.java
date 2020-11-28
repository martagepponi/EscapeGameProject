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

import Bean.AbstractMinigame;
import Bean.MiniGame;
import Bean.Room;
import Bean.User;
import DAO.MiniGameDAO;
import DAO.RoomDAO;


@WebServlet("/Minigame")
public class Minigame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
   
    public Minigame() {
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
		HttpSession session = request.getSession();
		String id_stanzaString = (String) session.getAttribute("id_stanza");
		int id_stanza = Integer.parseInt(id_stanzaString);
//		System.out.println(id_stanza);
		
		RoomDAO roomDAO = new RoomDAO(connection);
		Room room;
		try {
			room = roomDAO.selectById(id_stanza, connection);
	
		
		String prima_volta =(String) session.getAttribute("prima_volta");
		session.setAttribute("prima_volta", "NO");  //COSì GAME.JSP SA CHE DEVE ATTIVARE LA SECONDA MAP
		System.out.println("primavolta:"+ prima_volta);
		
		
		
		int numeroMinigame = Integer.parseInt((String)session.getAttribute("numeroMinigame"));
		
		int numeroMuro = Integer.parseInt((String)session.getAttribute("muro"));
		session.setAttribute("muro", ++numeroMuro);
		System.out.println("numeroMinigame"+ numeroMinigame);
		

		
		int Id_minigame = 0;
		if(numeroMinigame==1) {
			
	
			Id_minigame= room.getMinigame1();
			
			
			
			
		}else if(numeroMinigame==2) {
			Id_minigame=room.getMinigame2();
			
			
		}else if(numeroMinigame==3) {
			
			Id_minigame=room.getMinigame3();
			
			
		}
		System.out.println("minigame1 qui"+ Id_minigame);
		if (Id_minigame != 0 ) {
			MiniGameDAO minigameDAO = new MiniGameDAO(connection);
			AbstractMinigame minigame = (AbstractMinigame) minigameDAO.findById(Id_minigame);
			
			System.out.println("MINIGAME TIPO:"+ minigame.getType());
			 
			
			session.setAttribute("Minigame", minigame);
		
			if(minigame.getType().equals("hangmangame")) {
				//request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
			    request.getRequestDispatcher("/Hangmangame.jsp").forward(request, response);
			}else if(minigame.getType().equals("Quizgame")) {
				request.getRequestDispatcher("/Quizgame.jsp").forward(request, response);
				//request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
			}else if(minigame.getType().equals("Quizgame")) {
				request.getRequestDispatcher("/Affinitygame.jsp").forward(request, response);
				//request.getRequestDispatcher("/Minigame.jsp").forward(request, response);
				
			}else {
				
				//TODO errore
			}
			
			

		} else {
			//TODO: errore
		}
		
	} catch (SQLException e) {
			
			e.printStackTrace();
		}

		
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
