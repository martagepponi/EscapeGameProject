package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.User;
import DAO.UserDAO;



class TestUser {

private Connection connection;
User user = new User();
String username1= "marta";
String password1= "marti";
String username2= "tommifabbri"; //username esistente in db
String password2= "tommi1";
String surname = "lll";



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
	void testCheck() {
	UserDAO dao = new UserDAO(this.connection);
	
	//verifico che l'oggetto tornato sia un oggetto user (se parametri in ingresso esistenti)
	assertTrue((dao.check(username2, password2)).getClass()== user.getClass());
	
	
	//verifico che l'oggetto tornato non sia un oggetto user (se parametri in ingresso non esistenti)
	assertFalse(dao.check(username1, password1)== user);
	assertTrue(dao.check(username1, password1)== null);
	
	System.out.println("test testCheck di TestUser eseguito");
	
	}
	
	
	@Test
	void testGetSet() {
		
		//verifico che password sia "marti"
		user.setPassword(password1);
		assertEquals("marti", user.getPassword());
		
		//verifico che surname sia "lll"
		user.setSurname(surname);
		assertEquals("lll", user.getSurname());
		
		System.out.println("test GetSet di TestUser eseguito");
	
	}
	
	
	

}
