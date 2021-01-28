package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.Ranking;
import Bean.Room;
import DAO.RankingDAO;


class TestRanking {
	
	private Connection connection;
	private Ranking ranking = new Ranking();
	private ArrayList<Ranking> RankingsList = new ArrayList<Ranking>();
	private int iduser= 1;
	
	
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
	void testfindAllRanking() {
		RankingDAO dao = new RankingDAO(this.connection);
		
		//verifico che giocatore con id=1 non abbia ancora punteggi salvati
		RankingsList= (ArrayList<Ranking>) dao.findAllRanking(iduser);
		assertTrue(dao.findAllRanking(iduser).isEmpty());
		assertTrue(dao.findAllRanking(iduser).size()==0);
		
		
		
	}

}
