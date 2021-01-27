package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO {

    private  Connection connection;
	
	public RegistrationDAO (Connection conn) {
	this.connection= conn;
	}
	

	// CONTROLLO SE UTENTE ESISTE GIA' NEL DB
	public boolean register(String username) {

		String query = " SELECT username FROM user WHERE user.username= ?;";
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String username1 = null;

		try {

			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, username);
			rs = pstatement.executeQuery();
			System.out.println("username"+ username );

			if (rs.next()) {
				username1 = rs.getString("username");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstatement.close();
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
        //SE username E' = NULL ALLORA utente NON ESISTE NEL DB E SI PUO' PASSARE ALL'INSERT      
		if (username1 == null) {	
			return true;
			//SE username E' DIVERSA DA NULL USERNAME GIA' IN UTILIZZO
		} else {
			return false;
		}
	}

	//QUERY PER AGGIUNGERE UN NUOVO UTENTE NEL DB
	public void addUser(String name, String surname, String username, String password, String type) {
		String query = "INSERT INTO `escapegame`.`user` (`name`, `surname`, `username`, `password`, `type`) VALUES (?, ?, ?, ?, ?);";
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, name);
			pstatement.setString(2, surname); 
			pstatement.setString(3, username);
			pstatement.setString(4, password);
			pstatement.setString(5, type);
			
			pstatement.executeUpdate(); 
			
			}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try 
			{ 
				pstatement.close();
			} 
			catch (Exception e3)
			{ e3.printStackTrace(); } 
		}
		
	}

}  

	
