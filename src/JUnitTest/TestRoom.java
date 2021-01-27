package JUnitTest;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.Room;
import DAO.RoomDAO;


class TestRoom {
	
	private Connection connection;
	private Room room = new Room();
	private ArrayList<Room> RoomsList = new ArrayList<Room>();
	int id=1;
	int minigame1;
	int numElements;
	
	
	@BeforeEach
	   void setUp() throws Exception{
		System.out.println("setUp");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/escapegame?useTimezone=true&serverTimezone=UTC", "root", "marta");
			System.out.println("Connessione al db effettuata correttamente!");

		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Can't load database driver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
		
		
	}
	
	
	
	
	@Test
	void testFindAllRooms() {
		RoomDAO dao = new RoomDAO(this.connection);
		numElements = RoomsList.size();
		
		//verifico che elementi nella lista sono 0
		assertTrue(numElements== 0);       
		
		
		//verifico che elementi della lista non sono 0
		RoomsList= (ArrayList<Room>) dao.findAllRooms();
		numElements = RoomsList.size();    
		assertTrue(numElements!=0);                  
		
		
		//verifico che gli elementi di RoomList siano oggetti Room
		assertTrue(RoomsList.get(0).getClass().equals(room.getClass()));
	}
	
	
	@Test
	void testSelectById() {
		RoomDAO dao = new RoomDAO(this.connection);
		
		//verifico che la stanza ritornata non sia vuota
		assertFalse(room.equals(dao.selectById(id, connection))); 
		
		//verifico che minigame1 è un intero
	    minigame1 = dao.selectById(1, connection).getMinigame1(); //verifico che minigame1 è un intero
	    
	    //verifico che non esistono stanze con id negativo
	    assertTrue(dao.selectById(-1, connection) == null);
	    
	    
	  //verifico che minigame1 prende il giusto valore (1)
	    assertTrue(minigame1==1);
	}
	
}
