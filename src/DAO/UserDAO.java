package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.Room;
import Bean.User;

public class UserDAO {
	private Connection connection;

	public UserDAO (Connection conn) {
		this.connection= conn;
	}

	//CONTROLLO CHE I DATI INSERITI DALL'UTENTE SIANO CORRETTI
	public User check(String username, String psw) {

		String query = "SELECT * FROM `user` WHERE `username`=? AND `password` =?;";
		User user = null;
		ResultSet result = null;
		PreparedStatement pstatement = null;

		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setString(1, username);
			pstatement.setString(2, psw);
			result = pstatement.executeQuery();

			//RIPRENDO RISULTATI DELLA QUERY
			while(result.next()) {
				int iduser =result.getInt("iduser");
				String name= result.getString("name");
				String surname= result.getString("surname");
				String username1= result.getString("username");
				String password= result.getString("password");
				String type=result.getString("type");



				user = new User(iduser, name, surname, username1, password, type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				result.close();
			} 
			catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				pstatement.close();
			} 
			catch (Exception e3) {
				e3.printStackTrace();
			}
		}
		return user;
	}

}

