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

	
	//TUTTE LE ROOMS PRESENTI IN DATABASE
	//invece che idsubject e idprof esce name subject e name prof
	 public List<Room> findAllRooms() {
		
	String query = "SELECT `idroom`,`title`, `date`, `subject`.`name`, `user`.`name`,`user`.`surname`, `room`.`password`, `minigame1`, `minigame2`, `minigame3`, `thumbnail`, `room`.`idsubject` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` ON `idprof`=`iduser` AND `escapegame`.`room`.`idsubject` = `escapegame`.`subject`.`idsubject`;";
		List<Room> Rooms = new ArrayList<Room>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			result = pstatement.executeQuery();
			while (result.next()) {
			
				
				int idRoom = result.getInt("idroom"); 
				String title = result.getString("title");
				Date date = result.getDate("date");
				String subject = result.getString("subject.name");
				int idSubject = result.getInt("room.idsubject");
				String profName = result.getString("user.name");
				String profSurname = result.getString("user.surname");
				String password = result.getString("password");
				int minigame1 = result.getInt("minigame1");
				int minigame2 = result.getInt("minigame2");
				int minigame3 = result.getInt("minigame3");
				String thumbnail = result.getString("thumbnail");
			
				Room room = new Room(idRoom,title, date, subject,profName , profSurname, password, minigame1, minigame2, minigame3, thumbnail);
				room.setIdSubject(idSubject);
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
	
	
	 
	 
		
		//CERCO UNA ROOM TRAMITE ID
		public Room selectById(int idRoom, Connection connection) {
			Room t = null;
			String query = "SELECT `idroom`,`title`, `date`, `subject`.`name`, `user`.`name`,`user`.`surname`, `room`.`password`, `minigame1`, `minigame2`, `minigame3`, `thumbnail`,`room`.`idsubject` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` ON `idprof`=`iduser` AND `escapegame`.`room`.`idsubject` = `escapegame`.`subject`.`idsubject` WHERE `escapegame`.`room`.`idroom` = ? ;";
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
					t.setIdSubject(result.getInt("room.idsubject"));
					t.setProfName(result.getString("user.name"));
					t.setProfSurname(result.getString("user.surname"));
					t.setPassword( result.getString("password"));
					t.setMinigame1(result.getInt("minigame1"));
					t.setMinigame2(result.getInt("minigame2"));
					t.setMinigame3(result.getInt("minigame3"));
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
		
		
		
		

	//TUTTE LE ROOMS CREATE DAL PROF LOGGATO

	public List<Room> findCreatedRooms(int iduser) {
		String query ="SELECT `user`.`name`, `user`.`surname`, `room`.`idroom`,`room`.`title`, `room`.`date`, `room`.`thumbnail`, `subject`.`name` AS `subject`, `subject`.`year` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` WHERE `escapegame`.`room`.`idprof`=`escapegame`.`user`.`iduser` AND `escapegame`.`room`.`idsubject`= `escapegame`.`subject`.`idsubject` AND `escapegame`.`room`.`idprof`= ? ORDER BY `room`.`date`;";
		
		
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
	
	
	
	public void addRoom(int idsubject, int idprof, String password, int idMin1, int idMin2,int idMin3) {
		String query = "INSERT INTO `escapegame`.`room` (`idsubject`, `idprof`, `password`, `minigame1`, `minigame2`, `minigame3`, `finalgame`, `thumbnail`, `title`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idsubject);
			pstatement.setInt(2, idprof); 	
			pstatement.setString(3, password);
			pstatement.setInt(4, idMin1);
			pstatement.setInt(5, idMin2);
			pstatement.setInt(6, idMin3);
			pstatement.setInt(7, 2);
			pstatement.setString(8, "stanza");
			pstatement.setString(9, "Un brutto risveglio");
			
			pstatement.executeUpdate(); 
			
			}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try 
			{ 
				pstatement.close();
			} 
			catch (Exception e3)
			{ e3.printStackTrace(); } 
		}
		
	}
	
	public void updateRoom(int idRoom,String password,int idMinigame1,int idMinigame2,int idMinigame3) {

		String query ="UPDATE escapegame.room SET password = ?, minigame1= ?, minigame2= ?,minigame3= ? WHERE idroom = ?";
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1,  password);
			pstatement.setInt(2,  idMinigame1);
			pstatement.setInt(3,  idMinigame2);
			pstatement.setInt(4,  idMinigame3);
			pstatement.setInt(5,  idRoom);
		    pstatement.executeUpdate();
		
		
		}catch(SQLException e) {
			System.out.append("SQL ERROR");
			e.printStackTrace();
		} finally {
			try {
				pstatement.close();
			}catch(Exception e) {
				System.out.append("SQL STMT ERROR");
				e.printStackTrace();
				
			}
		}
		
		
		
		
		
		
	}
	
	
	
	
}