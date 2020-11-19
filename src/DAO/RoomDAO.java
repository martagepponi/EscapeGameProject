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
	//invece che idsubject e idprof esce nome materia e nome prof
	public List<Room> findAllRooms() {
		
	
		
	String query = "SELECT `idroom`, `date`, `subject`.`name`, `user`.`name`, `room`.`password`, `minigame1`, `minigame2`, `minigame3`, `finalgame`, `thumbnail` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` ON `idprof`=`iduser` AND `escapegame`.`room`.`idsubject` = `escapegame`.`subject`.`idsubject`;";
		List<Room> Rooms = new ArrayList<Room>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			result = pstatement.executeQuery();
			while (result.next()) {
			
				
				int idRoom = result.getInt("idroom"); 
				System.out.println("idRoom  "+ idRoom);
				Date date = result.getDate("date");
				System.out.println("date  " + date);
				String subject = result.getString("subject.name");
				System.out.println("materia  " + subject);
				String profName = result.getString("user.name");
				System.out.println("prof   " + profName);
				String password = result.getString("password");
			//	System.out.println("pwd  " + password);
				int minigame1 = result.getInt("minigame1");
				//System.out.println("mg1  " + minigame1);
				int minigame2 = result.getInt("minigame2");
				//System.out.println("mg2  " + minigame2);
				int minigame3 = result.getInt("minigame3");
				//System.out.println("mg3  " + minigame3);
				int finalgame = result.getInt("finalgame");
				//System.out.println("fg   " + finalgame);
				String thumbnail = result.getString("thumbnail");
				System.out.println("imm   " + thumbnail);

			
				Room room = new Room(idRoom, date, subject,profName , password, minigame1, minigame2, minigame3, finalgame, thumbnail);
		
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
		String query ="SELECT `user`.`name`, `user`.`surname`, `room`.`idroom`, `room`.`date`, `room`.`thumbnail`, `subject`.`name` AS `subject`, `subject`.`year` FROM `escapegame`.`room` JOIN `escapegame`.`user` JOIN `escapegame`.`subject` WHERE `escapegame`.`room`.`idprof`=`escapegame`.`user`.`iduser` AND `escapegame`.`room`.`idsubject`= `escapegame`.`subject`.`idsubject` AND `escapegame`.`room`.`idprof`= ?;";
		
		
		List<Room> createdRooms = new ArrayList<Room>();
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, iduser);
			result = pstatement.executeQuery();
			while (result.next()) {
			
				
				int idRoom = result.getInt("idroom"); 
				System.out.println("idRoom  "+ idRoom);
				Date date = result.getDate("date");
				System.out.println("date  " + date);
				String subject = result.getString("subject");
				System.out.println("materia  " + subject);
				String profName = result.getString("user.name");
				System.out.println("prof   " + profName);
				String profSurname = result.getString("user.surname");
				System.out.println("cognome   " + profSurname);
				int year = result.getInt("year");
				System.out.println("year:  " + year);
				String thumbnail = result.getString("thumbnail");
				System.out.println("imm   " + thumbnail);

			
				Room room = new Room(idRoom, date, subject, profName, profSurname, year, thumbnail);
		
				createdRooms.add(room);
			
				
				
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
		return createdRooms;
	}
	
	
	
		
	
	
	//CERCO UNA ROOM TRAMITE ID
/*	
	public Room selectById(int idRoom, Connection connection) throws SQLException {
		Room t = new Room();
		String query = "SELECT * FROM `room` where `idroom` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idRoom);
			result = pstatement.executeQuery();
			while (result.next()) {
				
				t.setIdRoom(result.getInt("idroom"));
				t.setDate(result.getDate("date"));
			//	t.setSubject(result.getString("subject"));
				//t.setProf(result.getString("prof"));
				t.setMinigame1(result.getInt("minigame1"));
				t.setMinigame2(result.getInt("minigame2"));
				t.setMinigame3(result.getInt("minigame3"));
				t.setFinalgame(result.getInt("finalgame"));
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close result");
			}
			try {
				pstatement.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close statement");
			}
		}
		return t;
	}

	*/
	
}
