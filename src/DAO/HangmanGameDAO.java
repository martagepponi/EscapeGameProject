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
		Hangmangame hangman = new Hangmangame();
		String query = "SELECT * FROM `hangmangame` where `idhangman` = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, idhangman);
			result = pstatement.executeQuery();
			while (result.next()) {
				
				hangman.setIdHangman(result.getInt("idhangman"));
				hangman.setIdsubject(result.getInt("idsubject"));
				hangman.setWord(result.getString("word"));
				hangman.setQuestion1(result.getString("question1"));
				hangman.setQuestion2(result.getString("question2"));
				hangman.setPrize(result.getString("prize"));
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
		return hangman;
	}

}
