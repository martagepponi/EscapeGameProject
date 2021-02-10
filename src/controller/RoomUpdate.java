package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
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
import Bean.Room;
import Bean.RoomCreationResponse;
import Bean.RoomUpdateResponse;
import Bean.User;
import DAO.MiniGameDAO;
import DAO.RoomDAO;


@WebServlet("/RoomUpdate")
public class RoomUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
   
    public RoomUpdate() {
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
		RoomUpdateResponse retval = new RoomUpdateResponse();
		
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("user");
		if(session.isNew() || user == null) {
			System.out.println("redirect a login -...");
			retval.setSessionExpired(true);
		}else {
			
			int iduser = user.getIduser();	
			String action = request.getParameter("action");
			 if("fillForm".equals(action)) {
				MiniGameDAO minigameDao = new MiniGameDAO(connection);
				RoomDAO roomDao = new RoomDAO(connection);
								
				String idR = request.getParameter("idRoom");
				int idRoom = Integer.parseInt(idR);
				Room room = roomDao.selectById(idRoom, connection);
				int idM1 = room.getMinigame1();
				retval.setIdMinigame1(idM1);
				int idM2 = room.getMinigame2();
				retval.setIdMinigame2(idM2);
				int idM3 = room.getMinigame3();
				retval.setIdMinigame3(idM3);
				String subj = room.getSubject();
				int idSubj = room.getIdSubject();
				retval.setSubject(subj);
				AbstractMinigame minigame1 = minigameDao.findById(idM1, idRoom);
				AbstractMinigame minigame2 = minigameDao.findById(idM2, idRoom);
				AbstractMinigame minigame3 = minigameDao.findById(idM3, idRoom);
				String type1 = minigame1.getType();
				retval.setType1(type1);
				String type2 = minigame2.getType();
				retval.setType2(type2);
				String type3 = minigame3.getType();
				retval.setType3(type3);
				List<AbstractMinigame> minigameByTypeList = minigameDao.findMinigamesByTypesubject(type1,idSubj);
				retval.setMinigameByTypeList1(minigameByTypeList);
				minigameByTypeList = minigameDao.findMinigamesByTypesubject(type2,idSubj);
				retval.setMinigameByTypeList2(minigameByTypeList);
				minigameByTypeList = minigameDao.findMinigamesByTypesubject(type3,idSubj);
				retval.setMinigameByTypeList3(minigameByTypeList);
				
				retval.setOutcome(true);
			}else if("minigameListByType".equals(action)){
				String type= request.getParameter("type");
				String numCombo= request.getParameter("num");
				String idRm = request.getParameter("idRoom");
				int idRoom = Integer.parseInt(idRm);
				
				RoomDAO roomDao = new RoomDAO(connection);
				Room room = roomDao.selectById(idRoom, connection);
				int idSubj = room.getIdSubject();
				MiniGameDAO minigameDao = new MiniGameDAO(connection);
				List<AbstractMinigame> minigameByTypeList = minigameDao.findMinigamesByTypesubject(type,idSubj);
				if(minigameByTypeList.isEmpty()) {
					retval.setOutcome(false);
				}else {
					retval.setOutcome(true);
					retval.setMinigameByTypeList(minigameByTypeList);
					retval.setComboBoxSelected(numCombo);}
			}else if("updateRoom".equals(action)){
				
				String idM1 = request.getParameter("idMinigame1");
				String idM2 = request.getParameter("idMinigame2");
				String idM3 = request.getParameter("idMinigame3");
				String idRm = request.getParameter("idRoom");
				int idRoom = Integer.parseInt(idRm);
				int idMinigame1 = Integer.parseInt(idM1);
				int idMinigame2 = Integer.parseInt(idM2);
				int idMinigame3 = Integer.parseInt(idM3);
				
				RoomDAO roomDao = new RoomDAO(connection);
				
				String password = request.getParameter("password");
				if(password == "") {
					Room room = roomDao.selectById(idRoom, connection);
					password = room.getPassword();
				}
				
				
				
				
				try {
					roomDao.updateRoom( idRoom,password, idMinigame1, idMinigame2, idMinigame3);
					retval.setOutcome(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}else {
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
