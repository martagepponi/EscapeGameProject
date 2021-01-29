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
	equals(dao.check(username1, password1)== null);
	
	}
	
	
	

}