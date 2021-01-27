package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bean.User;
import DAO.RegistrationDAO;


public class TestRegistration {
	private User user;
	private ArrayList<User> UserList;
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
		
		
		
		this.user = new User (1, "marco", "bianchi", "marco1", "123", "studente");
		UserList = new ArrayList<User>();
		
	}

	@Test
       void testUser() {
		assertEquals("marco1", user.getUsername());
		assertFalse(user.getUsername()=="");
		assertFalse(user.getUsername()== "marco");
		assertFalse(user.getUsername()=="MARCO1");
	}
	
	@Test
    void testRegisterMethod() {
		RegistrationDAO dao1 = new RegistrationDAO(this.connection);
		equals(dao1.register(user.getUsername())== true);
	}
	
	@Test
        void testAddUserMethod() {
		RegistrationDAO dao2 = new RegistrationDAO(this.connection);
		dao2.addUser("marco", "bianchi", "marco1", "123", "studente");
		
	}


	

}
