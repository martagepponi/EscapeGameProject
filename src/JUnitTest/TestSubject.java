package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.UnavailableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import Bean.Subject;
import DAO.SubjectDAO;

class TestSubject {
	
	private Connection connection;
	private Subject subject = new Subject();
	int idroom=1;
	String nome="";
	
	
	
	
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
	void testfindSubjectByIdRoom() {
		SubjectDAO dao = new SubjectDAO(this.connection);
		
		//verifico che la materia ritornata non sia vuota
		assertFalse(dao.findSubjectByIdRoom(idroom).equals(subject)); 
		
		
		//verifico che oggetto tornato è un oggetto subject
		assertTrue((new Subject().getClass()).equals(dao.findSubjectByIdRoom(1).getClass()));
		
		//verifico che la materia di subject(1) sia matematica
		String name =dao.findSubjectByIdRoom(1).getName();
		assertTrue(name.equals("matematica"));
		
		//verifico che attributo name di subject sia di tipo stringa
		assertTrue(name.getClass().equals(nome.getClass()));
		
	}

}
