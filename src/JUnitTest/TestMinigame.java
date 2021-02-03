package JUnitTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.AbstractMinigame;
import Bean.Affinitygame;
import DAO.MiniGameDAO;




class TestMinigame {
	
	private Connection connection;
	
	
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
	void testFindById() {
		AbstractMinigame ag = new Affinitygame();
		MiniGameDAO dao = new MiniGameDAO(this.connection);
		
		//verifico che il tipo di minigioco tornato sia affinitygame
		ag =  dao.findById(6,1);
		assertTrue(ag.getType().equals("affinitygame"));
		
		
		//verifico che un minigame con id=-1 non esiste
		ag = dao.findById(-1,1);
		assertNull(ag);                            
		
		 //verifico che la seconda espressione scatena una eccezione uguale alla prima espressione
		                                          //function(){..... return dao.findById(Integer.parseInt("mm"));}
		assertThrows(NumberFormatException.class, () -> { dao.findById(Integer.parseInt("abc"),Integer.parseInt("def"));  });
		
		
		System.out.println("test testFindById di TestMinigame eseguito");
	}
}
