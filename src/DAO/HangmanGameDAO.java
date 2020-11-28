package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Bean.Hangmangame;

public class HangmanGameDAO {
	
private Connection connection;
	
	public HangmanGameDAO (Connection conn) {
	this.connection= conn;
	}
	
	
	

	//CERCO LA TUPLA DI HANGMANEGAME CON UNO SPECIFICO IDHANGMANGAME
	
	public static Hangmangame selectById(int idhangman, Connection connection) throws SQLException {
		Hangmangame t = new Hangmangame();
		String query = "SELECT * FROM `hangmangame` where `idhangman` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idhangman);
			result = pstatement.executeQuery();
			while (result.next()) {
				
				t.setIdHangman(result.getInt("idhangman"));
				t.setIdsubject(result.getInt("idsubject"));
				t.setWord(result.getString("word"));
				t.setQuestion1(result.getString("question1"));
				t.setQuestion2(result.getString("question2"));
				t.setPrize(result.getString("prize"));
				t.setPass(result.getString("pass"));
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close result");
			}
			try {
				pstatement.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close statement");
			}
		}
		return t;
	}

}
