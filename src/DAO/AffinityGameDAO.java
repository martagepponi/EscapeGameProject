package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Bean.Affinitygame;


public class AffinityGameDAO {
	
	
private Connection connection;
	
	public AffinityGameDAO (Connection conn) {
	this.connection= conn;
	}
	
	
	
	//CERCO LA TUPLA DI AFFINITYGAME CON UNO SPECIFICO IDAFFINITYGAME
	
		public static Affinitygame SelectById(int idGame, Connection connection) throws SQLException {
			Affinitygame t = new Affinitygame();
			String query = "SELECT * FROM `affinitygame` where `idaffinityGame` = ?";
			ResultSet result = null;
			PreparedStatement pstatement = null;
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, idGame);
				result = pstatement.executeQuery();
				while (result.next()) {
					
					t.setIdAffgame(result.getInt("idaffinityGame"));
					t.setIdsubject(result.getInt("idsubject"));
					t.setWord1(result.getString("word1"));
					t.setWord2(result.getString("word2"));
					t.setWord3(result.getString("word3"));
					t.setWord4(result.getString("word4"));
					t.setRightAnswer(result.getString("rightanswer"));
					t.setHint(result.getString("hint"));
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
