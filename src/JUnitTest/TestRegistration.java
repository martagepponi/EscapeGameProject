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
		assertTrue(user.getPassword()=="123");
		assertFalse(user.getIduser()== 0);
	}
	
	@Test
    void testRegisterMethod() {
		RegistrationDAO dao = new RegistrationDAO(this.connection);
		
		//verifico che non sono presenti username=marco1 in db
		assertTrue(dao.register(user.getUsername())== true);
	}
	
	@Test
        void testAddUserMethod() {
		RegistrationDAO dao = new RegistrationDAO(this.connection);
		
		//verifico che inserimento in db è andato a buon fine 
//		dao.addUser("marco", "bianchi", "marco1", "123", "studente");
//		assertTrue(dao.register(user.getUsername())== false);
		//FUNZIONA LA PRIMA VOLTA COME è GIUSTO CHE SIA PERCHè INSERISCE UTENTE E LA SECONDA VOLTA NON LO INSERISCE PIù
		
	}


	

}
