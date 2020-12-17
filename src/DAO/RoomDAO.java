package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import Bean.Room;


public class RoomDAO {
	private Connection connection;
	public RoomDAO (Connection conn) {
		this.connection= conn;
		}

	
	//LEGGO TUTTE LE ROOMS PRESENTI IN DATABASE
	//invece che idsubject e idprof esce name subject e name prof
	public List<Room> findAllRooms() {
		
	
		
	String query = "SELECT `idroom`,`title`, `date`, `subject`.`name`, `user`.`name`,`user`.`surname`, `room`.`password`, `minigame1`, `minigame2`, `minigame3`, `finalgame`, `thumbnail` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` ON `idprof`=`iduser` AND `escapegame`.`room`.`idsubject` = `escapegame`.`subject`.`idsubject`;";
		List<Room> Rooms = new ArrayList<Room>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			result = pstatement.executeQuery();
			while (result.next()) {
			
				
				int idRoom = result.getInt("idroom"); 
				//System.out.println("idRoom  "+ idRoom);
				String title = result.getString("title");
				Date date = result.getDate("date");
				String subject = result.getString("subject.name");
				String profName = result.getString("user.name");
				String profSurname = result.getString("user.surname");
				String password = result.getString("password");
				int minigame1 = result.getInt("minigame1");
				int minigame2 = result.getInt("minigame2");
				int minigame3 = result.getInt("minigame3");
				int finalgame = result.getInt("finalgame");
				String thumbnail = result.getString("thumbnail");
			
				Room room = new Room(idRoom,title, date, subject,profName , profSurname, password, minigame1, minigame2, minigame3, finalgame, thumbnail);
				Rooms.add(room);
				System.out.println("rooms DAO:  "+ Rooms);
				
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			  try {
				  result.close();
			  } 
			  catch (Exception e2) {
				  e2.printStackTrace();
			  }
			  try {
				  pstatement.close();
			  } 
			  catch (Exception e3) {
				  e3.printStackTrace();
			  }
		}
		return Rooms;
	}
	
	

	//LEGGO TUTTE LE ROOMS CREATE DAL PROF LOGGATO

	public List<Room> findCreatedRooms(int iduser) {
		String query ="SELECT `user`.`name`, `user`.`surname`, `room`.`idroom`,`room`.`title`, `room`.`date`, `room`.`thumbnail`, `subject`.`name` AS `subject`, `subject`.`year` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` WHERE `escapegame`.`room`.`idprof`=`escapegame`.`user`.`iduser` AND `escapegame`.`room`.`idsubject`= `escapegame`.`subject`.`idsubject` AND `escapegame`.`room`.`idprof`= ?;";
		
		
		List<Room> createtedRooms = new ArrayList<Room>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, iduser);
			result = pstatement.executeQuery();
			while (result.next()) {
			
				
				int idRoom = result.getInt("idroom"); 
				String title = result.getString("title");
				Date date = result.getDate("date");
				String subject = result.getString("subject");
				String profName = result.getString("user.name");
				String profSurname = result.getString("user.surname");
				int year = result.getInt("year");
				String thumbnail = result.getString("thumbnail");

				Room room = new Room(idRoom, title, date, subject, profName, profSurname, year, thumbnail);
				createtedRooms.add(room);
			
				
				
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			  try {
				  result.close();
			  } 
			  catch (Exception e2) {
				  e2.printStackTrace();
			  }
			  try {
				  pstatement.close();
			  } 
			  catch (Exception e3) {
				  e3.printStackTrace();
			  }
		}
		return createtedRooms;
	}
	
	
	
		
	
	
	//CERCO UNA ROOM TRAMITE ID
	
	public Room selectById(int idRoom, Connection connection) {
		Room t = null;
		String query = "SELECT `idroom`,`title`, `date`, `subject`.`name`, `user`.`name`,`user`.`surname`, `room`.`password`, `minigame1`, `minigame2`, `minigame3`, `finalgame`, `thumbnail` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` ON `idprof`=`iduser` AND `escapegame`.`room`.`idsubject` = `escapegame`.`subject`.`idsubject` WHERE `escapegame`.`room`.`idroom` = ? ;";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idRoom);
			result = pstatement.executeQuery();
			if (result.next()) {
				t = new Room();
				t.setIdRoom(result.getInt("idroom"));
				t.setTitle(result.getString("title"));
				t.setDate(result.getDate("date"));
				t.setSubject(result.getString("subject.name"));
				t.setProfName(result.getString("user.name"));
				t.setProfSurname(result.getString("user.surname"));
				t.setPassword( result.getString("password"));
				t.setMinigame1(result.getInt("minigame1"));
				t.setMinigame2(result.getInt("minigame2"));
				t.setMinigame3(result.getInt("minigame3"));
				t.setFinalgame(result.getInt("finalgame"));
				t.setThumbnail(result.getString("thumbnail"));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			t = null;
		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				pstatement.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return t;
	}

}