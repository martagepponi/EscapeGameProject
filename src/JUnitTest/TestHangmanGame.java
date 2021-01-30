package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.AbstractMinigame;
import Bean.Affinitygame;
import Bean.Hangmangame;
import DAO.HangmanGameDAO;
import DAO.MiniGameDAO;


class TestHangmanGame {
	
	private Connection connection;
	int idHangman =1;
	
	
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
	void testSelectById() {
		AbstractMinigame hg = new Hangmangame();
		HangmanGameDAO dao = new HangmanGameDAO(this.connection);
		
	
		
		

	}

}
