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

import com.google.gson.Gson;

import Bean.AbstractMinigame;
import Bean.RoomCreationResponse;
import Bean.Subject;
import Bean.User;
import DAO.MiniGameDAO;
import DAO.RoomDAO;
import DAO.SubjectDAO;

/**
 * Servlet implementation class InitRoomCreation
 */
@WebServlet("/RoomCreation")
public class RoomCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private static final String HANGMAN = "hangmangame";
	private static final String AFFINITY = "affinitygame";
	private static final String QUIZ = "quizgame";
    
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
				
			}else if("minigameListByType".equals(action)){
				String type= request.getParameter("type");
				String numCombo= request.getParameter("num");
				String idSubj = request.getParameter("idSubject");
				int idSubject = Integer.parseInt(idSubj);
				MiniGameDAO minigameDao = new MiniGameDAO(connection);
				List<AbstractMinigame> minigameByTypeList = minigameDao.findMinigamesByTypesubject(type,idSubject);
				if(minigameByTypeList.isEmpty()) {
					retval.setOutcome(false);
				}else {
					retval.setOutcome(true);
					retval.setMinigameByTypeList(minigameByTypeList);
					retval.setComboBoxSelected(numCombo);
					
				}
				
				
			}else if("createRoom".equals(action)){
				
				String idM1 = request.getParameter("idMinigame1");
				String idM2 = request.getParameter("idMinigame2");
				String idM3 = request.getParameter("idMinigame3");
				String idp = request.getParameter("idprof");
				String ids = request.getParameter("idsubject");
				int idMinigame1 = Integer.parseInt(idM1);
				int idMinigame2 = Integer.parseInt(idM2);
				int idMinigame3 = Integer.parseInt(idM3);
				int idprof = Integer.parseInt(idp);
				int idsubject = Integer.parseInt(ids);
				String password = request.getParameter("password");
				
				
				
				RoomDAO roomDao = new RoomDAO(connection);
				
				try {
					roomDao.addRoom(idsubject, idprof, password, idMinigame1, idMinigame2, idMinigame3);
					retval.setOutcome(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}else if("addSubject".equals(action)){
				
				String name = request.getParameter("subjectName");
				String y = request.getParameter("subjectYear");
				int year = Integer.parseInt(y);
	
				SubjectDAO subjectDao = new SubjectDAO(connection);
				
				try {
					subjectDao.addSubject(name, year);
					retval.setOutcome(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}else if("createMinigame".equals(action)){
				MiniGameDAO minigameDao = new MiniGameDAO(connection);
				
				String type = request.getParameter("type");
				if(HANGMAN.equalsIgnoreCase(type)) {
				String hangmanWord = request.getParameter("hangmanWord");
				String questionH = request.getParameter("questionH");
				String hintH = request.getParameter("hintH");
				String ids = request.getParameter("idSubject");
				int idsubject = Integer.parseInt(ids);
					try {
						minigameDao.addMinigameH(hangmanWord, questionH, hintH, idsubject);
						retval.setOutcome(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(AFFINITY.equalsIgnoreCase(type)) {
					String affinityWord = request.getParameter("affinityWord");
					String word1A = request.getParameter("word1A");
					String word2A = request.getParameter("word2A");
					String word3A = request.getParameter("word3A");
					String word4A = request.getParameter("word4A");
					String hintA = request.getParameter("hintA");
					String ids = request.getParameter("idSubject");
					int idsubject = Integer.parseInt(ids);
						try {
							minigameDao.addMinigameA(affinityWord, word1A, word2A, word3A, word4A, hintA, idsubject);
							retval.setOutcome(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else if(QUIZ.equalsIgnoreCase(type)) {
					String questionQ = request.getParameter("questionQ");
					String quizWord = request.getParameter("quizWord");
					String wrong1Q = request.getParameter("wrong1Q");
					String wrong2Q = request.getParameter("wrong2Q");
					String ids = request.getParameter("idSubject");
					int idsubject = Integer.parseInt(ids);
						try {
						minigameDao.addMinigameQ(questionQ, quizWord, wrong1Q, wrong2Q, idsubject);
							retval.setOutcome(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
				
	
				
				
				
				
				
				
			}else {
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
